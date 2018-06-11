/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentdemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author yannikolaev
 */
public class Reviews extends javax.swing.JFrame {

    //add fields to storage
    private int review_id = 0;
    private int rest_id;
    private int avg_num;
    private int total_reviews;
    private String picture_link;
    private String avg_link;
    private String title;

    /**
     * Creates new form Reviews
     */
    public Reviews(int id) {
        //set restaurant id which is passed row id number
        setRest(id);
        //set average number
        setAvgNum();
        //opens connection
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //opens database file
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            //selects all information about certain restaurant
            String query = "SELECT *\n"
                    + "FROM Restaurants\n"
                    + "WHERE rest_id = '" + getRest() + "'";
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            //if the restaurant with mentioned id doesnt exist
            if (!more) {
                String message = "Oops!\n"
                        + "Your request had not been found\n"
                        + "Try again!";
                JOptionPane.showMessageDialog(new JFrame(), message, "Search Error",
                        JOptionPane.ERROR_MESSAGE);
                dispose();
                //if restaurant is found    
            } else {
                //1-rest-id, 2-title, 3-location, 4-type
                //5-picture, 6-description
                //sets restaurant picture
                setPicture(rs.getString(5));
                //sets restaurant's average rating
                setAvgRating(getAvgNum());
                //initialize components
                initComponents();
                //counts reviews for certain restaurant
                countReviews();
                //sets title for the restaurant
                setTitle(rs.getString(2));
                //sets description for the restaurant
                setDescription(rs.getString(6));
                //sets its location
                setLocation(rs.getString(3));
                //sets its type
                setType(rs.getString(4));
                //fill table with a content
                tableContent(getRest());
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

    //method that fills table with a content
    public void tableContent(int id) {
        jPanel2.setVisible(true);
        jScrollPane2.setVisible(true);
        jTable2.setVisible(true);
        //icons initialisation
        ImageIcon icon = new ImageIcon("images/1.png");
        ImageIcon delete = new ImageIcon("images/delete.png");
        //table model initialisation
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        //sets rows to zero before filling the content
        model.setRowCount(0);
        //content for tables if there are no reviews
        if (getReviewTotal() == 0) {
            jLabel10.setText("No Reviews");
            model.addRow(new Object[]{"-", "-", "-", "-", "-"});
        //if reviews are found    
        } else {
            //opens connection
            Connection conn = null;
            try {
                Class.forName("org.sqlite.JDBC");
                //opens database file
                conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
                Statement stmt = conn.createStatement();
                //select reviews of the certain restaurant
                String query = "SELECT review_id, Restaurants.rest_id, review, rating\n"
                        + "FROM Restaurants, Reviews\n"
                        + "WHERE Restaurants.rest_id = Reviews.rest_id\n"
                        + "AND Restaurants.rest_id='" + rest_id + "'";
                ResultSet rs = stmt.executeQuery(query);
                boolean more = rs.next();
                if (!more) {
                    jLabel10.setText("No Reviews");
                    jTable2.setVisible(false);
                    jScrollPane2.setVisible(true);
                } else {
                    while (more) {
                        if (rs.getString(3).equals("null")) {
                            icon = new ImageIcon("images/0.png");
                        } else {
                            if (rs.getString(4).equals("1")) {
                                icon = new ImageIcon("images/1.png");
                            } else if (rs.getString(4).equals("2")) {
                                icon = new ImageIcon("images/2.png");
                            } else if (rs.getString(4).equals("3")) {
                                icon = new ImageIcon("images/3.png");
                            } else if (rs.getString(4).equals("4")) {
                                icon = new ImageIcon("images/4.png");
                            } else if (rs.getString(4).equals("5")) {
                                icon = new ImageIcon("images/5.png");
                            }
                        }
                        //adds results to the table
                        model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), icon, delete});
                        more = rs.next();
                    }
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
    }

    //average rating number setter
    public void setAvgNum() {
        //opens connection
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //opens database file
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            //sets average number of rating for certain restaurant
            String query = "SELECT title, ROUND(AVG([rating]),0) AS rating, review\n"
                    + "FROM Restaurants, Reviews\n"
                    + "WHERE Restaurants.rest_id = Reviews.rest_id\n"
                    + "AND Restaurants.rest_id= '" + rest_id + "'\n"
                    + "GROUP BY title";
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            //if there are no reviews
            if (!more) {
                System.out.println("No Average");
                avg_num = 0;
            //if reviews have been found
            } else {
                String result = rs.getString(2);
                avg_num = Math.round(Float.parseFloat(result));
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

    //restaurant type setter
    public void setType(String type) {
        String cap = type.substring(0, 1).toUpperCase() + type.substring(1);
        jLabel9.setText(cap + " Restaurant");
    }

    //average rating number getter
    public int getAvgNum() {
        return avg_num;
    }

    //counts reviews
    public void countReviews() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT COUNT(rest_id)\n"
                    + "FROM Reviews\n"
                    + "WHERE rest_id = '" + rest_id + "'";

            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            if (!more) {
                String message = "Oops!\n"
                        + "Your request had not been found\n"
                        + "Try again!";
                JOptionPane.showMessageDialog(new JFrame(), message, "Search Error",
                        JOptionPane.ERROR_MESSAGE);
                dispose();
            } else {
                if (rs.getString(1).equals("0")) {
                    jLabel7.setText("(0)");
                } else {
                    jLabel7.setText("(" + rs.getString(1) + ")");
                }
                setReviewTotal(Integer.parseInt(rs.getString(1)));
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

    //restaurant location setter
    public void setLocation(String rest_location) {
        jLabel5.setText(rest_location);
    }

    //restaurant location getter
    public void setDescription(String rest_description) {
        jLabel3.setText(rest_description);
    }

    //restaurant id setter
    public void setRest(int id) {
        rest_id = id;
    }

    //restaurant id getter
    public int getRest() {
        return rest_id;
    }

    //restaurant picture setter
    public void setPicture(String link) {
        picture_link = link;
    }

    //restaurant title setter
    public void setTitle(String rest_title) {
        jLabel1.setText(rest_title);
        title = rest_title;
    }

    //restaurant title getter
    public String getTitle() {
        return title;
    }

    //restaurant picture allocation according to rating
    public void setAvgRating(int avg) {
        System.out.println("IMAGE avg == " + avg);
        if (avg == 1) {
            avg_link = ("images/1.png");
        } else if (avg == 2) {
            avg_link = ("images/2.png");
        } else if (avg == 3) {
            avg_link = ("images/3.png");
        } else if (avg == 4) {
            avg_link = ("images/4.png");
        } else if (avg == 5) {
            avg_link = ("images/5.png");
        } else if (avg == 0) {
            avg_link = ("images/0.png");
        }
    }

    //restaurant review id setter
    public void setReviewId(int id) {
        review_id = id;
    }

    //restaurant review id getter
    public int getReviewId() {
        return review_id;
    }

    //restaurant review total number setter
    public void setReviewTotal(int number) {
        total_reviews = number;
    }

    //restaurant review total number getter
    public int getReviewTotal() {
        return total_reviews;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ImageIcon icon = new ImageIcon("images/"+picture_link);
        Image image = icon.getImage().getScaledInstance(250,150, Image.SCALE_SMOOTH);
        icon.setImage(image);

        int borderWidth = 1;
        int spaceAroundIcon = 5;
        Color borderColor = new Color(237,214,214);

        BufferedImage bi = new BufferedImage(icon.getIconWidth() + (2 * borderWidth + 2 * spaceAroundIcon),icon.getIconHeight() + (2 * borderWidth + 2 * spaceAroundIcon), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bi.createGraphics();
        g.setColor(borderColor);
        g.drawImage(icon.getImage(), borderWidth + spaceAroundIcon, borderWidth + spaceAroundIcon, null);

        BasicStroke stroke = new BasicStroke(5); //5 pixels wide (thickness of the border)
        g.setStroke(stroke);

        g.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);
        g.dispose();
        jLabel2 = new javax.swing.JLabel(new ImageIcon(bi));
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ImageIcon avg_icon = new ImageIcon(avg_link);
        jLabel7 = new javax.swing.JLabel(avg_icon);
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(181, 135, 135));
        setLocation(new java.awt.Point(100, 100));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(203, 171, 171));
        jPanel1.setForeground(new java.awt.Color(132, 92, 92));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Restaurant");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("jLabel3");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        jLabel4.setText("Location:");

        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        jLabel6.setText("Average Rating (#):");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel7.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        jLabel8.setText("Type:");

        jLabel9.setText("jLabel9");

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Reviews");

        jPanel2.setBackground(new java.awt.Color(237, 214, 214));

        jTable2.setRowHeight(50);
        jTable2.setBackground(new java.awt.Color(203, 171, 171));
        JTableHeader header = jTable2.getTableHeader();
        header.setBackground(new Color(203, 171, 171));
        header.setForeground(new Color(0, 0, 0));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
            }
        ){
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 3: return ImageIcon.class;
                    default: return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            /*public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }*/
        });
        jTable2.setGridColor(new java.awt.Color(203, 171, 171));
        jTable2.setSelectionBackground(new java.awt.Color(132, 92, 92));
        jTable2.setSelectionForeground(new java.awt.Color(248, 227, 227));
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        model2.setRowCount(0);
        model2.addColumn("Rest_id");
        model2.addColumn("ID");
        model2.addColumn("Review");
        model2.addColumn("Rating");
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RowSelected(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        TableColumnModel tcm = jTable2.getColumnModel();
        tcm.removeColumn(tcm.getColumn(0));
        tcm.removeColumn(tcm.getColumn(0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(237, 214, 214));

        jButton1.setOpaque(true);
        jButton1.setBorderPainted(false);
        jButton1.setBackground(new java.awt.Color(176, 128, 128));
        jButton1.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButton(evt);
            }
        });

        jButton2.setVisible(false);
        jButton2.setBackground(Color.RED);
        jButton2.setOpaque(true);
        jButton2.setBorderPainted(false);
        jButton2.setBackground(new java.awt.Color(236, 116, 116));
        jButton2.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Delete");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButton(evt);
            }
        });

        jButton3.setOpaque(true);
        jButton3.setBorderPainted(false);
        jButton3.setBackground(new java.awt.Color(135, 233, 135));
        jButton3.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Add");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddReview(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3CloseButton(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel11.setIcon(new javax.swing.ImageIcon("/Users/yannikolaev/Documents/University/MSc Computing/CO4403 Object-Oriented Software Development/AssignmentDemo/images/delete.png")); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                deleteRest(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel8))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(23, 23, 23)))
                .addGap(1, 1, 1)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Close Review window
    private void CloseButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButton
        //closes current window
        dispose();
        //opens main menu with a restaurant tab open
        new GUI("restaurant", "").setVisible(true);
    }//GEN-LAST:event_CloseButton

    //Add review
    private void RowSelected(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RowSelected
        //Sets model of the table
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        //get content of the row
        String selection = model.getDataVector().elementAt(jTable2.getSelectedRow()).toString();
        //splits content string into parts
        String[] parts = selection.split(", ");
        String part1 = parts[0];
        String result = part1.charAt(1) + "";
        //if row is empty, no reviews
        if (result.equals("-")) {
            Object[] options = {"Yes, please",
                "No, later"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    "Would you like to add a review?",
                    "No reviews",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n == 0) {
                AddReview(evt);
            }
            jButton2.setVisible(false);
        //choose needed review    
        } else {
            //sets review id from the row content
            int review = Integer.parseInt(part1.substring(1) + "");
            setReviewId(review);
            jButton2.setVisible(true);
        }
    }//GEN-LAST:event_RowSelected

    private void jButton3CloseButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3CloseButton
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3CloseButton

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

    }//GEN-LAST:event_jButton2MouseClicked

    //Delete button
    private void DeleteButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButton
        //opens connection
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //opens database file
            conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
            Statement stmt = conn.createStatement();
            //SQL statement to delete certain review
            String query = "DELETE FROM Reviews\n"
                    + "WHERE review_id = '" + getReviewId() + "'";
            //execution of the query; executeUpdate checks if table was changed
            int result = stmt.executeUpdate(query);
            //result returns how many rows were changed/added
            if (result > 0) {
                //if more than 0, then delete query was successful
                //refreshes the page then
                setAvgNum();
                if (getAvgNum() == 0) {
                    jLabel7.setIcon(new ImageIcon("images/0.png"));
                } else if (getAvgNum() == 1) {
                    jLabel7.setIcon(new ImageIcon("images/1.png"));
                } else if (getAvgNum() == 2) {
                    jLabel7.setIcon(new ImageIcon("images/2.png"));
                } else if (getAvgNum() == 3) {
                    jLabel7.setIcon(new ImageIcon("images/3.png"));
                } else if (getAvgNum() == 4) {
                    jLabel7.setIcon(new ImageIcon("images/4.png"));
                } else if (getAvgNum() == 5) {
                    jLabel7.setIcon(new ImageIcon("images/5.png"));
                }
                countReviews();
                tableContent(getRest());
                jButton2.setVisible(false);
                System.out.print("1DELETED");

            } else {
                System.out.print("NOT DELETED");
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

    }//GEN-LAST:event_DeleteButton

    private void AddReview(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddReview
        dispose();
        new AddReview(rest_id, getRest(), getAvgNum()).setVisible(true);
    }//GEN-LAST:event_AddReview

    //Delete Restaurant
    private void deleteRest(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteRest
        //Ask user if he is sure to delete the restaurant
        Object[] options = {"Delete",
            "Cancel"};
        int n = JOptionPane.showOptionDialog(new JFrame(),
                "Would you like to delete the Restaurant?",
                "Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        //if user replies 'delete'
        if (n == 0) {
            //opens connection
            Connection conn = null;
            try {
                Class.forName("org.sqlite.JDBC");
                //opens database file
                conn = DriverManager.getConnection("jdbc:sqlite:Restaurants.db");
                Statement stmt = conn.createStatement();
                //SQL statement to delete certain restaurant
                String query = "DELETE FROM Restaurants\n"
                        + "WHERE rest_id = '" + getRest() + "'";
                //execution of the query; executeUpdate checks if table was changed
                int result = stmt.executeUpdate(query);
                //result returns how many rows were changed/added
                if (result > 0) {
                    //if nore than 0, then delete query was successful
                    dispose();
                    new GUI("restaurant", "Restaurant " + getTitle() + " was successfully deleted!").setVisible(true);
                    System.out.print("1DELETED");
                } else {
                    System.out.print("NOT DELETED");
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
    }//GEN-LAST:event_deleteRest

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
            java.util.logging.Logger.getLogger(Reviews.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reviews.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reviews.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reviews.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reviews().setVisible(true);
                
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
