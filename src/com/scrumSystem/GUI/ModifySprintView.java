package com.scrumSystem.GUI;

import com.scrumSystem.project.productBacklog.ProdBacklogEntity;
import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Darryl on 18-May-16.
 */
public class ModifySprintView extends JPanel {

    private JFrame parentFrame;
    private JPanel returnView;
    private JPanel currentView;
    private MemberView parentPanel;

    private int sprintID;

    public ModifySprintView(JFrame p, JPanel ret, MemberView pp){
        parentFrame = p;
        returnView = ret;
        currentView = this;
        parentPanel = pp;

       // prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        //header
        JLabel headerLabel = new JLabel("Modify Sprint" + sprintID, SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(100,40));


        //exit panel - south
        JPanel southLayout = new JPanel();
        southLayout.setLayout(new GridBagLayout());
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(currentView);
                parentFrame.add(returnView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(returnView);
            }
        });
        southLayout.add(exitButton);

        //center panel
        JPanel centerLayoutPanel = new JPanel();
        centerLayoutPanel.setLayout(new GridLayout(1,2));

        //center left panel
        JPanel centerLeftLayout = new JPanel();
        centerLeftLayout.setLayout(new BorderLayout());
        JLabel centerLeftHeader = new JLabel("Product Backlog", SwingConstants.CENTER);
        centerLeftLayout.add(centerLeftHeader,BorderLayout.NORTH);

        BacklogScrollPane pbScrollPane = new BacklogScrollPane(1000,1000);
        ModifySprintScrollPanel prodBacklogPanel = new ModifySprintScrollPanel("PRODUCTBACKLOG",sprintID,currentView,parentFrame,parentPanel);
        prodBacklogPanel.loadProductBacklog();
        pbScrollPane.setScrollPanel(prodBacklogPanel);
        centerLeftLayout.add(pbScrollPane,BorderLayout.CENTER);
        centerLayoutPanel.add(centerLeftLayout);


        //center right panel
        JPanel centerRightLayout = new JPanel();
        centerRightLayout.setLayout(new BorderLayout());
        JLabel centerRightHeader = new JLabel("Sprint Backlog", SwingConstants.CENTER);
        centerRightLayout.add(centerRightHeader,BorderLayout.NORTH);
        BacklogScrollPane sbScrollPane = new BacklogScrollPane(1000,1000);
        ModifySprintScrollPanel sprintBacklogPanel = new ModifySprintScrollPanel("SPRINTBACKLOG",sprintID,currentView,parentFrame,parentPanel);
        sprintBacklogPanel.loadSprintBacklog(sprintID);
        sbScrollPane.setScrollPanel(sprintBacklogPanel);
        centerRightLayout.add(sbScrollPane,BorderLayout.CENTER);
        centerLayoutPanel.add(centerRightLayout);


        //link scroll panels
        prodBacklogPanel.setPartner(sprintBacklogPanel);
        sprintBacklogPanel.setPartner(prodBacklogPanel);



        add(headerLabel,BorderLayout.NORTH);
        add(centerLayoutPanel,BorderLayout.CENTER);
        add(southLayout,BorderLayout.SOUTH);

    }

    public void setSprintID(int i){
        sprintID = i;
    }
}

class ModifySprintScrollPanel extends JPanel{
    private ArrayList<JTextArea> UIarray;
    private ArrayList<String> sprintBacklogData;

    private ModifySprintScrollPanel partnerPanel;
    private JPanel currentView;
    private JFrame parentFrame;
    private MemberView parentPanel;

    private String mode;
    private int sprintID;

    private Boolean wasDoubleClick = false;
    private Timer timer;

    public ModifySprintScrollPanel(String m,int id, JPanel curr, JFrame p, MemberView pp){
        partnerPanel = null;
        parentPanel = pp;
        currentView = curr;
        parentFrame = p;
        mode = m;
        sprintID = id;
        UIarray = new ArrayList<JTextArea>();
        sprintBacklogData = new ArrayList<String>();
    }

    public void setPartner(ModifySprintScrollPanel p){
        partnerPanel = p;
    }

    public void loadProductBacklog(){

        ArrayList<Integer> pb = parentPanel.sc.getProductBacklogIDs();
        //add all elements from product backlog into ui
        for(int i = 0; i<pb.size(); i++){
            ProdBacklogEntity pbe = parentPanel.sc.getBacklog(pb.get(i));
            //check if pb item has already been assigned to a sprint
            if(pbe.getAssignedToSprint() == -1){
                addPBElement(pb.get(i));
            }

        }

        //repaint
        revalidate();
        repaint();
    }

    public void loadSprintBacklog(int sprintID){
        //load sprint backlog data from DB into array
        removeAll();
        ArrayList<SprintBacklogEntity> bls = parentPanel.sc.getSprintBLsFromSprint(sprintID);
        System.out.println("MSV163: load sprint backlog for sprint " + sprintID + "size: " + bls.size() );
        for(int i = 0; i<bls.size(); i++){
            //if(bls.get(i).getAssignedUser().equals("none")){
                addSBElement(bls.get(i));
            //}

        }
        updateView();

    }


    public void addPBElement(int id){

        ProdBacklogEntity bl = parentPanel.sc.getBacklog(id);
        bl.setAssignedToSprint(-1);
        parentPanel.sc.modifyBacklog(bl);
        final ProductBacklogTextArea temp = new ProductBacklogTextArea(bl);

        UIarray.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - UIarray.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + UIarray.size(),1));
        }
        else{
            setLayout(new GridLayout(UIarray.size(),1));
        }


        //reference: http://stackoverflow.com/questions/548180/java-ignore-single-click-on-double-click
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("double clicked");

                    if(mode.equals("PRODUCTBACKLOG")){
                        DetailedBacklogItemView detailedBacklogItemView = new DetailedBacklogItemView(parentFrame,currentView,parentPanel);
                        detailedBacklogItemView.setPBE(temp.getBacklogEntity());
                        detailedBacklogItemView.displayDesc();
                        detailedBacklogItemView.displayComments();
                        parentFrame.remove(parentPanel.getCurrentView());
                        parentFrame.add(detailedBacklogItemView);
                        parentFrame.revalidate();
                        parentFrame.repaint();
                        parentPanel.setCurrentView(detailedBacklogItemView);
                    }

                    wasDoubleClick = true;
                }else{
                    Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty( "awt.multiClickInterval");
                    timer = new Timer(timerinterval.intValue(), new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (wasDoubleClick) {
                                wasDoubleClick = false; // reset flag
                            } else {

                                if(mode.equals("PRODUCTBACKLOG")){

                                    if(sprintID > parentPanel.sc.getCurrentSprint()){
                                        ProdBacklogEntity s = temp.getBacklogEntity();
                                        SprintBacklogEntity sbe = new SprintBacklogEntity();
                                        sbe.setProjectName(s.getProjectName());
                                        sbe.setSprintID(sprintID);
                                        sbe.setIssueID(parentPanel.sc.getNewestStoryId());
                                        sbe.setDescription(s.getDescription());
                                        sbe.setIssueType(s.getSubType());
                                        sbe.setPriority(s.getPriority());
                                        sbe.setStoryLink(s.getStoryNumber());
                                        sbe.setStoryPoints(s.getEffortEstimation());
                                        sbe.setCompletionStatus("ToDo");
                                        sbe.setAssignedUser("none");
                                        //sbe.setDateStarted(s.get);
                                        //sbe.setDateEnded(s.getDateEnded());

                                        parentPanel.sc.createSprintBL(sbe);
                                        //assign pbi to sprint
                                        System.out.println("MSV248: assigning proj to sprint " + sprintID);
                                        temp.getBacklogEntity().setAssignedToSprint(sprintID);
                                        parentPanel.sc.modifyBacklog(temp.getBacklogEntity());

                                        partnerPanel.addSBElement(sbe);
                                        partnerPanel.updateView();
                                        remove(temp);
                                        updateView();
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(parentFrame, "Sprint" + sprintID + " has already started or has ended. You cannot modify the sprint backlog");
                                    }


                                }

                                /*
                                descPanel.displayIssue(temp.getBacklogEntity());
                                selectedIssue = temp.getBacklogEntity().getStoryNumber();
                                commentsPanel.load(selectedIssue);
                                //remove(temp);
                                update();
                                */
                            }
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }

            }
        });

        add(temp);
    }

    public void addSBElement(SprintBacklogEntity s){
        parentPanel.sc.modifySprintBL(s);
        final SprintBLTextArea temp = new SprintBLTextArea(s);


        UIarray.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - UIarray.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + UIarray.size(),1));
        }
        else{
            setLayout(new GridLayout(UIarray.size(),1));
        }


        //reference: http://stackoverflow.com/questions/548180/java-ignore-single-click-on-double-click
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("double clicked");
                    if(mode.equals("SPRINTBACKLOG")){
                        DetailedBacklogItemView detailedBacklogItemView = new DetailedBacklogItemView(parentFrame,currentView,parentPanel);
                        detailedBacklogItemView.setSBE(temp.getSBE());
                        detailedBacklogItemView.displayDesc();
                        detailedBacklogItemView.displayComments();
                        parentFrame.remove(parentPanel.getCurrentView());
                        parentFrame.add(detailedBacklogItemView);
                        parentFrame.revalidate();
                        parentFrame.repaint();
                        parentPanel.setCurrentView(detailedBacklogItemView);
                    }

                    wasDoubleClick = true;
                }else{
                    Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty( "awt.multiClickInterval");
                    timer = new Timer(timerinterval.intValue(), new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (wasDoubleClick) {
                                wasDoubleClick = false; // reset flag
                            } else {

                                if(mode.equals("SPRINTBACKLOG")){

                                    ProdBacklogEntity pbe;
                                    if(temp.getSBE().getStoryLink() == -1){
                                        //no prod backlog associated with this
                                        pbe = new ProdBacklogEntity();
                                        pbe.setProjectName(parentPanel.getActiveProj());
                                        pbe.setTitle("Sprint Backlog Item " + temp.getSBE().getIssueID());
                                        pbe.setStoryType("UserStory");
                                        pbe.setDescription(temp.getSBE().getDescription());
                                        pbe.setPriority("Stretch");
                                        pbe.setEffortEstimation(temp.getSBE().getStoryPoints());
                                        pbe.setSubType("Feature");
                                        pbe.setStoryNumber(parentPanel.sc.getNewestStoryId());
                                        pbe.setAssignedToSprint(-1);

                                        parentPanel.sc.addBacklog(pbe);
                                    }
                                    else{
                                        pbe = parentPanel.sc.getBacklog(temp.getSBE().getStoryLink());
                                        pbe.setAssignedToSprint(-1);
                                        parentPanel.sc.modifyBacklog(pbe);
                                        parentPanel.sc.removeSprintBL(temp.getSBE());
                                    }

                                    partnerPanel.addPBElement(pbe.getStoryNumber());
                                    partnerPanel.updateView();

                                    remove(temp);
                                    updateView();
                                }


                            }
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }

            }
        });

        add(temp);
    }

    public void updateView(){
        revalidate();
        repaint();
    }




}
