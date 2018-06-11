/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentdemo;
//initialization of additional libraries

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author yannikolaev
 */
public class AddRest extends JFrame implements ActionListener {

    //add fields to storage
    private JTextField title;
    private JTextField location;
    private JTextField description;
    private JTextField review;
    private JComboBox rating;
    private JComboBox type;
    private JButton submitButton;
    private JButton clearButton;
    private JButton backButton;
    private JLabel titleLabel;
    private JLabel locationLabel;
    private JLabel descriptionLabel;
    private JLabel headerLabel;
    private JLabel ratingLabel;
    private JLabel typeLabel;
    private JLabel reviewLabel;

    public AddRest() {
        //close window button leads to exit from the application
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //sets location of the application interface on the screen
        setLocation(100, 100);
        //variables for combobox initialization
        String[] items = {
            "1",
            "2",
            "3",
            "4",
            "5"
        };
        String[] rest_types = {
            "European",
            "Japanese",
            "Mexican",
            "Russian"
        };
        //initialisation of the fields
        type = new JComboBox(rest_types);
        type.setEditable(true);
        rating = new JComboBox(items);
        rating.setEditable(true);
        titleLabel = new JLabel("Restaurant Title: ");
        titleLabel.setForeground(new Color(132, 92, 92));
        locationLabel = new JLabel("Location: ");
        locationLabel.setForeground(new Color(132, 92, 92));
        descriptionLabel = new JLabel("Description: ");
        descriptionLabel.setForeground(new Color(132, 92, 92));
        ratingLabel = new JLabel("Rating: ");
        ratingLabel.setForeground(new Color(132, 92, 92));
        typeLabel = new JLabel("Type: ");
        typeLabel.setForeground(new Color(132, 92, 92));
        headerLabel = new JLabel("REGISTRATION");
        headerLabel.setForeground(new Color(132, 92, 92));
        headerLabel.setFont(headerLabel.getFont().deriveFont(64f));
        reviewLabel = new JLabel("Review");
        reviewLabel.setForeground(new Color(132, 92, 92));

        //TEXT FIELDS
        title = new JTextField(15);
        title.setPreferredSize(new Dimension(200, 24));
        location = new JTextField(15);
        location.setPreferredSize(new Dimension(200, 24));
        description = new JTextField(15);
        description.setPreferredSize(new Dimension(200, 24));
        review = new JTextField(15);
        review.setPreferredSize(new Dimension(200, 24));

        //BUTTONS 
        //and their Action Listener to proide buttons with functionality
        submitButton = new JButton("Add Restaurant");
        submitButton.setPreferredSize(new Dimension(150, 50));
        submitButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(this);

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(150, 50));
        clearButton.addActionListener(this);

        //PANELS
        //main panel
        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(gui);

        //panel to hold headers, labels, fields and buttons
        JPanel header = new JPanel(new FlowLayout());
        JPanel labels = new JPanel(new GridLayout(0, 1));
        JPanel fields = new JPanel(new GridLayout(0, 1));
        JPanel buttons = new JPanel(new FlowLayout());

        //set colour for the elements
        header.setBackground(new Color(203, 171, 171));
        header.setForeground(new Color(203, 171, 171));
        labels.setBackground(new Color(203, 171, 171));
        labels.setForeground(new Color(203, 171, 171));
        fields.setBackground(new Color(203, 171, 171));
        fields.setForeground(new Color(203, 171, 171));
        buttons.setBackground(new Color(203, 171, 171));
        buttons.setForeground(new Color(203, 171, 171));

        //set position of the elements
        gui.add(header, BorderLayout.NORTH);
        gui.add(labels, BorderLayout.WEST);
        gui.add(fields, BorderLayout.CENTER);
        gui.add(buttons, BorderLayout.SOUTH);

        //add fields and elements to the certain panel
        header.add(headerLabel);

        labels.add(titleLabel);
        fields.add(title);

        labels.add(locationLabel);
        fields.add(location);

        labels.add(descriptionLabel);
        fields.add(description);

        labels.add(typeLabel);
        fields.add(type);

        buttons.add(submitButton);
        buttons.add(clearButton);
        buttons.add(backButton);

        //essential method that sizes the frame so that all its 
        //contents are at or above their preferred sizes.  
        pack();
    }

    @Override
    //Action Listener for buttons
    public void actionPerformed(ActionEvent ae) {
        //in case Clear button is pressed
        if (ae.getSource() == clearButton) {
            //clears text field
            title.setText(null);
            location.setText(null);
            description.setText(null);
            review.setText(null);
        } else if (ae.getSource() == backButton) { //when back button pressed
            //method dispose() closes active window of the application
            dispose();
            //and then invokes new frame
            //GUI contains 2 attributes String and String
            //1 String - what shortcut to open
            //2 String - what message to pass to another class
            new GUI("menu", "").setVisible(true);
        } else if (ae.getSource() == submitButton) { // when submit button pressed
            //checks if fields are empty
            if ((title.getText().equals("")) || (description.getText().equals("")) || (location.getText().equals(""))) {
                //invokes widow dialog with a caution
                String message = "Input Error!\n"
                        + "Make sure you entered Title, Location and Description of the Restaurant\n"
                        + "Try again!";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error!",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                //following steps depend on users choice, what type the restaurant will be
                if (type.getSelectedItem().equals("European")) {
                    //If combobox value equals european, it calls new instence 
                    //of European class and goes through Restaurant and
                    //European classes methods (it is possible because of the inheritance
                    European newRest = new European();
                    //invoking setters
                    newRest.setTitle(title.getText());
                    newRest.setDescription(description.getText());
                    newRest.setLocation(location.getText());
                    newRest.setType(type.getSelectedItem().toString());
                    newRest.setReview(review.getText());
                    newRest.setRating(Integer.parseInt(rating.getSelectedItem().toString()));
                    //take values through getters
                    String rest_title = newRest.getTitle();
                    String rest_desc = newRest.getDescription();
                    String rest_loc = newRest.getLocation();
                    String rest_type = newRest.getType();
                    //checks if restaurant title is alredy taken
                    if (rest_title.equals("0")) {
                        String message = "Failure!\n"
                                + "That restaurant already exists!\n"
                                + "Try Another Title!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        //if everything is fine, it opens the connection
                        Connection conn = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            //choosing the database file
                            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
                            //creates statement
                            Statement stmt = conn.createStatement();
                            //SQL query that inserts values into the table
                            String query = "insert into Restaurants (title, location, description, picture, type) "
                                    + "values ('" + rest_title + "', '" + rest_loc + "', '" + rest_desc + "', 'sample.jpg', '" + rest_type + "')";
                            //execution of the query; executeUpdate checks if table was changed
                            int result = stmt.executeUpdate(query);
                            //result returns how many rows were changed/added
                            if (result > 0) {
                                //if nore than 0, then insert query was successful
                                String message = "Success!\n"
                                        + "New restaurant was added!\n"
                                        + "Thank you!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Successful!",
                                        JOptionPane.INFORMATION_MESSAGE);
                                //closes current window
                                dispose();
                                //opens main menu; also shows the restaurant title that has been deleted
                                new GUI("restaurant", "Restaurant " + rest_title + " was added successfully").setVisible(true);
                            } else {
                                //if no rows were changed, failed query
                                String message = "Failure!\n"
                                        + "New restaurant was not added!\n"
                                        + "Try Again!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            //catch possible connection errors    
                        } catch (Exception ex) {
                            ex.printStackTrace(System.err);
                        } finally {
                            try {
                                conn.close(); //closes connection
                            } catch (Exception ex) { //checks if successful
                                ex.printStackTrace(System.err);
                            }
                        }
                    }
                } else if (type.getSelectedItem().equals("Mexican")) {
                    //If combobox value equals european, it calls new instence 
                    //of Mexican class and goes through Restaurant and
                    //Mexican classes methods (it is possible because of the inheritance
                    Mexican newRest = new Mexican();
                    //invoking setters
                    newRest.setTitle(title.getText());
                    newRest.setDescription(description.getText());
                    newRest.setLocation(location.getText());
                    newRest.setType(type.getSelectedItem().toString());
                    newRest.setReview(review.getText());
                    //invoking getters
                    newRest.setRating(Integer.parseInt(rating.getSelectedItem().toString()));
                    String rest_title = newRest.getTitle();
                    String rest_desc = newRest.getDescription();
                    String rest_loc = newRest.getLocation();
                    String rest_type = newRest.getType();
                    //checks if restaurant title is already taken 
                    if (rest_title.equals("0")) {
                        String message = "Failure!\n"
                                + "That restaurant already exists!\n"
                                + "Try Another Title!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        //if everything is fine, it opens the connection
                        Connection conn = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            //choosing the database file
                            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
                            //creates statement
                            Statement stmt = conn.createStatement();
                            //SQL query that inserts values into the table
                            String query = "insert into Restaurants (title, location, description, picture, type) "
                                    + "values ('" + rest_title + "', '" + rest_loc + "', '" + rest_desc + "', 'sample.jpg', '" + rest_type + "')";
                            //execution of the query; executeUpdate checks if table was changed
                            int result = stmt.executeUpdate(query);
                            //result returns how many rows were changed/added
                            if (result > 0) {
                                //if nore than 0, then insert query was successful
                                String message = "Success!\n"
                                        + "New restaurant was added!\n"
                                        + "Thank you!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Successful!",
                                        JOptionPane.INFORMATION_MESSAGE);
                                //closes current window
                                dispose();
                                //opens main menu; also shows the restaurant title that has been deleted
                                new GUI("restaurant", "Restaurant " + rest_title + " was added successfully").setVisible(true);
                            } else {
                                //if no rows were changed, failed query
                                String message = "Failure!\n"
                                        + "New restaurant was not added!\n"
                                        + "Try Again!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            //catch possible connection errors    
                        } catch (Exception ex) {
                            ex.printStackTrace(System.err);
                        } finally {
                            try {
                                conn.close(); //closes connection
                            } catch (Exception ex) { //checks if successful
                                ex.printStackTrace(System.err);
                            }
                        }
                    }
                } else if (type.getSelectedItem().equals("Japanese")) {
                    //If combobox value equals european, it calls new instence 
                    //of Japanese class and goes through Restaurant and
                    //Japanese classes methods (it is possible because of the inheritance
                    Japanese newRest = new Japanese();
                    //invoke setters
                    newRest.setTitle(title.getText());
                    newRest.setDescription(description.getText());
                    newRest.setLocation(location.getText());
                    newRest.setType(type.getSelectedItem().toString());
                    newRest.setReview(review.getText());
                    newRest.setRating(Integer.parseInt(rating.getSelectedItem().toString()));
                    //invoke getters
                    String rest_title = newRest.getTitle();
                    String rest_desc = newRest.getDescription();
                    String rest_loc = newRest.getLocation();
                    String rest_type = newRest.getType();
                    //checks if restaurant title is already taken 
                    if (rest_title.equals("0")) {
                        String message = "Failure!\n"
                                + "That restaurant already exists!\n"
                                + "Try Another Title!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        //if everything is fine, it opens the connection
                        Connection conn = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            //choosing the database file
                            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
                            //creates statement
                            Statement stmt = conn.createStatement();
                            //SQL query that inserts values into the table
                            String query = "insert into Restaurants (title, location, description, picture, type) "
                                    + "values ('" + rest_title + "', '" + rest_loc + "', '" + rest_desc + "', 'sample.jpg', '" + rest_type + "')";
                            //execution of the query; executeUpdate checks if table was changed
                            int result = stmt.executeUpdate(query);
                            //result returns how many rows were changed/added
                            if (result > 0) {
                                //if nore than 0, then insert query was successful
                                String message = "Success!\n"
                                        + "New restaurant was added!\n"
                                        + "Thank you!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Successful!",
                                        JOptionPane.INFORMATION_MESSAGE);
                                //closes current window
                                dispose();
                                //opens main menu; also shows the restaurant title that has been deleted
                                new GUI("restaurant", "Restaurant " + rest_title + " was added successfully").setVisible(true);
                            } else {
                                //if no rows were changed, failed query
                                String message = "Failure!\n"
                                        + "New restaurant was not added!\n"
                                        + "Try Again!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            //catch possible connection errors    
                        } catch (Exception ex) {
                            ex.printStackTrace(System.err);
                        } finally {
                            try {
                                conn.close(); //closes connection
                            } catch (Exception ex) { //checks if successful
                                ex.printStackTrace(System.err);
                            }
                        }
                    }
                } else if (type.getSelectedItem().equals("Russian")) {
                    //If combobox value equals european, it calls new instence 
                    //of Russian class and goes through Restaurant and
                    //Russian classes methods (it is possible because of the inheritance
                    Russian newRest = new Russian();
                    //invoke setters
                    newRest.setTitle(title.getText());
                    newRest.setDescription(description.getText());
                    newRest.setLocation(location.getText());
                    newRest.setType(type.getSelectedItem().toString());
                    newRest.setReview(review.getText());
                    newRest.setRating(Integer.parseInt(rating.getSelectedItem().toString()));
                    //invoke getters
                    String rest_title = newRest.getTitle();
                    String rest_desc = newRest.getDescription();
                    String rest_loc = newRest.getLocation();
                    String rest_type = newRest.getType();
                    //checks if restaurant title is already taken 
                    if (rest_title.equals("0")) {
                        String message = "Failure!\n"
                                + "That restaurant already exists!\n"
                                + "Try Another Title!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        //if everything is fine, it opens the connection
                        Connection conn = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            //choosing the database file
                            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
                            //creates statement
                            Statement stmt = conn.createStatement();
                            //SQL query that inserts values into the table
                            String query = "insert into Restaurants (title, location, description, picture, type) "
                                    + "values ('" + rest_title + "', '" + rest_loc + "', '" + rest_desc + "', 'sample.jpg', '" + rest_type + "')";
                            //execution of the query; executeUpdate checks if table was changed
                            int result = stmt.executeUpdate(query);
                            //result returns how many rows were changed/added
                            if (result > 0) {
                                //if nore than 0, then insert query was successful
                                String message = "Success!\n"
                                        + "New restaurant was added!\n"
                                        + "Thank you!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Successful!",
                                        JOptionPane.INFORMATION_MESSAGE);
                                //closes current window
                                dispose();
                                //opens main menu; also shows the restaurant title that has been deleted
                                new GUI("restaurant", "Restaurant " + rest_title + " was added successfully").setVisible(true);
                            } else {
                                //if no rows were changed, failed query
                                String message = "Failure!\n"
                                        + "New restaurant was not added!\n"
                                        + "Try Again!";
                                JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            //catch possible connection errors    
                        } catch (Exception ex) {
                            ex.printStackTrace(System.err);
                        } finally {
                            try {
                                conn.close(); //closes connection
                            } catch (Exception ex) { //checks if successful
                                ex.printStackTrace(System.err);
                            }
                        }
                    }
                }
            }
        }
    }
}
