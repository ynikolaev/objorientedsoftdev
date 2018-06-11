/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentdemo;

/**
 *
 * @author ynikolaev
 */
public class AssignmentDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //initialize application, starts GUI class
        //GUI contains 2 attributes String and String
        //1 String - what shortcut to open
        //2 String - what message to pass to GUI class
        GUI theWindow = new GUI("menu", "");
        theWindow.setVisible(true);

    }

}
