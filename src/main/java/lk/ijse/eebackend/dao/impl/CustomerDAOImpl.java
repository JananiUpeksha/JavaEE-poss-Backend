package lk.ijse.eebackend.dao.impl;

import lk.ijse.eebackend.dao.CustomerDAO;
import lk.ijse.eebackend.dto.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    /*@Override
    public CustomerDTO getCustomerById(String customerId, Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id = ?";
        CustomerDTO customer = null;

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                customer = new CustomerDTO();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setContact(rs.getString("contact"));
            }
        }
        return customer;
    }*/

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
    @Override
   public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
       List<CustomerDTO> customers = new ArrayList<>();
       String query = "SELECT * FROM customer"; // Replace with your actual query
       try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
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
   }

}
