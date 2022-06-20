package com.revature.dao;

import com.revature.MainDriver;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;
import java.sql.*;

import java.util.ArrayList;
public class UserDao implements UserDaoInterface{


    @Override
    public void insertUser(User user) {
        MainDriver.monitor.getRequestLatency().record(() -> {
            //int userID, String firstName, String lastName, String username, String password, boolean isEmployee)
            String sql = "insert into users (first_name, last_name, username, password) values " +
                    "(?,?,?,?)";
            Connection connection = ConnectionFactory.getConnection();

            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getUsername());
                ps.setString(4, user.getPassword());
                ps.execute();
            } catch (SQLException e){
                e.printStackTrace();
            }
        });

    }

    @Override
    public User getUserByUsername(String username) {
       return MainDriver.monitor.getRequestLatency().record(() -> {
            String sql = "select * from users a left join employees b on a.user_id = b.fk_user_id where username = ?";
            Connection connection = ConnectionFactory.getConnection();
            User user = null;
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    int userID = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String password = rs.getString("password");
                    boolean isEmployee = rs.getBoolean("authorized");
                    user = new User(userID, firstName, lastName, username, password, isEmployee);
                }

            } catch (SQLException e){
                e.printStackTrace();
            }
            return user;
        });

    }

    @Override
    public User getUserByUserID(int userID) {
        return MainDriver.monitor.getRequestLatency().record(() -> {
            String sql = "select * from users a left join employees b on a.user_id = b.fk_user_id where username = ?";
            Connection connection = ConnectionFactory.getConnection();
            User user = null;
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    int userIDa = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    boolean isEmployee = rs.getBoolean("authorized");
                    user = new User(userIDa, firstName, lastName, username, password, isEmployee);
                }

            } catch (SQLException e){
                e.printStackTrace();
            }
            return user;
        });

    }

    @Override
    public ArrayList<User> getAllUsers() {
        return MainDriver.monitor.getRequestLatency().record(() -> {
            //int userID, String firstName, String lastName, String username, String password, boolean isEmployee)
            String sql = "select * from users a left join employees b on a.user_id = b.fk_user_id";
            Connection connection = ConnectionFactory.getConnection();
            ArrayList<User> users = new ArrayList<>();

            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    boolean isEmployee;
                    if(rs.getBoolean("authorized")){ //might not work, need to test.
                        isEmployee = true;
                    } else{
                        isEmployee = false;
                    }
                    User user = new User(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            isEmployee
                    );
                    users.add(user);
                }

            } catch (SQLException e){
                e.printStackTrace();
            }
            return users;
        });

    }

    @Override
    public boolean existsByName(String username) {
        return Boolean.TRUE.equals(MainDriver.monitor.getRequestLatency().record(() -> {
            String sql = "select * from users a left join employees b on a.user_id = b.fk_user_id where username = ?";
            Connection connection = ConnectionFactory.getConnection();
            boolean exists = false;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                if (rs.isBeforeFirst()) {
                    exists = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return exists;
        }));

    }

    @Override
    public boolean existsByUserID(int userID) {
        return Boolean.TRUE.equals(MainDriver.monitor.getRequestLatency().record(() -> {
            String sql = "select * from users a left join employees b on a.user_id = b.fk_user_id where user_id = ?";
            Connection connection = ConnectionFactory.getConnection();
            boolean exists = false;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();

                if (rs.isBeforeFirst()) {
                    exists = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return exists;
        }));

    }
}
