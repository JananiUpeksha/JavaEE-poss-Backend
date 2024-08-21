package lk.ijse.eebackend.dao;

import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO {
    boolean saveCustomer(CustomerDTO customer, Connection connection) throws SQLException;

    boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection) throws SQLException;

    boolean deleteCustomer(String customerId, Connection connection) throws SQLException;
}
