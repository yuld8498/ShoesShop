package DAO;

import Model.TypeOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeOrderDao implements ITypeOrderDao{
    private String jdbcURL = "jdbc:mysql://localhost:3306/quanlisanpham?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private static final String SELECT_ALL_TYPE_ORDER = "select * from status";
    private static final String SELECT_TYPE_ORDER_BY_ID = "select * from status where statusID = ?";
    @Override
    public List<TypeOrder> selectAllType() {
        List<TypeOrder> list=new ArrayList<>();
        try(Connection connection= getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_TYPE_ORDER)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                TypeOrder typeOrder = new TypeOrder();
                typeOrder.setTypeOrderID(resultSet.getInt("statusID"));
                typeOrder.setTypeOrderName(resultSet.getString("statusName"));
                list.add(typeOrder);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public TypeOrder selectTypeByID(int ID) {
        TypeOrder typeOrder = new TypeOrder();
        try(Connection connection= getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(SELECT_TYPE_ORDER_BY_ID)){
            preparedStatement.setInt(1,ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                typeOrder.setTypeOrderID(resultSet.getInt("statusID"));
                typeOrder.setTypeOrderName(resultSet.getString("statusName"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeOrder;
    }
    protected Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
