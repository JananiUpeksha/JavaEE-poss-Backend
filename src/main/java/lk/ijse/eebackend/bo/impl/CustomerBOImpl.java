package lk.ijse.eebackend.bo.impl;

import lk.ijse.eebackend.bo.CustomerBO;
import lk.ijse.eebackend.dao.CustomerDAO;
import lk.ijse.eebackend.dao.impl.CustomerDAOImpl;
import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO;

    public CustomerBOImpl() {
        this.customerDAO = new CustomerDAOImpl(); // Dependency injection can be used here for better flexibility
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        try {
            return customerDAO.saveCustomer(customerDTO, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save customer", e);
        }
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection) {
        try {
            return customerDAO.updateCustomer(id, customerDTO, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update customer", e);
        }
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) throws SQLException {
        return customerDAO.deleteCustomer(customerId, connection);
    }

    /*@Override
    public CustomerDTO getCustomerById(String customerId, Connection connection) throws SQLException {
        return customerDAO.getCustomerById(customerId, connection);
    }*/

  /*  @Override
    public CustomerDTO getCustomerById(String id, Connection connection) throws SQLException {
        return customerDAO.getCustomerById(id, connection);
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        return customerDAO.getAllCustomers(connection);
    }*/
    @Override
     public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
      return customerDAO.getAllCustomers(connection);
  }

}
