package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String type;

    public User() {
    }

    public User(int id, String name, String username, String password, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    // Controller içerisinde olabilecek bir static Method
    // Kullanıcı listesini geri döndüren method
    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM userTable";
        User obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    // Ekle butonu için static boolean method
    public static boolean add(String name, String username, String password, String type) {
        String query = "INSERT INTO userTable (name, username, password, type) VALUES (?,?,?,?)";
        // aynı kullanıcı adlı Kullanıcı girişi sorgusu için
        User findUser = User.getFetch(username);
        if (findUser != null) {
            Helper.showMsg("Bu kullanıcı adı alınmış. Lütfen farklı bir kullanıcı adı giriniz.");
            return false; // bir değer var demektir ve false dönmesi gerekir
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setString(3, password);
            pr.setObject(4, type, Types.OTHER); // enum type olduğu için
            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1; // başarılı ise 1 değilse -1

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    // Kullanıcı adına göre aynı girilen veriyi kabul etmeyeceğiz
    public static User getFetch(String username) {
        User obj = null;
        String query = "SELECT * FROM userTable WHERE username = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, username);
            ResultSet resultSet = pr.executeQuery();
            if (resultSet.next()) {
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    // getFetch(int id)
    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM userTable WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet resultSet = pr.executeQuery();
            if (resultSet.next()) {
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    // Silme işlemi için
    public static boolean delete(int id) {
        String query = "DELETE FROM userTable WHERE id = ?";
        ArrayList<Course> courseList = Course.getListByUser(id);

        for (Course c : courseList) {
            Course.delete(c.getId());
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true; // varsayılan olarak
    }

    // Update method
    public static boolean update(int id, String name, String username, String password, String type) {
        String query = "UPDATE userTable SET name = ?, username = ? , password = ? , type = ? WHERE id = ?";
        // aynı kullanıcı adlı Kullanıcı girişi sorgusu için
        User findUser = User.getFetch(username);
        if (findUser != null && findUser.getId() != id) { // username değişmediği takdirde hatayı önlemek için
            Helper.showMsg("Bu kullanıcı adı alınmış. Lütfen farklı bir kullanıcı adı giriniz.");
            return false; // bir değer var demektir ve false dönmesi gerekir
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setString(3, password);
            pr.setObject(4, type, Types.OTHER);
            pr.setInt(5, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    // arama yaparken kullanacağımız ArrayList<User> döndürecek method
    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    // arama sorgumuz için dinamik bir yapı
    public static String searchQuery(String name, String username, String type) {
        String query = "SELECT * FROM userTable WHERE username LIKE '%{{username}}%' AND name LIKE '%{{name}}%'";
        // burda replace ile parametreleri değiştireceğiz
        query = query.replace("{{username}}", username);
        query = query.replace("{{name}}", name);
        if (!type.isEmpty()) {
            query += " AND type = '{{type}}'";
            query = query.replace("{{type}}", type);
        }
//        System.out.println(query);
        return query;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
