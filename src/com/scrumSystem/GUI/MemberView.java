package com.scrumSystem.GUI;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Darryl on 12/05/2016.
 */
public class MemberView extends JPanel {

    private JPanel currentView;
    private String username;
    private String password;
    private String userType;
    private String activeProject;
    private String firstName;
    private String lastName;
    private String email;
    private String UAFile;

    /**
     * Buffered reader object to read from files
     */
    private BufferedReader reader;


    public MemberView(){

        currentView = this;
        username = "";
        password = "";
        userType = "";
        activeProject = "";
        firstName = "";
        lastName = "";
        email = "";
        UAFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userAccounts.csv";
    }

    public void setCurrentView(JPanel next){
        currentView = next;
    }
     public JPanel getCurrentView(){
         return currentView;
     }

    public void deselectAllNavigatorButtons(){
        //overriden by sub-class
    }

    public void setUsername(String u){
        username = u;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        String lineInFile;
        String none = "No password found";
        try {
            reader = new BufferedReader(new FileReader(UAFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                System.out.print(fields[1]);
                if (this.getUsername().equals(fields[0])) {
                    System.out.print(password);
                    password = fields[1];
                    System.out.print("Am i here");
                    return password;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return none;
    }
}
