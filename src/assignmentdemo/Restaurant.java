/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentdemo;

import java.sql.*;

/**
 *
 * @author yannikolaev
 */
public class Restaurant {
    
    private String title;
    private String location;
    private int avg_rate;
    private int count_rev;

    public Restaurant() {
        System.out.println("Restaurant Constructor Method");
    }

    public void setTitle(String rest_title) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT title\n"
                    + "FROM Restaurants\n"
                    + "WHERE title = '" + rest_title + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.isBeforeFirst()) {
                System.out.println("Title is free!");
                title = rest_title;
            } else {
                System.out.println("Title is already taken!");
                title = "0";
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setLocation(String rest_loc) {
        location = rest_loc;
    }

    public String getLocation() {
        return location;
    }

    public int stat_Avg() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT ROUND(AVG(CAST(rating AS INT)), 0) AS Average, title\n"
                    + "FROM Restaurants, Reviews\n"
                    + "WHERE Restaurants.rest_id = Reviews.rest_id";
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            if (!more) {
                avg_rate = 0;
            } else {
                String result = rs.getString(1);
                avg_rate = Math.round(Float.parseFloat(result));
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        return avg_rate;
    }

    public int count_rev() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT COUNT(review_id)\n"
                    + "FROM Restaurants, Reviews\n"
                    + "WHERE Restaurants.rest_id = Reviews.rest_id";
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            if (!more) {
                count_rev = 0;
            } else {
                String result = rs.getString(1);
                count_rev = Math.round(Float.parseFloat(result));
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        return count_rev;
    }
}
