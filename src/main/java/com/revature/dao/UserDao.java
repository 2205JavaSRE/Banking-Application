package com.revature.dao;

import com.revature.models.User;
import com.revature.util.ConnectionFactory;
import java.sql.*;

import java.util.ArrayList;
public class UserDao implements UserDaoInterface{


    @Override
    public void insertUser(User user) {
        //int userID, String firstName, String lastName, String username, String password, boolean isEmployee)
        String sql = "insert into users (first_name, last_name, username, password)";
        Connection connection = ConnectionFactory.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUsername(String username) {
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
    }

    @Override
    public ArrayList<User> getAllUsers() {
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
    }

    @Override
    public boolean existsByName(String username) {
        String sql = "select * from users a left join employees b on a.user_id = b.fk_user_id where username = ?";
        Connection connection = ConnectionFactory.getConnection();
        boolean exists = false;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.isBeforeFirst()){
                exists = true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return exists;
    }
}
