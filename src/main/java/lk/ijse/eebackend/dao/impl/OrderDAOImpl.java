package lk.ijse.eebackend.dao.impl;

import lk.ijse.eebackend.dao.OrderDAO;
import lk.ijse.eebackend.entity.OrderEntity;
import lk.ijse.eebackend.entity.Order_itemEntity;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean saveOrder(OrderEntity orderEntity, Connection connection) throws SQLException {
        String sql = "INSERT INTO orders (customerId, total, date) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, orderEntity.getCustomerId());
            pst.setDouble(2, orderEntity.getTotal());
            // Convert LocalDate to java.sql.Date
            pst.setDate(3, Date.valueOf(orderEntity.getDate()));
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Set generated orderId in the OrderEntity object
                    orderEntity.setOrderId(String.valueOf(generatedKeys.getInt(1)));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean saveOrderItem(Order_itemEntity orderItemEntity, Connection connection) throws SQLException {
        String sql = "INSERT INTO order_items (orderId, itemId, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(orderItemEntity.getOrderId()));
            pst.setInt(2, orderItemEntity.getItemId());
            pst.setInt(3, orderItemEntity.getQuantity());
            return pst.executeUpdate() > 0;
        }
    }

    private static final String GET_LAST_ORDER_QUERY = "SELECT * FROM orders ORDER BY orderId DESC LIMIT 1";
    private static final String GET_ORDER_BY_ID_QUERY = "SELECT * FROM orders WHERE orderId = ?";

    @Override
    public OrderEntity getLastOrder(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ORDER_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return new OrderEntity(
                        resultSet.getString("orderId"),
                        resultSet.getString("customerId"),
                        resultSet.getDouble("total"),
                        resultSet.getDate("date").toLocalDate() // Convert java.sql.Date to LocalDate
                );
            }
            return null;
        }
    }

    @Override
    public OrderEntity getOrderById(String orderId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID_QUERY)) {
            preparedStatement.setString(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new OrderEntity(
                            resultSet.getString("orderId"),
                            resultSet.getString("customerId"),
                            resultSet.getDouble("total"),
                            resultSet.getDate("date").toLocalDate() // Convert java.sql.Date to LocalDate
                    );
                }
                return null;
            }
        }
    }
}
