package com.scrumSystem.project;

import com.scrumSystem.project.sprintBacklog.CommentEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Darryl on 25/05/2016.
 */
public class CommentController {

    private ArrayList<CommentEntity> allComments;
    private String commentsFile;

    public CommentController(){
        allComments = new ArrayList<CommentEntity>();
        commentsFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "comments.csv";

        loadComments();
    }

    public void loadComments(){
        String lineInFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(commentsFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                CommentEntity temp = new CommentEntity();
                temp.setProjectName(fields[0]);
                temp.setIssueNumber(Integer.parseInt(fields[1]));
                temp.setComment(fields[2]);
                temp.setUsername(fields[3]);
                temp.setDate(fields[4]);
                temp.setIssueType(fields[5]);
                temp.setSprintID(Integer.parseInt(fields[6]));
                allComments.add(temp);
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveComments(){
        try{
            PrintWriter writer = new PrintWriter(commentsFile);
            for(int i = 0; i<allComments.size(); i++){
                writer.println(allComments.get(i).toCSV());
            }
            writer.close();
        }catch(Exception err){
            System.out.println(err);
        }
    }

    public void createComment(CommentEntity c){
        allComments.clear();
        loadComments();
        allComments.add(c);
        saveComments();
    }

    public ArrayList<CommentEntity> getCommentsByIssue(int issueNum, String issType){
        ArrayList<CommentEntity> ret = new ArrayList<CommentEntity>();
        for(int i = 0; i<allComments.size(); i++){
            if(allComments.get(i).getIssueNumber() == issueNum && allComments.get(i).getIssueType().equals(issType)){
                ret.add(allComments.get(i));
            }
        }
        return ret;
    }
}
