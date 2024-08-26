package lk.ijse.eebackend.bo;

import lk.ijse.eebackend.dto.OrderDTO;

import javax.naming.NamingException;
import java.sql.SQLException;

public interface OrderBO {
    boolean saveOrder(OrderDTO orderDTO) throws SQLException, NamingException;

    OrderDTO getOrderById(String orderId) throws SQLException, NamingException;

    OrderDTO getLastOrder() throws SQLException, NamingException;
}
