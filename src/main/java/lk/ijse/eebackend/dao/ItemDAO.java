package lk.ijse.eebackend.dao;

import lk.ijse.eebackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    boolean saveItem(ItemDTO item, Connection connection) throws SQLException;

    boolean updateItem(String id, ItemDTO item, Connection connection) throws SQLException;

    boolean deleteItem(String id, Connection connection) throws SQLException;

    List<ItemDTO> getAllItems(Connection connection) throws SQLException;

    ItemDTO getItemById(String id, Connection connection) throws SQLException;

    boolean updateItemQuantity(String id, int qty, Connection connection) throws SQLException;
}
