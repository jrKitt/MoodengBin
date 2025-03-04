package com.example.moodengbin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class getMenu {
    public static List<Menu> getMenuByCategory(String category) {
        List<Menu> menuList = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE category = ?";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Menu menu = new Menu(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("image_path")
                );
                menuList.add(menu);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return menuList;
    }
}
