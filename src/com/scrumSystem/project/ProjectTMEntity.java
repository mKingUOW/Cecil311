package com.scrumSystem.project;

import java.util.ArrayList;

/**
 * Created by Darryl on 25/05/2016.
 */
public class ProjectTMEntity {

    private String projectName;
    private ArrayList<String> TMs;

    public ProjectTMEntity(String pName){
        projectName = pName;
        TMs = new ArrayList<String>();
    }

    public void setProjectName(String p){
        projectName = p;
    }

    public String getProjectName(){
        return projectName;
    }

    public void addTM(String po){
        TMs.add(po);
    }

    public void removeTM(String id){
        for(int i = 0; i< TMs.size(); i++){
            if(TMs.get(i).equals(id)){
                TMs.remove(i);
            }
        }
    }

    public ArrayList<String> getTMs(){
        return TMs;
    }

    public void clearTMs(){
        TMs.clear();
    }

    public String toCSV(){
        String ret = projectName + ",";
        for(int i = 0; i<TMs.size(); i++){
            if(i == TMs.size()-1){
                ret = ret + TMs.get(i);
            }
            else{
                ret = ret + TMs.get(i) + ",";
            }
        }
        return ret;
    }
}
