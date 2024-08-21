package lk.ijse.eebackend.bo;

import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection);

    boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection);

    boolean deleteCustomer(String customerId, Connection connection) throws SQLException;
}
