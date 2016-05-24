package com.scrumSystem.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Darryl on 24/05/2016.
 */
public class ProjectController {

    private ArrayList<ProjectDetails> allProjects;
    private String projectFile;

    public ProjectController(){
       // System.out.println("new pc");
        allProjects = new ArrayList<ProjectDetails>();
        projectFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "project.csv";
        this.loadProjects();
    }

    public ProjectDetails getProject(String id){
        for(int i = 0; i<allProjects.size(); i++){
            if(id.equals(allProjects.get(i).getName())){
                return allProjects.get(i);
            }
        }
        return null;
    }

    public void loadProjects(){

            String lineInFile;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(projectFile));
                lineInFile = reader.readLine();

                while (lineInFile != null) {
                    String [] fields = lineInFile.split(",");
                    ProjectDetails temp = new ProjectDetails();
                    temp.loadFromDB(fields[0]);
                    allProjects.add(temp);
                    //read the next line in the file
                    lineInFile = reader.readLine();
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        System.out.println(allProjects.size() + " projects in system");
    }
}