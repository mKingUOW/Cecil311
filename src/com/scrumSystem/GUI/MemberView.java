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
    private String userDetails;

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
        userDetails = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userDetails.csv";
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
        return this.password;
    }

    public String getUserType(){
        return userType;
    }

    public String getActiveProject(){
        return activeProject;
    }


    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getEmail(){
        return this.email;
    }
    public void setAccountDetails(){
        String lineInFile;
        try {
            reader = new BufferedReader(new FileReader(UAFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                if (this.username.equals(fields[0])) {
                    this.password = fields[1];
                    this.userType = fields[2];
                    this.activeProject = fields[3];
                    reader.close();
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            reader = new BufferedReader(new FileReader(userDetails));
            lineInFile = reader.readLine();

            while (lineInFile != null){
                String [] fields = lineInFile.split(",");
                if (this.username.equals(fields[0])){
                    this.firstName = fields[1];
                    this.lastName = fields[2];
                    this.email = fields[3];
                    reader.close();
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
