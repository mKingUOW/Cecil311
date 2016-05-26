package com.scrumSystem.project;

import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Darryl on 25/05/2016.
 */
public class SprintController {

    private ArrayList<SprintBacklogEntity> sprintbls;
    private String sprintblsFile;

    public SprintController(){
        sprintbls = new ArrayList<SprintBacklogEntity>();
        sprintblsFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "sprintBacklogs.csv";
        loadSprintBLs();
    }

    public void addSprintBL(SprintBacklogEntity s){
        sprintbls.clear();
        loadSprintBLs();
        sprintbls.add(s);
        saveSprintBLs();
    }

    public int getNextID(){
        return sprintbls.size();
    }

    public ArrayList<SprintBacklogEntity> getSprintBLsFromSprint(int id){
        loadSprintBLs();
        ArrayList<SprintBacklogEntity> ret = new ArrayList<SprintBacklogEntity>();
        for(int i = 0; i< sprintbls.size(); i++){
            System.out.println("SPC41: " + sprintbls.get(i).getSprintID() + ":" + id);
            if(sprintbls.get(i).getSprintID() == id){
                ret.add(sprintbls.get(i));
            }
        }
        return ret;
    }

    public ArrayList<SprintBacklogEntity> getSprintBoardBLs(int sprintID, String username){
        ArrayList<SprintBacklogEntity> ret = new ArrayList<SprintBacklogEntity>();
        for(int i = 0; i<sprintbls.size(); i++){
            System.out.println(sprintbls.get(i).getSprintID() + ":" + sprintID);
            System.out.println(sprintbls.get(i).getAssignedUser() + ":" + username);
            if(sprintbls.get(i).getSprintID() == sprintID && sprintbls.get(i).getAssignedUser().equals(username)){
                ret.add(sprintbls.get(i));
            }
        }
        return ret;
    }

    public void modifySprintBL(SprintBacklogEntity s){
        sprintbls.clear();
        loadSprintBLs();
        for(int i = 0; i<sprintbls.size(); i++){
            if(sprintbls.get(i).getIssueID() == s.getIssueID()){
                SprintBacklogEntity temp = sprintbls.get(i);
                temp.setProjectName(s.getProjectName());
                temp.setSprintID(s.getSprintID());
                temp.setIssueID(s.getIssueID());
                temp.setDescription(s.getDescription());
                temp.setIssueType(s.getIssueType());
                temp.setPriority(s.getPriority());
                temp.setStoryLink(s.getStoryLink());
                temp.setStoryPoints(s.getStoryPoints());
                temp.setCompletionStatus(s.getCompletionStatus());
                temp.setAssignedUser(s.getAssignedUser());
                temp.setDateStarted(s.getDateStarted());
                temp.setDateEnded(s.getDateEnded());
            }
        }
        saveSprintBLs();
    }

    public void removeSprintBl(SprintBacklogEntity s){
        for(int i = 0; i<sprintbls.size(); i++){
            if(sprintbls.get(i).getIssueID() == s.getIssueID()){
                sprintbls.remove(i);
            }
        }
        saveSprintBLs();
    }


    public void saveSprintBLs() {
        try {
            PrintWriter writer = new PrintWriter(sprintblsFile);
            for (int i = 0; i < sprintbls.size(); i++) {
                writer.println(sprintbls.get(i).toCSV());
            }
            writer.close();
        } catch (Exception err) {
            System.out.println(err);
        }

    }

    public void loadSprintBLs(){
        sprintbls.clear();
        String lineInFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(sprintblsFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                SprintBacklogEntity temp = new SprintBacklogEntity();

                temp.setProjectName(fields[0]);
                temp.setSprintID(Integer.parseInt(fields[1]));
                temp.setIssueID(Integer.parseInt(fields[2]));
                temp.setDescription(fields[3]);
                temp.setIssueType(fields[4]);
                temp.setPriority(fields[5]);
                temp.setStoryLink(Integer.parseInt(fields[6]));
                temp.setStoryPoints(Integer.parseInt(fields[7]));
                temp.setCompletionStatus(fields[8]);
                temp.setAssignedUser(fields[9]);
                temp.setDateStarted(fields[10]);
                temp.setDateEnded(fields[11]);

                sprintbls.add(temp);
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(sprintbls.size() + " SprintBacklogs in system");
    }


}
