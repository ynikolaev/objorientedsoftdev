/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentdemo;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author yannikolaev
 */
public class AddReview extends javax.swing.JFrame {

    //add fields to storage
    private int count_id;
    private int rest_id;
    private String title;
    private String location;
    private String description;
    private String review;
    private String picture;
    private String type;
    private int rating;
    private int getRest;
    private int getAvgNum;

    /**
     * Creates new form AddReview
     */
    //constructor takes 3 arguments: review id, restaurant id and average number
    public AddReview(int id, int getRest, int getAvgNum) {
        //close window button leads to no actions
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        //set window size
        setSize(454, 343);
        //sets restaurant id
        setGetRest(getRest);
        //sets average number
        setGetAvgNum();
        //sets review id
        setRest_id(id);
        //initializes GUI components
        initComponents();
    }

    //setter for restaurant id
    public void setGetRest(int number) {
        getRest = number;
    }

    //getter for restaurant id
    public int getGetRest() {
        return getRest;
    }

    //setter for average number
    public void setGetAvgNum() {
        //opens the connection
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //gets database file
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            //creates statement
            Statement stmt = conn.createStatement();
            //SQL query that will get average number for certain restaurant
            String query = "SELECT title, ROUND(AVG([rating]),0) AS rating, review\n"
                    + "FROM Restaurants, Reviews\n"
                    + "WHERE Restaurants.rest_id = Reviews.rest_id\n"
                    + "AND Restaurants.rest_id= '" + getGetRest() + "'\n"
                    + "GROUP BY title";
            //executeQuery uses for SELECT statements
            ResultSet rs = stmt.executeQuery(query);
            //boolean indicates if there are more results
            boolean more = rs.next();
            //if no more, prints that there is no reviews
            if (!more) {
                System.out.println("No Average");
            } else {
                //set rounded average number to the getAvgNum variable
                String result = rs.getString(2);
                getAvgNum = Math.round(Float.parseFloat(result));
            }
            //in case connection failed
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            //closes connection
            try {
                conn.close();
                //checks if closing was successful
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    //getter for an average number
    public int getGetAvgNum() {
        return getAvgNum;
    }

    //setter for restaurant id
    public void setRest_id(int id) {
        rest_id = id;
    }

    //getter for restaurant id
    public int getRest_id() {
        return rest_id;
    }

    //setter for a review
    public String setReview(String r_review) {
        //checks if review field is empty
        if (r_review.equals("")) {
            String message = "\"Failure\"\n"
                    + "Please enter the review";
            JOptionPane.showMessageDialog(new JFrame(), message, "Adding Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            //checks if review contains only numeric values
            if (isType(r_review, "int")) {
                String message = "\"Failure\"\n"
                        + "Review cannot contain only numeric values";
                JOptionPane.showMessageDialog(new JFrame(), message, "Adding Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                review = r_review;
                return "success";
            }
        }
        return "failure";
    }
    
    //getter for a review
    public String getReview() {
        return review;
    }

    //setter for rating combobox
    public void setRating(int r_rating) {
        rating = r_rating;
    }

    //getter for rating
    public int getRating() {
        return rating;
    }

    //checks variables for certain formats
    public Boolean isType(String testStr, String type) {
        try {
            if (type.equalsIgnoreCase("float")) {
                Float.parseFloat(testStr);
            } else if (type.equalsIgnoreCase("int")) {
                Integer.parseInt(testStr);
            } else if (type.equalsIgnoreCase("double")) {
                Double.parseDouble(testStr);
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(new java.awt.Point(100, 100));

        jPanel1.setBackground(new java.awt.Color(203, 171, 171));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Review");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel2.setText("Review:");

        jTextField1.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel3.setText("Rating:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));

        jButton1.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton1.setText("Submit");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton3.setText("Back");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(34, 34, 34)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Connection conn = null;
        try {
            //gets information about certain restaurant
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT *\n"
                    + "FROM Restaurants\n"
                    + "WHERE rest_id = '" + getGetRest() + "'";
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            if (!more) {
                /*String message = "\"Oops!\"\n"
                        + "Your request had not been found\n"
                        + "Try again!";
                JOptionPane.showMessageDialog(new JFrame(), message, "Search Error",
                        JOptionPane.ERROR_MESSAGE);
                dispose();*/
            } else {
                //1-rest-id, 2-title, 3-location, 4-type
                //5-picture, 6-description
                //sets restaurant id (converts from string to integer)
                setRest_id(Integer.parseInt(rs.getString(1)));
                //if review field is valid, inserts values to the Reviews table
                if (setReview(jTextField1.getText()).equals("success")) {
                    setRating(Integer.parseInt(jComboBox1.getSelectedItem().toString()));
                    query = "insert into Reviews (review, rating, rest_id) "
                            + "values ('" + getReview() + "','" + getRating() + "','" + getRest_id() + "')";
                    //execution of the query; executeUpdate checks if table was changed
                    int result = stmt.executeUpdate(query);
                    //result returns how many rows were changed/added
                    if (result > 0) {
                        //if nore than 0, then insert query was successful
                        String message = "\"Success\"\n"
                                + "New review was added!\n"
                                + "Thank you!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Successful!",
                                JOptionPane.INFORMATION_MESSAGE);
                        //closes current window
                        dispose();
                        //opens previous window with reviews (passes to Review class constructor
                        //restaurant's id
                        new Reviews(getGetRest()).setVisible(true);
                    } else {
                        String message = "\"Oops!\"\n"
                                + "New review was not added!\n"
                                + "Try Again!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Failed!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    System.out.println("Fail to add a review");
                }
            }
            //System.out.println(rs.getString(7));
            //System.out.print(model.getValueAt(0, 0));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //back button that leads to previous window
        dispose();
        //contains restaurant id for Reviews class constructor
        new Reviews(getGetRest()).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
