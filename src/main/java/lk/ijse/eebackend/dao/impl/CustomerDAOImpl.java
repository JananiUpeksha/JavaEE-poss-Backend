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
}
