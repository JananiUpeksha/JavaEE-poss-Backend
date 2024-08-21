package lk.ijse.eebackend.dao;

import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO {
    boolean saveCustomer(CustomerDTO customer, Connection connection) throws SQLException;
}
