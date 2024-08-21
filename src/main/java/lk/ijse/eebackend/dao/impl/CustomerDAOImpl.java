package lk.ijse.eebackend.dao.impl;

import lk.ijse.eebackend.dao.CustomerDAO;
import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean saveCustomer(CustomerDTO customer, Connection connection) throws SQLException {
        String sql = "INSERT INTO customer (id, name, address, contact) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, customer.getId());
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getContact());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE customer SET name = ?, address = ?, contact = ? WHERE id = ?")) {
            ps.setString(1, customerDTO.getName());
            ps.setString(2, customerDTO.getAddress());
            ps.setString(3, customerDTO.getContact());
            ps.setString(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM customer WHERE id = ?")) {
            ps.setString(1, customerId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
