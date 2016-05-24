package com.scrumSystem.project;

import com.scrumSystem.user.UserController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Darryl on 24/05/2016.
 */
public class ProjectController {

    private ArrayList<ProjectDetails> allProjects;
    private String projectFile;

    public ProjectController(){
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

    public void saveProject(ProjectDetails p){
        for(int i = 0; i<allProjects.size(); i++){
            if(allProjects.get(i).getName().equals(p.getName())){
                System.out.println("saving project " + allProjects.get(i).getName());
                allProjects.get(i).setName(p.getName());
                allProjects.get(i).setStartDate(p.getStartDate());
                allProjects.get(i).setEndDate(p.getEndDate());
                allProjects.get(i).setStoryPointValue(p.getStoryPointValue());
                allProjects.get(i).setScrumMaster(p.getScrumMaster());
                allProjects.get(i).setDurationOfSprint(p.getDurationOfSprint());
                allProjects.get(i).setCurrentSprint(p.getCurrentSprint());
            }
        }
        saveAllProjects();
    }

    public void createProject(String name, String sm ){
        ProjectDetails temp = new ProjectDetails(name,sm);

        UserController uc = new UserController();
        uc.assignUserToProject(sm,name);

        allProjects.add(temp);
        saveAllProjects();
    }

    public void saveAllProjects(){
        try{
            PrintWriter writer = new PrintWriter(projectFile);
            for(int i = 0; i<allProjects.size(); i++){
                writer.println(allProjects.get(i).toCSV());
            }
            writer.close();
        }catch(Exception err){
            System.out.println(err);
        }

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
