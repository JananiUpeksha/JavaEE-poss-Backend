package lk.ijse.eebackend.controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.eebackend.bo.impl.ItemBOImpl;
import lk.ijse.eebackend.dto.ItemDTO;
import jakarta.servlet.annotation.WebServlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/item", loadOnStartup = 2)
public class ItemController extends HttpServlet {
    private Connection connection;
    private ItemBOImpl itemBOImpl;

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/webPoss");
            this.connection = pool.getConnection();
            this.itemBOImpl = new ItemBOImpl();
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
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

            if (itemBOImpl.saveItem(itemDTO, connection)) {
                writer.write("Item saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Save item failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        String itemId = req.getParameter("id");
        if (itemId == null || itemId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing item ID");
            return;
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO updatedItem = jsonb.fromJson(req.getReader(), ItemDTO.class);
            updatedItem.setId(itemId);

            if (itemBOImpl.updateItem(itemId, updatedItem, connection)) {
                writer.write("Item updated successfully");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                writer.write("Update failed");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (JsonException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("id");
        if (itemId == null || itemId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item ID is required.");
            return;
        }

        try (var writer = resp.getWriter()) {
            if (itemBOImpl.deleteItem(itemId, connection)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Delete Failed");
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the item.");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            List<ItemDTO> items = itemBOImpl.getAllItems(connection);

            if (!items.isEmpty()) {
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(items, writer);
                System.out.println("All items data retrieved: " + items);
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

}
