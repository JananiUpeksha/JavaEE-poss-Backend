package lk.ijse.eebackend.controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.eebackend.dto.CustomerDTO;
import lk.ijse.eebackend.bo.impl.CustomerBOImpl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/customer", loadOnStartup = 2)
public class CustomerController extends HttpServlet {
    private Connection connection;
    private CustomerBOImpl customerBOImpl;

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/webPoss");
            this.connection = pool.getConnection();
            this.customerBOImpl = new CustomerBOImpl(); // Initialize the CustomerService
            System.out.println("Database connection established successfully");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ServletException("Database driver class not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Failed to establish database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            if (customerBOImpl.saveCustomer(customerDTO, connection)) {
                writer.write("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Save customer failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        String customerID = req.getParameter("id");
        if (customerID == null || customerID.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing customer ID");
            return;
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO updatedCustomer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            updatedCustomer.setId(customerID);

            if (customerBOImpl.updateCustomer(customerID, updatedCustomer, connection)) {
                writer.write("Customer updated successfully");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                writer.write("Update failed");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (JsonException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("id");
        if (customerId == null || customerId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required.");
            return;
        }

        try (var writer = resp.getWriter()) {
            if (customerBOImpl.deleteCustomer(customerId, connection)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Delete Failed");
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the customer.");
            throw new RuntimeException(e);
        }
    }

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("id");

        if (customerId == null || customerId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required.");
            return;
        }

        try (var writer = resp.getWriter()) {
            CustomerDTO customer = customerBOImpl.getCustomerById(customerId, connection);

            if (customer != null) {
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(customer, writer);
                System.out.println("Customer data retrieved: " + customer);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
            e.printStackTrace();
        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
            e.printStackTrace();
        }
    }*/

   /* @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("id");

        if (customerId != null && !customerId.isEmpty()) {
            try (var writer = resp.getWriter()) {
                CustomerDTO customer = customerBOImpl.getCustomerById(customerId, connection);

                if (customer != null) {
                    resp.setContentType("application/json");
                    Jsonb jsonb = JsonbBuilder.create();
                    jsonb.toJson(customer, writer);
                    System.out.println("Customer data retrieved: " + customer);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
                }
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
                e.printStackTrace();
            } catch (JsonException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
                e.printStackTrace();
            }
        } else {
            // Handle retrieving all customers
            try (var writer = resp.getWriter()) {
                List<CustomerDTO> customers = customerBOImpl.getAllCustomers(connection);

                if (!customers.isEmpty()) {
                    resp.setContentType("application/json");
                    Jsonb jsonb = JsonbBuilder.create();
                    jsonb.toJson(customers, writer);
                    System.out.println("All customers data retrieved: " + customers);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // No content
                }
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
                e.printStackTrace();
            } catch (JsonException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
                e.printStackTrace();
            }
        }
    }*/
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try (var writer = resp.getWriter()) {
           // Retrieve all customers
           List<CustomerDTO> customers = customerBOImpl.getAllCustomers(connection);

           // Check if any customers were retrieved
           if (customers != null && !customers.isEmpty()) {
               resp.setContentType("application/json");
               Jsonb jsonb = JsonbBuilder.create();
               // Convert the list of customers to JSON
               jsonb.toJson(customers, writer);
               System.out.println("Customer data retrieved: " + customers);
           } else {
               // Send a 404 if no customers found
               resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No customers found");
           }
       } catch (SQLException e) {
           resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
           e.printStackTrace();
       } catch (JsonException e) {
           resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
           e.printStackTrace();
       }
   }


}
