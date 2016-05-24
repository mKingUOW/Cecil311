package com.scrumSystem.project;

import java.util.ArrayList;

/**
 * Created by Darryl on 25/05/2016.
 */
public class ProjectPOEntity {

    private String projectName;
    private ArrayList<String> POs;

    public ProjectPOEntity(String pName){
        projectName = pName;
        POs = new ArrayList<String>();
    }

    public void setProjectName(String p){
        projectName = p;
    }

    public String getProjectName(){
        return projectName;
    }

    public void addPO(String po){
        POs.add(po);
    }

    public void removePO(String id){
        for(int i = 0; i< POs.size(); i++){
            if(POs.get(i).equals(id)){
                POs.remove(i);
            }
        }
    }

    public ArrayList<String> getPOs(){
        return POs;
    }

    public void clearPOs(){
        POs.clear();
    }

    public String toCSV(){
        String ret = projectName + ",";
        for(int i = 0; i<POs.size(); i++){
            if(i == POs.size()-1){
                ret = ret + POs.get(i);
            }
            else{
                ret = ret + POs.get(i) + ",";
            }
        }
        return ret;
    }
}
