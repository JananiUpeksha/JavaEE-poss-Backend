package lk.ijse.eebackend.dao.impl;

import lk.ijse.eebackend.dao.ItemDAO;
import lk.ijse.eebackend.dto.ItemDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean saveItem(ItemDTO item, Connection connection) throws SQLException {
        String query = "INSERT INTO item (name, price, qty) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, item.getName());
            pst.setBigDecimal(2, new BigDecimal(item.getPrice()));
            pst.setInt(3, Integer.parseInt(item.getQty()));
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateItem(String id, ItemDTO item, Connection connection) throws SQLException {
        String query = "UPDATE item SET name = ?, price = ?, qty = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, item.getName());
            pst.setBigDecimal(2, new BigDecimal(item.getPrice()));
            pst.setInt(3, Integer.parseInt(item.getQty()));
            pst.setInt(4, Integer.parseInt(id));
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws SQLException {
        String query = "DELETE FROM item WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, Integer.parseInt(id));
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws SQLException {
        String query = "SELECT * FROM item";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<ItemDTO> itemList = new ArrayList<>();
            while (rs.next()) {
                itemList.add(new ItemDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("price"),
                        rs.getString("qty")
                ));
            }
            return itemList;
        }
    }

}
