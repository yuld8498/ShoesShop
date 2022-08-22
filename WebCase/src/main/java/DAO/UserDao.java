package DAO;

import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/quanlisanpham?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private static final String INSERT_USER_SQL = "INSERT INTO user " +
            "(userName, password, fullname, age, email, phoneNumber, address)VALUES" + "(?,?,?,?,?,?,?);";
    private static final String SELECT_ALL_USERS = "select * from user";
    private static final String DELETE_USERS_SQL = "delete from user where id = ?;";
    private static final String UPDATE_USERS_SQL = "update user" +
            " set fullname = ?, age =?,email= ? ,phoneNumber =?, address =? where id = ?;";
    private static final String SELECT_USER_BY_ID = "select fullName,age, email,phoneNumber, address from user where id =?";
    private static final String SELECT_USER_BY_userName = "select * from user where userName =? and password = ?";
    private static final String UPDATE_PASSWORD_SQL = "update user" +
            " set password =? where userName = ?;";
    private static final String CHECK_TYPE_USER_SQL ="select u.userName,u.typeID,t.typeName from user as u " +
            "inner join typeuser as t on t.typeID = u.typeID where userName =?;";

    public UserDao() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public void insertUser(User user) {
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)){
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getFullName());
            preparedStatement.setInt(4,user.getAge());
            preparedStatement.setString(5,user.getEmail());
            preparedStatement.setString(6,user.getPhoneNumber());
            preparedStatement.setInt(7,user.getAddress());
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            printSQLException(ex);
        }
    }

    @Override
    public String typeUser(String userName){
        String typeUser =null;
        try(Connection connection =getConnection(); PreparedStatement preparedStatement =connection.prepareStatement(CHECK_TYPE_USER_SQL)){
            preparedStatement.setString(1,userName);
            ResultSet resultSet  =preparedStatement.executeQuery();
            while (resultSet.next()){
                 typeUser = resultSet.getString("typeName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeUser;
    }

    @Override
    public User selectUserByID(int id) {
        User user = null;
        try(Connection connection =getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()){
                String fullName = resultSet.getString("fullName");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");
                int address = resultSet.getInt("address");
                user = new User(fullName,age,email,phoneNumber,address);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> list = new ArrayList<>();
        try(Connection connection =getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int userID = resultSet.getInt("UserID");
                String userName = resultSet.getString("UserName");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("fullName");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");
                int address = resultSet.getInt("address");
                list.add(new User(userID,userName,password,fullName,age,email,phoneNumber,address));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUserPassword(String  userName, String newPassword) throws SQLException {
        boolean updatePassword;
        try(Connection connection =getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SQL)){
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2, userName);
            updatePassword =preparedStatement.executeUpdate()>0;
        }
        return updatePassword;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate;
        try(Connection connection = getConnection(); PreparedStatement preparedStatement =connection.prepareStatement(UPDATE_USERS_SQL)){
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setInt(2,user.getAge());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setInt(5, user.getAddress());
            preparedStatement.setInt(6,user.getUserID());

            rowUpdate = preparedStatement.executeUpdate()>0;
        }
        return rowUpdate;
    }

    @Override
    public boolean checkUserName(String userName){
        for (User user : selectAllUsers()){
            if (user.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkEmail(String email){
        for (User user : selectAllUsers()){
            if (user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPhone(String Phone){
        for (User user : selectAllUsers()){
            if (user.getPhoneNumber().equals(Phone)){
                return true;
            }
        }
        return false;
    }


    public User login(String userName, String pw){
        User user = null;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement =connection.prepareStatement(SELECT_USER_BY_userName)){
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, pw);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user= new User(resultSet.getInt("UserID"), resultSet.getString("UserName"), resultSet.getString("password"),
                        resultSet.getString("fullName"), resultSet.getInt("age"), resultSet.getString("email"),
                        resultSet.getString("phoneNumber"), resultSet.getInt("address"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
