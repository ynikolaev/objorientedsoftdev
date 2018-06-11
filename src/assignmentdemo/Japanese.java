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
//extends main class Restaurant
public class Japanese extends Restaurant {

    //add fields to storage
    private String title;
    private String location;
    private String description;
    private String review;
    private String picture;
    private String type;
    private int rating;
    private int avg_rate;
    private int count_rev;

    //constructor that sets variables using getters from main class
    public Japanese() {
        System.out.println("Japanese Class Constructor Method");
        title = super.getTitle();
        location = super.getLocation();
    }

    //description setter
    public void setDescription(String rest_desc) {
        description = rest_desc;
    }

    //description getter
    public String getDescription() {
        return description;
    }

    //review setter
    public void setReview(String rest_review) {
        review = rest_review;
    }

    //review getter
    public String getReview() {
        return review;
    }

    //picture setter
    public void setPicture(String rest_pic) {
        picture = rest_pic;
    }

    //picture getter
    public String getPicture() {
        return picture;
    }

    //restaurant type setter
    public void setType(String rest_type) {
        rest_type = "japanese";
        type = rest_type;
    }

    //restaurant type getter
    public String getType() {
        return type;
    }

    //restaurant rating setter
    public void setRating(int rest_rate) {
        rating = rest_rate;
    }

    //restaurant rating getter
    public int getRating() {
        return rating;
    }

    @Override
    //method that calculates average rating for each restaurant of the current type
    public int stat_Avg() {
        //counts reviews for a certain type of restaurants
        if (count_rev() == 0) {
            //if there are no reviews, average is zero
            avg_rate = 0;
        } else {
            //opens connection
            Connection conn = null;
            try {
                Class.forName("org.sqlite.JDBC");
                //opens database files
                conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
                Statement stmt = conn.createStatement();
                //SQL statement that calculate average for certain type of restaurant
                String query = "SELECT ROUND(AVG([rating]),0) AS rating, Restaurants.rest_id\n"
                        + "FROM Restaurants, Reviews\n"
                        + "WHERE Restaurants.rest_id = Reviews.rest_id\n"
                        + "AND type = 'japanese'";
                ResultSet rs = stmt.executeQuery(query);
                boolean more = rs.next();
                //if there are no results, average equals zero
                if (!more) {
                    avg_rate = 0;
                } else {
                    //if there is a result, converts it from integer to float and to integer
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
        }
        return avg_rate;
    }

    @Override
    public int count_rev() {
        //opens connection
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //opens database files
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            //SQL statement that counts reviews
            String query = "SELECT COUNT(rating)\n"
                    + "FROM Restaurants, Reviews\n"
                    + "WHERE Restaurants.rest_id = Reviews.rest_id\n"
                    + "AND type = 'japanese'";
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            if (!more) {
                count_rev = 0;
            } else {
                //if there is a result, converts it from integer to float and to integer
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
