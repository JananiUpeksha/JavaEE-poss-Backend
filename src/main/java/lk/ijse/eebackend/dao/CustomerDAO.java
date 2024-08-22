package lk.ijse.eebackend.dao;

import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    boolean saveCustomer(CustomerDTO customer, Connection connection) throws SQLException;

    boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection) throws SQLException;

    boolean deleteCustomer(String customerId, Connection connection) throws SQLException;

    /* private static final String GET_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id = ?";
         private static final String GET_ALL_CUSTOMERS = "SELECT * FROM customers";

         @Override
         public CustomerDTO getCustomerById(String id, Connection connection) throws SQLException {
             CustomerDTO customer = null;
             try (PreparedStatement ps = connection.prepareStatement(GET_CUSTOMER_BY_ID)) {
                 ps.setString(1, id);
                 try (ResultSet rs = ps.executeQuery()) {
                     if (rs.next()) {
                         customer = new CustomerDTO();
                         customer.setId(rs.getString("id"));
                         customer.setName(rs.getString("name"));
                         customer.setAddress(rs.getString("address"));
                         customer.setContact(rs.getString("contact"));
                     }
                 }
             }
             return customer;
         }

         @Override
         public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
             List<CustomerDTO> customers = new ArrayList<>();
             try (PreparedStatement ps = connection.prepareStatement(GET_ALL_CUSTOMERS);
                  ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     CustomerDTO customer = new CustomerDTO();
                     customer.setId(rs.getString("id"));
                     customer.setName(rs.getString("name"));
                     customer.setAddress(rs.getString("address"));
                     customer.setContact(rs.getString("contact"));
                     customers.add(customer);
                 }
             }
             return customers;
         }*/
    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;

   /* CustomerDTO getCustomerById(String id, Connection connection) throws SQLException;

    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;*/

    /*CustomerDTO getCustomerById(String customerId, Connection connection) throws SQLException;*/
}
