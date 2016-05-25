package com.scrumSystem.project;

import com.scrumSystem.user.UserController;
import com.scrumSystem.user.UserEntity;

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
    private ArrayList<ProjectPOEntity> allProjectPOs;
    private ArrayList<ProjectTMEntity> allProjectTMs;
    private String projectFile;
    private String poFile;
    private String tmFile;

    public ProjectController(){
        allProjects = new ArrayList<ProjectDetails>();
        allProjectPOs = new ArrayList<ProjectPOEntity>();
        allProjectTMs = new ArrayList<ProjectTMEntity>();
        projectFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "project.csv";
        poFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "projectPOs.csv";
        tmFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "projectTMs.csv";
        this.loadProjects();
        loadProjectPOs();
        loadProjectTMs();
    }

    public  ArrayList<String> getAllPorjectNames(){
        ArrayList<String> ret = new ArrayList<String>();
        for(int i = 0; i<allProjects.size(); i++){
            ret.add(allProjects.get(i).getName());
        }
        return ret;
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

        createProjectPO(name);
        createProjectTM(name);

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


    /* ------------------ porjectPOs.csv functions --------------------- */

    public void createProjectPO(String proj){
        ProjectPOEntity poe = new ProjectPOEntity(proj);
        allProjectPOs.add(poe);
        saveProjectPOs();
    }

    public void saveProjectPOs(){
        try{
            PrintWriter writer = new PrintWriter(poFile);
            for(int i = 0; i<allProjectPOs.size(); i++){
                if(i == allProjectPOs.size()-1){
                    writer.print(allProjectPOs.get(i).toCSV());
                }
                else{
                    writer.println(allProjectPOs.get(i).toCSV());
                }

            }
            writer.close();
        }catch(Exception err){
            System.out.println(err);
        }
    }

    //loads all projectPOs into POs array
    public void loadProjectPOs(){
        allProjectPOs.clear();
        String lineInFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(poFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                ProjectPOEntity temp = new ProjectPOEntity(fields[0]);
                for(int i = 1; i<fields.length; i++){
                    temp.addPO(fields[i]);
                }
                allProjectPOs.add(temp);
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(allProjectPOs.size() + " projectPOs in system");
    }

    public void clearPOsFromProject(String proj){
        for(int i = 0; i<allProjectPOs.size(); i++){
            if(allProjectPOs.get(i).getProjectName().equals(proj)){
                allProjectPOs.get(i).clearPOs();
            }
        }
        saveProjectPOs();
    }

    public void addPOtoProject(String proj, String po){
        for(int i = 0; i<allProjectPOs.size(); i++){
            System.out.println("pc126: " + allProjectPOs.get(i).getProjectName() + ":" + proj);
            if(allProjectPOs.get(i).getProjectName().equals(proj)){
                allProjectPOs.get(i).addPO(po);
                System.out.println("pc128: " + allProjectPOs.get(i).toCSV());
            }
        }

        saveProjectPOs();
    }

    public ProjectPOEntity getPOsByProject(String proj){
        loadProjectPOs();
        for(int i = 0; i<allProjectPOs.size(); i++){
            if(allProjectPOs.get(i).getProjectName().equals(proj)){
               return allProjectPOs.get(i);
            }
        }
        return null;
    }


    /* ------------------ porjectTMs.csv functions --------------------- */

    public void createProjectTM(String proj){
        ProjectTMEntity ptme = new ProjectTMEntity(proj);
        allProjectTMs.add(ptme);
        saveProjectTMs();
    }

    public void saveProjectTMs(){
        try{
            PrintWriter writer = new PrintWriter(tmFile);
            for(int i = 0; i<allProjectTMs.size(); i++){
                if(i == allProjectTMs.size()-1){
                    writer.print(allProjectTMs.get(i).toCSV());
                }
                else{
                    writer.println(allProjectTMs.get(i).toCSV());
                }

            }
            writer.close();
        }catch(Exception err){
            System.out.println(err);
        }
    }

    //loads all projectTMs into TMs array
    public void loadProjectTMs(){
        allProjectTMs.clear();
        String lineInFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(tmFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                ProjectTMEntity temp = new ProjectTMEntity(fields[0]);
                for(int i = 1; i<fields.length; i++){
                    temp.addTM(fields[i]);
                }
                allProjectTMs.add(temp);
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(allProjectTMs.size() + " projectTMs in system");
    }

    public void addTMtoProject(String proj, String tm){
        for(int i = 0; i<allProjectTMs.size(); i++){
            System.out.println("pc164: " + allProjectTMs.get(i).getProjectName() + ":" + proj);
            if(allProjectTMs.get(i).getProjectName().equals(proj)){
                allProjectTMs.get(i).addTM(tm);
                System.out.println("pc167: " + allProjectTMs.get(i).toCSV());
            }
        }

        saveProjectTMs();
    }

    public void clearTMsFromProject(String proj){
        for(int i = 0; i<allProjectTMs.size(); i++){
            if(allProjectTMs.get(i).getProjectName().equals(proj)){
                allProjectTMs.get(i).clearTMs();
            }
        }
        saveProjectTMs();
    }

    //returns ProjectTMEntity for a given project
    public ProjectTMEntity getTMsByProject(String proj){
        loadProjectTMs();
        for(int i = 0; i<allProjectTMs.size(); i++){
            if(allProjectTMs.get(i).getProjectName().equals(proj)){
                return allProjectTMs.get(i);
            }
        }
        return null;
    }

}
