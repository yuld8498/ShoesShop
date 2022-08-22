package DAO;

import Model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements IOrderDao{
    private String jdbcURL = "jdbc:mysql://localhost:3306/quanlisanpham?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private static final String INSERT_ORDER_SQL =  "INSERT INTO order_details" +
            " (person_order, phoneNumber, city, price, email, quantity, idProduct, IDStatus)" +
            " VALUES"+ " (?,?,?,?,?,?,?,?);";
    private static final String SELECT_ALL_ORDER = "select * from order_details";
    private static final String SELECT_ALL_ORDER_STATUS_0 = "select * from order_details where IDStatus =0";
    private static final String SELECT_ORDER_INFO = "SELECT p.productID,p.image,t.typeName, o.quantity, p.price" +
            " from ((product as p" +
            " inner join order_details as o on idProduct = productID)" +
            " inner join typeproduct as t on p.typeID = t.typeID) where person_order = ? and IDStatus =0;";
    private static final String CONFIRM_ORDER_USER = "Update order_details set IDStatus =1 where person_order=?";
    @Override
    public void InsertOrder(Order order) {
        try(Connection connection = getConnection(); PreparedStatement preparedStatement =connection.prepareStatement(INSERT_ORDER_SQL)){
            preparedStatement.setString(1,order.getUserName());
            preparedStatement.setString(2,order.getPhoneNumber());
            preparedStatement.setInt(3,order.getCity());
            preparedStatement.setDouble(4,order.getPrice());
            preparedStatement.setString(5,order.getEmail());
            preparedStatement.setInt(6,order.getProductQuaility());
            preparedStatement.setInt(7,order.getProductID());
            preparedStatement.setInt(8,0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void confirmOrder(String userName) {
        try(Connection connection = getConnection(); PreparedStatement preparedStatement =connection.prepareStatement(CONFIRM_ORDER_USER)) {
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> showOrderList(String userName) {
        List<Order> listOrder = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_INFO);) {
            preparedStatement.setString(1,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setProductID(resultSet.getInt("productID"));
                order.setProductQuaility(resultSet.getInt("quantity"));
                listOrder.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOrder;
    }

    @Override
    public Order selectOrderByID(int ID) {
        return null;
    }

    @Override
    public void deleteOrderByID(int ID) {

    }

    @Override
    public List<Order> selectAllOrder() {
        List<Order> listOrder = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setID(resultSet.getInt("id"));
                order.setProductID(resultSet.getInt("idProduct"));
                order.setProductQuaility(resultSet.getInt("quantity"));
                order.setIDStatus(resultSet.getInt("IDStatus"));
                listOrder.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOrder;
    }
    public List<Order> selectAllOrderToConform() {
        List<Order> listOrder = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER_STATUS_0);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setID(resultSet.getInt("id"));
                order.setProductID(resultSet.getInt("idProduct"));
                order.setProductQuaility(resultSet.getInt("quantity"));
                order.setIDStatus(resultSet.getInt("IDStatus"));
                listOrder.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOrder;
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
