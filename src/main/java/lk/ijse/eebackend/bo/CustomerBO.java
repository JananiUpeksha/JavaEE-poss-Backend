package lk.ijse.eebackend.bo;

import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;

public interface CustomerBO {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection);
}
