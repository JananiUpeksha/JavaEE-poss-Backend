package lk.ijse.eebackend.bo.impl;

import lk.ijse.eebackend.bo.OrderBO;
import lk.ijse.eebackend.dao.ItemDAO;
import lk.ijse.eebackend.dao.OrderDAO;
import lk.ijse.eebackend.dao.impl.ItemDAOImpl;
import lk.ijse.eebackend.dao.impl.OrderDAOImpl;
import lk.ijse.eebackend.dto.ItemDTO;
import lk.ijse.eebackend.dto.OrderDTO;
import lk.ijse.eebackend.entity.OrderEntity;
import lk.ijse.eebackend.entity.Order_itemEntity;
import lk.ijse.eebackend.db.DbConnection;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderBOImpl implements OrderBO {

    private final ItemDAO itemDao;
    private final OrderDAO orderDao;
    private Connection connection;

    public OrderBOImpl() {
        this.itemDao = new ItemDAOImpl();
        this.orderDao = new OrderDAOImpl();
    }

    /*@Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException, NamingException {
        try (Connection connection = DbConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);  // Start transaction

            // Check if customerId exists
            if (!customerExists(orderDTO.getCustomerId(), connection)) {
                connection.rollback();
                throw new SQLException("Customer ID does not exist.");
            }

            // Create a new order entity without orderId (assuming it's auto-generated)
            OrderEntity orderEntity = new OrderEntity(
                    null,  // Auto-generated orderId
                    orderDTO.getCustomerId(),
                    orderDTO.getTotal(),
                    orderDTO.getDate() // Ensure this is a LocalDate
            );

            // Save order
            if (!orderDao.saveOrder(orderEntity, connection)) {
                connection.rollback();
                throw new SQLException("Failed to save order.");
            }

            // Fetch the generated orderId
            String generatedOrderId = orderEntity.getOrderId(); // Retrieve generated orderId from OrderEntity

            // Save order items and update item quantities
            for (ItemDTO itemDTO : orderDTO.getItemDtoList()) {
                Order_itemEntity orderItemEntity = new Order_itemEntity(
                        generatedOrderId,  // Use generated orderId
                        Integer.parseInt(itemDTO.getId()),
                        Integer.parseInt(itemDTO.getQty())
                );

                // Save order item
                if (!orderDao.saveOrderItem(orderItemEntity, connection)) {
                    connection.rollback();
                    throw new SQLException("Failed to save order item: " + itemDTO.getId());
                }

                // Update item quantity
                if (!itemDao.updateItemQuantity(itemDTO.getId(), Integer.parseInt(itemDTO.getQty()), connection)) {
                    connection.rollback();
                    throw new SQLException("Failed to update item quantity: " + itemDTO.getId());
                }
            }

            // Commit the transaction if everything is successful
            connection.commit();
            return true;

        } catch (SQLException | NamingException e) {
            throw new SQLException("Error during order processing: " + e.getMessage(), e);
        }
    }*/
    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException, NamingException {
        // Ensure date is not null before processing
        if (orderDTO.getDate() == null) {
            throw new IllegalArgumentException("Order date cannot be null.");
        }

        try (Connection connection = DbConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);  // Start transaction

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(orderDTO.getDate());

            // Check if customerId exists
            if (!customerExists(orderDTO.getCustomerId(), connection)) {
                connection.rollback();
                throw new SQLException("Customer ID does not exist.");
            }

            // Create a new order entity with sqlDate
            OrderEntity orderEntity = new OrderEntity(
                    null,  // Auto-generated orderId
                    orderDTO.getCustomerId(),
                    orderDTO.getTotal(),
                    sqlDate.toLocalDate() // Use sqlDate instead of LocalDate
            );

            // Save order
            if (!orderDao.saveOrder(orderEntity, connection)) {
                connection.rollback();
                throw new SQLException("Failed to save order.");
            }

            // Fetch the generated orderId
            String generatedOrderId = orderEntity.getOrderId(); // Retrieve generated orderId from OrderEntity

            // Save order items and update item quantities
            for (ItemDTO itemDTO : orderDTO.getItemDtoList()) {
                Order_itemEntity orderItemEntity = new Order_itemEntity(
                        generatedOrderId,  // Use generated orderId
                        Integer.parseInt(itemDTO.getId()),
                        Integer.parseInt(itemDTO.getQty())
                );

                // Save order item
                if (!orderDao.saveOrderItem(orderItemEntity, connection)) {
                    connection.rollback();
                    throw new SQLException("Failed to save order item: " + itemDTO.getId());
                }

                // Update item quantity
                if (!itemDao.updateItemQuantity(itemDTO.getId(), Integer.parseInt(itemDTO.getQty()), connection)) {
                    connection.rollback();
                    throw new SQLException("Failed to update item quantity: " + itemDTO.getId());
                }
            }

            // Commit the transaction if everything is successful
            connection.commit();
            return true;

        } catch (SQLException | NamingException e) {
            // Rollback the transaction in case of errors
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                // Log rollback exception
                System.err.println("Rollback error: " + rollbackEx.getMessage());
            }
            throw new SQLException("Error during order processing: " + e.getMessage(), e);
        }
    }



    private boolean customerExists(String customerId, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM customer WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public OrderDTO getOrderById(String orderId) throws SQLException, NamingException {
        try (Connection connection = DbConnection.getInstance().getConnection()) {
            OrderEntity orderEntity = orderDao.getOrderById(orderId, connection);
            if (orderEntity != null) {
                return new OrderDTO(
                        orderEntity.getCustomerId(),
                        orderEntity.getTotal(),
                        orderEntity.getDate(), // Ensure this is a LocalDate
                        null // Fetch items if needed
                );
            }
            return null;
        }
    }

    @Override
    public OrderDTO getLastOrder() throws SQLException, NamingException {
        try (Connection connection = DbConnection.getInstance().getConnection()) {
            OrderEntity lastOrder = orderDao.getLastOrder(connection);
            if (lastOrder != null) {
                return new OrderDTO(
                        lastOrder.getCustomerId(),
                        lastOrder.getTotal(),
                        lastOrder.getDate(), // Ensure this is a LocalDate
                        null // Assuming no need for item details here
                );
            }
            return null;
        }
    }
}
