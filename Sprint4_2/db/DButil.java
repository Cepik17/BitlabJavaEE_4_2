package Sprint4_2.db;

import Sprint4_2.models.Item;
import Sprint4_2.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DButil {
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/g111_db",
                    "postgres",
                    "postgres"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "select * from items i order by i.id desc ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getLong("id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setDescription(resultSet.getString("description"));
                items.add(item);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void addItem(Item item) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "insert into items(name, price, amount) " +
                    "values (?,?,?)");
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Item getById(Long id) {
        Item item = new Item();
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "select * from items i where i.id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item.setId(resultSet.getLong("id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public static void editItem(Item item) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "update items " +
                    "set name=?, price=?, amount=? " +
                    "where id=?");
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(4, item.getId());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "delete from items i where i.id=?");
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUser(String email, String password) {
        boolean user = false;
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "select * from users where email=? and password =?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = true;
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String getFullName(String email) {
        String fullName = "";
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "select fullname from users where email=?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                fullName = resultSet.getString("fullname");
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullName;
    }

}


