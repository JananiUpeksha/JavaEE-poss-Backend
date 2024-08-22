package lk.ijse.eebackend.bo;

import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection);

    boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection);

    boolean deleteCustomer(String customerId, Connection connection) throws SQLException;

    /*  @Override
          public CustomerDTO getCustomerById(String id, Connection connection) throws SQLException {
              return customerDAO.getCustomerById(id, connection);
          }

          @Override
          public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
              return customerDAO.getAllCustomers(connection);
          }*/
    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;

    /*CustomerDTO getCustomerById(String id, Connection connection) throws SQLException;

    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;*/

    /*CustomerDTO getCustomerById(String customerId, Connection connection) throws SQLException;*/
}
