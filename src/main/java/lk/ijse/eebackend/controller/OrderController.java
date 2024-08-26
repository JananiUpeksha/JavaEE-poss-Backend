package lk.ijse.eebackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.eebackend.bo.OrderBO;
import lk.ijse.eebackend.bo.impl.OrderBOImpl;
import lk.ijse.eebackend.dto.OrderDTO;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    private final OrderBO orderBO;

    public OrderController() throws SQLException, NamingException {
        this.orderBO = new OrderBOImpl(); // Initialize orderBO
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);

            boolean isSaved = orderBO.saveOrder(orderDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the stack trace
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NamingException e) {
            e.printStackTrace(); // Log the stack trace
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace for unexpected errors
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String orderId = req.getParameter("orderId");

            if (orderId != null && !orderId.trim().isEmpty()) {
                OrderDTO orderDTO = orderBO.getOrderById(orderId);

                if (orderDTO != null) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String jsonResponse = jsonb.toJson(orderDTO);
                    resp.setContentType("application/json");
                    resp.getWriter().write(jsonResponse);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
