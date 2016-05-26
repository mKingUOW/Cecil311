package com.scrumSystem.GUI;

import com.scrumSystem.project.productBacklog.ProdBacklogEntity;
import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Darryl on 7/05/2016.
 */
public class CreateSprintBacklogItemView extends JPanel{
    private JFrame parentFrame;
    private MemberView parentPanel;
    //private JPanel productBacklogView;
    private JPanel returnView;
    private JPanel currentView;
    JPanel southLayoutPanel;
    JPanel westLayoutPanel;
    JPanel centerLayoutPanel;
    JPanel centerLeftLayoutPanel;
    JPanel centerRightLayoutPanel;
    JLabel header;
    IssueButton exit;
    IssueButton create;
    String mode;

    //private JTextField titleField;
    private JComboBox<String> typeOptions;
    //private JComboBox<String> subTypeOptions;
    private JTextField storyPointsField;
    private JComboBox<String> priorityOptions;
    private int modifiyStoryID;

    private String selectedBacklogItem; //will eventually be backlog object

    private JTextArea descArea;
    private MyScollPanel myScollPanel;

    public CreateSprintBacklogItemView(String m,JFrame f, JPanel ret, MemberView pp, MyScollPanel s){
        parentFrame = f;
        parentPanel = pp;
        //productBacklogView = pbv;
        returnView = ret;
        currentView = this;
        mode = m;
        myScollPanel = s;
        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());
        setBackground(Color.decode("#EBF0F2"));

        //  HEADER - THIS.BORDERLAYOUT.NORTH
        if(mode.equals("MODIFY")){
            header = new JLabel("Modify Task");
        }
        else{
            header = new JLabel("Create New Task");
        }


        header.setBackground(Color.decode("#EBF0F2"));

        //  BUTTONS - THIS.BORDERLAYOUT.SOUTH
        southLayoutPanel = new JPanel();
        southLayoutPanel.setBackground(Color.decode("#EBF0F2"));
        southLayoutPanel.setLayout(new GridLayout(1,4));

        exit = new IssueButton("Exit",parentFrame,this);
        if(mode.equals("CREATE")){
            create = new IssueButton("Create",parentFrame,this);
        }
        else if(mode.equals("MODIFY")){
            create = new IssueButton("Modify",parentFrame,this);
        }


        //NEED TO UPDATE TEAMMEMBERVIEW.SETCURRENTVIEW()
        //EXIT BUTTON - add onclick listener to newIssue button
        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int res = JOptionPane.showConfirmDialog(parentFrame,"Issue will not be saved. Proceed? ","Cancel Creation of Issue",JOptionPane.OK_CANCEL_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    parentFrame.remove(parentPanel.getCurrentView());
                    parentFrame.add(returnView);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    parentPanel.setCurrentView(returnView);
                    //currentView = productBacklogView;
                    //NEED TO UPDATE TEAMMEMBERVIEW.SETCURRENTVIEW()
                }
            }
        });

        //CREATE BUTTON - add onclick listener to newIssue button
        create.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //save issue
                SprintBacklogEntity temp = new SprintBacklogEntity();
                temp.setProjectName(parentPanel.getActiveProj());
                temp.setSprintID(parentPanel.sc.getCurrentSprint());
                temp.setDescription(descArea.getText());
                temp.setIssueType((String)typeOptions.getSelectedItem());
                temp.setPriority((String)priorityOptions.getSelectedItem());
                //link to story
                temp.setStoryPoints(Integer.parseInt(storyPointsField.getText()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                temp.setDateStarted(dateFormat.format(date));

                //temp.setStoryNumber(modifiyStoryID);

                //get selected epic
                //temp.setEpicRef(Integer.parseInt(fields[8]));

                if(mode.equals("CREATE")){
                    parentPanel.sc.createSprintBL(temp);
                    myScollPanel.addElement(temp);
                    myScollPanel.update();
                }
                else if(mode.equals("MODIFY")){
                    parentPanel.sc.modifySprintBL(temp);
                    myScollPanel.update();
                    //returnView.updatePBList();
                }


                //reset view
                parentFrame.remove(parentPanel.getCurrentView());
                parentFrame.add(returnView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(returnView);
                //currentView = productBacklogView;
                //NEED TO UPDATE TEAMMEMBERVIEW.SETCURRENTVIEW()
            }
        });

        //WEST - this.borderLayout.west
        westLayoutPanel = new JPanel();
        westLayoutPanel.setBackground(Color.decode("#EBF0F2"));
        westLayoutPanel.setLayout(new GridLayout(7,1));

        //JLabel title = new JLabel("Title: ", SwingConstants.CENTER);
        JLabel desc = new JLabel("Description: ", SwingConstants.CENTER);
        JLabel priority = new JLabel("Priority: ",SwingConstants.CENTER);
        JLabel type = new JLabel("Issue Type: ",SwingConstants.CENTER);
        //JLabel subType = new JLabel("Sub Type: ",SwingConstants.CENTER);
        JLabel storyPoints = new JLabel("Story Points: ",SwingConstants.CENTER);
        JLabel relatedBacklogItem = new JLabel("Link to story: ",SwingConstants.CENTER);

        //westLayoutPanel.add(title);
        westLayoutPanel.add(desc);
        westLayoutPanel.add(priority);
        westLayoutPanel.add(type);
       // westLayoutPanel.add(subType);
        westLayoutPanel.add(storyPoints);
        westLayoutPanel.add(relatedBacklogItem);

        // CENTER - this.borderLayout.center
        centerLayoutPanel = new JPanel();
        centerLayoutPanel.setBackground(Color.decode("#EBF0F2"));
        centerLayoutPanel.setLayout(new GridLayout(1,2));

        // CENTER LEFT
        centerLeftLayoutPanel = new JPanel();
        centerLeftLayoutPanel.setBackground(Color.decode("#EBF0F2"));
        centerLeftLayoutPanel.setLayout(new GridLayout(7,1));

        //elements in center left panel
        //titleField = new JTextField();
        descArea = new JTextArea(4,52);
        String[] optionsArray = new String[]{"Blocked","Critical","High", "Moderate","Low"};
        priorityOptions = new JComboBox<String>(optionsArray);
        String[] typeArray = new String[]{"Bug","Task","Improvement"};
        typeOptions = new JComboBox<String>(typeArray);
        //String[] subTypeArray = new String[]{"Bug", "Extension", "Feature"};
        //subTypeOptions = new JComboBox<String>(subTypeArray);
        storyPointsField = new JTextField();
        JRadioButton yes = new JRadioButton("Yes");
        JRadioButton no = new JRadioButton("No");

        //CENTER RIGHT - needs to be instantiated here for layoutLeft
        centerRightLayoutPanel = new JPanel();
        centerRightLayoutPanel.setBackground(Color.decode("#EBF0F2"));
        centerRightLayoutPanel.setLayout(new BorderLayout());
        ScrollPanel scrollPanel = new ScrollPanel(null);
        BacklogScrollPane backlogScrollPane = new BacklogScrollPane(450,500);
        backlogScrollPane.setScrollPanel(scrollPanel);
        JLabel rightHeader = new JLabel("Choose related issue: ");


        //CENTER LEFT
        //TextFieldLayout titleLayout = new TextFieldLayout(titleField);
        //centerLeftLayoutPanel.add(titleLayout);
        centerLeftLayoutPanel.add(descArea);
        ComboBoxLayout poLayout = new ComboBoxLayout(priorityOptions);
        centerLeftLayoutPanel.add(poLayout);
        ComboBoxLayout tLayout = new ComboBoxLayout(typeOptions);
        centerLeftLayoutPanel.add(tLayout);
        //ComboBoxLayout stLayout = new ComboBoxLayout(subTypeOptions);
        //centerLeftLayoutPanel.add(stLayout);
        TextFieldLayout tfLayout = new TextFieldLayout(storyPointsField);
        centerLeftLayoutPanel.add(tfLayout);
        ButtonGroupLayout buttonGroupLayout = new ButtonGroupLayout(yes,no,backlogScrollPane,rightHeader);
        centerLeftLayoutPanel.add(buttonGroupLayout);



        centerRightLayoutPanel.setBorder(BorderFactory.createEtchedBorder());
        centerRightLayoutPanel.add(rightHeader,BorderLayout.NORTH);
        centerRightLayoutPanel.add(backlogScrollPane,BorderLayout.CENTER);
        rightHeader.setVisible(false);
        backlogScrollPane.setVisible(false);

        centerLayoutPanel.add(centerLeftLayoutPanel);
        centerLayoutPanel.add(centerRightLayoutPanel);

        //add to this.borderlayout.north
        add(header,BorderLayout.NORTH);

        //add buttons and dummy jlabels to southLayoutPanel
        southLayoutPanel.add(new JLabel(), BorderLayout.SOUTH);
        southLayoutPanel.add(create, BorderLayout.SOUTH);
        southLayoutPanel.add(exit, BorderLayout.SOUTH);
        southLayoutPanel.add(new JLabel(), BorderLayout.SOUTH);

        //add westLayoutPanel = this.borerLayout.west
        add(westLayoutPanel,BorderLayout.WEST);

        //add centerLayoutPanel to this.borderlayout.center
        add(centerLayoutPanel,BorderLayout.CENTER);

        //add southPanelLayout to this.borderlayout.south
        add(southLayoutPanel,BorderLayout.SOUTH);
    }

    public void setupModifyBacklogItem(ProdBacklogEntity p){
        // ProdBacklogEntity temp = new ProdBacklogEntity();
        //titleField.setText(p.getTitle());
        typeOptions.setSelectedItem(p.getStoryType());
        descArea.setText(p.getDescription());
        priorityOptions.setSelectedItem(p.getPriority());
        storyPointsField.setText(Integer.toString(p.getEffortEstimation()));
        //subTypeOptions.setSelectedItem(p.getSubType());
        modifiyStoryID = p.getStoryNumber();
    }

}
