package lk.ijse.eebackend.bo.impl;

import lk.ijse.eebackend.bo.ItemBO;
import lk.ijse.eebackend.dao.ItemDAO;
import lk.ijse.eebackend.dao.impl.ItemDAOImpl;
import lk.ijse.eebackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean saveItem(ItemDTO item, Connection connection) throws SQLException {
        return itemDAO.saveItem(item, connection);
    }

    @Override
    public boolean updateItem(String id, ItemDTO item, Connection connection) throws SQLException {
        return itemDAO.updateItem(id, item, connection);
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws SQLException {
        return itemDAO.deleteItem(id, connection);
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws SQLException {
        return itemDAO.getAllItems(connection);
    }
}
