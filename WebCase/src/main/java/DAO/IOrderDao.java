package DAO;

import Model.Order;

import java.sql.SQLException;
import java.util.List;

public interface IOrderDao {
    void InsertOrder(Order order);
    void confirmOrder(String userName);
    List<Order> showOrderList(String userName);
    Order selectOrderByID(int ID);
    void deleteOrderByID(int ID);
    List<Order> selectAllOrder();
    List<Order> selectAllOrderToConform();
}
