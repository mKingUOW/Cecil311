package com.scrumSystem.project.productBacklog;

import com.scrumSystem.project.ProjectDetails;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Darryl on 25/05/2016.
 */
public class ProductBacklogController {

    ArrayList<ProdBacklogEntity> backlogs;
    String backlogFile;

    public ProductBacklogController(){
        backlogs = new ArrayList<ProdBacklogEntity>();
        backlogFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "productBacklogs.csv";
        loadBacklogs();
    }

    public int getNextStoryNum(){
        return backlogs.size();
    }


    public void loadBacklogs(){
        backlogs.clear();
        String lineInFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(backlogFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                ProdBacklogEntity temp = new ProdBacklogEntity();
                temp.setProjectName(fields[0]);
                temp.setStoryNumber(Integer.parseInt(fields[1]));
                temp.setTitle(fields[2]);
                temp.setStoryType(fields[3]);
                temp.setDescription(fields[4]);
                temp.setPriority(fields[5]);
                temp.setEffortEstimation(Integer.parseInt(fields[6]));
                temp.setSubType(fields[7]);
                temp.setEpicRef(Integer.parseInt(fields[8]));
                temp.setCompleteionStatus(fields[9]);
                temp.setAssignedToSprint(Integer.parseInt(fields[10]));

                backlogs.add(temp);
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveBacklogs(){
        try{
            PrintWriter writer = new PrintWriter(backlogFile);
            for(int i = 0; i<backlogs.size(); i++){
                writer.println(backlogs.get(i).toCSV());
            }
            writer.close();
        }catch(Exception err){
            System.out.println(err);
        }
    }

    public void addBacklog(ProdBacklogEntity p){
        loadBacklogs();
        backlogs.add(p);
        saveBacklogs();
    }

    public void modifyBacklog(ProdBacklogEntity p){
        backlogs.clear();
        loadBacklogs();


        for(int i = 0; i<backlogs.size(); i++){
            if(backlogs.get(i).getStoryNumber() == p.getStoryNumber()){

                System.out.println("PBC82: found");
                ProdBacklogEntity temp = backlogs.get(i);
                temp.setProjectName(p.getProjectName());
                temp.setStoryNumber(p.getStoryNumber());
                temp.setTitle(p.getTitle());
                temp.setStoryType(p.getStoryType());
                temp.setDescription(p.getDescription());
                temp.setPriority(p.getPriority());
                temp.setEffortEstimation(p.getEffortEstimation());
                temp.setSubType(p.getSubType());
                temp.setEpicRef(p.getEpicRef());
                temp.setCompleteionStatus(p.getCompleteionStatus());
                temp.setAssignedToSprint(p.getAssignedToSprint());
            }
        }
        saveBacklogs();
    }

    public ProdBacklogEntity getBacklog(int id){
        backlogs.clear();
        loadBacklogs();
        for(int i = 0; i<backlogs.size(); i++){
            if(backlogs.get(i).getStoryNumber() == id){
                return backlogs.get(i);
            }
        }
        return null;
    }

    public ArrayList<Integer> getProductBacklogIDs(){
        backlogs.clear();
        loadBacklogs();
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for(int i = 0; i<backlogs.size(); i++){
            ret.add(backlogs.get(i).getStoryNumber());
        }
        return ret;
    }
}
