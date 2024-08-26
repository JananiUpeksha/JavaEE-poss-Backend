package lk.ijse.eebackend.dao;

import lk.ijse.eebackend.entity.OrderEntity;
import lk.ijse.eebackend.entity.Order_itemEntity;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO {
    boolean saveOrder(OrderEntity orderEntity, Connection connection) throws SQLException, NamingException;

    boolean saveOrderItem(Order_itemEntity orderItemEntity, Connection connection) throws SQLException, NamingException;

    OrderEntity getLastOrder(Connection connection) throws SQLException;

    OrderEntity getOrderById(String orderId, Connection connection) throws SQLException;
}
