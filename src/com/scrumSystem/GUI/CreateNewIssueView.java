package com.scrumSystem.GUI;

import com.scrumSystem.project.productBacklog.ProdBacklogEntity;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/**
 * Created by Darryl on 7/05/2016.
 */
public class CreateNewIssueView extends JPanel{
    private JFrame parentFrame;
    private MemberView parentPanel;
    //private JPanel productBacklogView;
    private ProductBacklogView returnView;
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

    private JTextField titleField;
    private JComboBox<String> typeOptions;
    private JComboBox<String> subTypeOptions;
    private JTextField storyPointsField;
    private JComboBox<String> priorityOptions;
    private int modifiyStoryID;

    private String selectedBacklogItem; //will eventually be backlog object

    private JTextArea descArea;

    public CreateNewIssueView(String m,JFrame f, ProductBacklogView ret, MemberView pp){
        parentFrame = f;
        parentPanel = pp;
        //productBacklogView = pbv;
        returnView = ret;
        currentView = this;
        mode = m;
        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());
        setBackground(Color.decode("#EBF0F2"));

        //  HEADER - THIS.BORDERLAYOUT.NORTH
        if(mode.equals("MODIFY")){
            header = new JLabel("Modify Issue");
        }
        else{
            header = new JLabel("Create New Issue");
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
                    parentFrame.remove(currentView);
                    parentFrame.add(returnView);
                    parentFrame.revalidate();
                    parentFrame.repaint();
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
                ProdBacklogEntity temp = new ProdBacklogEntity();
                temp.setProjectName(parentPanel.getActiveProj());
                temp.setTitle(titleField.getText());
                temp.setStoryType((String)typeOptions.getSelectedItem());
                temp.setDescription(descArea.getText());
                temp.setPriority((String)priorityOptions.getSelectedItem());
                temp.setEffortEstimation(Integer.parseInt(storyPointsField.getText()));
                temp.setSubType((String)subTypeOptions.getSelectedItem());
                temp.setStoryNumber(modifiyStoryID);

                //get selected epic
                //temp.setEpicRef(Integer.parseInt(fields[8]));

                if(mode.equals("CREATE")){
                    parentPanel.sc.addBacklog(temp);
                }
                else if(mode.equals("MODIFY")){
                    parentPanel.sc.modifyBacklog(temp);
                    returnView.updatePBList();
                }


                //reset view
                returnView.addElement(parentPanel.sc.getNewestStoryId()-1);
                parentFrame.remove(exit.getParentPanel());
                parentFrame.add(returnView);
                parentFrame.revalidate();
                parentFrame.repaint();
                //currentView = productBacklogView;
                //NEED TO UPDATE TEAMMEMBERVIEW.SETCURRENTVIEW()
            }
        });

        //WEST - this.borderLayout.west
        westLayoutPanel = new JPanel();
        westLayoutPanel.setBackground(Color.decode("#EBF0F2"));
        westLayoutPanel.setLayout(new GridLayout(7,1));

        JLabel title = new JLabel("Title: ", SwingConstants.CENTER);
        JLabel desc = new JLabel("Description: ", SwingConstants.CENTER);
        JLabel priority = new JLabel("Priority: ",SwingConstants.CENTER);
        JLabel type = new JLabel("Issue Type: ",SwingConstants.CENTER);
        JLabel subType = new JLabel("Sub Type: ",SwingConstants.CENTER);
        JLabel storyPoints = new JLabel("Story Points: ",SwingConstants.CENTER);
        JLabel relatedBacklogItem = new JLabel("Related backlog item: ",SwingConstants.CENTER);

        westLayoutPanel.add(title);
        westLayoutPanel.add(desc);
        westLayoutPanel.add(priority);
        westLayoutPanel.add(type);
        westLayoutPanel.add(subType);
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
        titleField = new JTextField();
        descArea = new JTextArea(4,52);
        String[] optionsArray = new String[]{"Core","Non-Core","Stretch"};
        priorityOptions = new JComboBox<String>(optionsArray);
        String[] typeArray = new String[]{"Story","Epic"};
        typeOptions = new JComboBox<String>(typeArray);
        String[] subTypeArray = new String[]{"Bug", "Extension", "Feature"};
        subTypeOptions = new JComboBox<String>(subTypeArray);
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
        TextFieldLayout titleLayout = new TextFieldLayout(titleField);
        centerLeftLayoutPanel.add(titleLayout);
        centerLeftLayoutPanel.add(descArea);
        ComboBoxLayout poLayout = new ComboBoxLayout(priorityOptions);
        centerLeftLayoutPanel.add(poLayout);
        ComboBoxLayout tLayout = new ComboBoxLayout(typeOptions);
        centerLeftLayoutPanel.add(tLayout);
        ComboBoxLayout stLayout = new ComboBoxLayout(subTypeOptions);
        centerLeftLayoutPanel.add(stLayout);
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
        titleField.setText(p.getTitle());
        typeOptions.setSelectedItem(p.getStoryType());
        descArea.setText(p.getDescription());
        priorityOptions.setSelectedItem(p.getPriority());
        storyPointsField.setText(Integer.toString(p.getEffortEstimation()));
        subTypeOptions.setSelectedItem(p.getSubType());
        modifiyStoryID = p.getStoryNumber();
    }

}

class DummyLabel extends JLabel{
    public DummyLabel(){
        setBackground(Color.decode("#EBF0F2"));
    }
}

class ButtonGroupLayout extends JPanel{
    private ButtonGroup bg;
    private JRadioButton yes;
    private JRadioButton no;
    private BacklogScrollPane scrollPanel;
    private JLabel header;

    public ButtonGroupLayout(JRadioButton y, JRadioButton n,  BacklogScrollPane s, JLabel h){
        setBackground(Color.decode("#EBF0F2"));
        bg = new ButtonGroup();
        yes = y;
        no = n;
        scrollPanel = s;
        header = h;

        bg.add(yes);
        bg.add(no);


        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton aButton = (AbstractButton) e.getSource();
                if(aButton.getText().equals("Yes")){
                    setScrollPaneVisible(true);
                }
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton aButton = (AbstractButton) e.getSource();
                if(aButton.getText().equals("No")){
                    setScrollPaneVisible(false);
                }
            }
        });


        yes.setBackground(Color.decode("#EBF0F2"));
        no.setBackground(Color.decode("#EBF0F2"));

        setLayout(new BorderLayout());
        JPanel west = new JPanel();
        west.setBackground(Color.decode("#EBF0F2"));
        west.setLayout(new GridLayout(5,2));
        west.add(new DummyLabel()); //dummy label
        west.add(new DummyLabel()); //dummy label
        west.add(new DummyLabel()); //dummy label
        west.add(new DummyLabel()); //dummy label
        west.add(yes);
        west.add(no);
        west.add(new DummyLabel()); //dummy label
        west.add(new DummyLabel()); //dummy label
        west.add(new DummyLabel()); //dummy label
        west.add(new DummyLabel()); //dummy label

        add(west,BorderLayout.WEST);
    }

    public void setScrollPaneVisible(Boolean state){
        scrollPanel.setVisible(state);
        header.setVisible(state);
    }
}

class TextFieldLayout extends JPanel{
    private JTextField field;

    public TextFieldLayout(JTextField tf){
        field = tf;

        setLayout(new BorderLayout());
        setBackground(Color.decode("#EBF0F2"));

        JLabel topPad = new JLabel();
        JLabel bottomPad = new JLabel();
        topPad.setPreferredSize(new Dimension(100,30));
        bottomPad.setPreferredSize(new Dimension(100,30));
        add(topPad,BorderLayout.NORTH);//dummy label
        field.setPreferredSize(new Dimension(70,50));
        add(field, BorderLayout.CENTER);
        add(bottomPad,BorderLayout.SOUTH);//dummy label

    }
}

class ComboBoxLayout extends JPanel{

    private JComboBox<String> box;

    public ComboBoxLayout(JComboBox<String> b){

        box = b;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#EBF0F2"));
        box.setBackground(Color.decode("#EBF0F2"));

        //create gridlayout panel for borderlayout.west
        JPanel layout_west = new JPanel();
        layout_west.setBackground(Color.decode("#EBF0F2"));
        layout_west.setLayout(new GridLayout(5,1));
        layout_west.add(new DummyLabel()); //dummy label
        layout_west.add(new DummyLabel());//dummy label
        box.setPreferredSize(new Dimension(100,300));
        layout_west.add(box);
        layout_west.add(new DummyLabel());//dummy label
        layout_west.add(new DummyLabel());//dummy label
        add(layout_west,BorderLayout.WEST);

    }
}

class IssueButton extends JButton{

    private JFrame parentFrame;
    private JPanel parentPanel;

    public JPanel getParentPanel(){
        return parentPanel;
    }

    public IssueButton(String text,JFrame pf, JPanel pp ){
        parentFrame = pf;
        parentPanel = pp;
        this.setText(text);
        //setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        Border margin = new EmptyBorder(0,10,0,10);
        setBorder(new CompoundBorder(BorderFactory.createRaisedBevelBorder(),margin));

        setPreferredSize(new Dimension(100,50));

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(Color.decode("#90C3D4"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new JButton().getBackground());
            }
        });
    }

}
