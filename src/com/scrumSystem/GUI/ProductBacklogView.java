package com.scrumSystem.GUI;

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
 * Created by Darryl on 7/05/2016.
 */
public class ProductBacklogView extends JPanel {
    private JFrame parentFrame;
    private ProductBacklogView currentView;
    private MemberView parentPanel;
    private String memberType;

    private JPanel panelHeader;
    private JPanel centerPanel;
    private JPanel centerDescAndCommentPanel;
    private DescPanel descPanel;
    private BacklogScrollPane descScrollPane;
    private BacklogScrollPane BLScrollPane;
    private ScrollPanel scrollPanel; // redundant - remove
    private ProductBacklogScrollPanel productBacklogScrollPanel;
    private CreateIssueButton createNewIssueButton;
    private CommentsPanel commentsPanel;
    private BacklogScrollPane commentsScrollPane;

    public ProductBacklogView(String m, JFrame p, MemberView parent){
        parentFrame = p;
        parentPanel = parent;
        currentView = this;
        memberType = m;
        prepare();
    }

    public void prepare(){

        /*      PRODUCT BACKLOG PANEL       */
        //productBacklogPanel = new JPanel();
        setBackground(Color.decode("#EBF0F2"));
        setBorder(BorderFactory.createRaisedBevelBorder());

        /*      PRODUCT BACKLOG PANEL  - HEADER     */
        //panel for holding 'create new issue' button. Resides in productBacklogPanel.BorderLayout.NORTH.
        panelHeader = new JPanel();
        panelHeader.setLayout(new GridLayout(1,20));

        /*      PRODUCT BACKLOG PANEL  - CENTER DESCRIPTION AND COMMENTS PANEL  */
        centerDescAndCommentPanel = new JPanel();
        centerDescAndCommentPanel.setBackground(Color.decode("#EBF0F2"));
        centerDescAndCommentPanel.setLayout(new GridLayout(2,1));

        //create panel used to set boarderlayout for centerDescAndCommentPanel Description panel
        JPanel desclayoutPanel = new JPanel();
        desclayoutPanel.setBackground(Color.decode("#EBF0F2"));
        desclayoutPanel.setLayout(new BorderLayout());
        JLabel desclayoutPanel_header = new JLabel("Description");
        desclayoutPanel.add(desclayoutPanel_header,BorderLayout.NORTH);

        /*      PRODUCT BACKLOG PANEL  - DESCRIPTION PANEL     */
        //create Description panel for displaying an issues details
        descPanel = new DescPanel();
        descScrollPane = new BacklogScrollPane(100,100);
        descScrollPane.setScrollPanel(descPanel);
        desclayoutPanel.add(descScrollPane, BorderLayout.CENTER);

        //create panel used to set boarderlayout for centerDescAndCommentPanel comments panel
        JPanel commentslayoutPanel = new JPanel(); //layout for comments section
        commentslayoutPanel.setBackground(Color.decode("#EBF0F2"));
        commentslayoutPanel.setLayout(new BorderLayout());

        JPanel commentsLayoutPanel_north = new JPanel(); //panel to hold new comment button and comments header
        commentsLayoutPanel_north.setLayout(new BorderLayout());
        commentsLayoutPanel_north.setBackground(Color.decode("#EBF0F2"));

        JLabel commentslayoutPanel_header = new JLabel("Comments");
        final NavButton newCommentButton = new NavButton("Create Comment",parentPanel); //error as param 2 is null
        newCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newCommentButton.getText().equals("Create Comment")){
                    newCommentButton.setText("Submit Comment");
                    commentsPanel.addComment();
                }
                else if(newCommentButton.getText().equals("Submit Comment")){

                    newCommentButton.setText("Create Comment");
                    //save comments section?
                }

            }
        });

        //add comments header and addComment button to commentsLayoutPanel_north.BorderLayout.NORTH
        commentsLayoutPanel_north.add(commentslayoutPanel_header,BorderLayout.WEST);
        commentsLayoutPanel_north.add(newCommentButton,BorderLayout.EAST);

        commentslayoutPanel.add(commentsLayoutPanel_north,BorderLayout.NORTH);

        /*      PRODUCT BACKLOG PANEL  - COMMENTS PANEL     */
        commentsPanel = new CommentsPanel();
        commentsPanel.setBackground(Color.decode("#EBF0F2"));
        commentsScrollPane = new BacklogScrollPane(100,100);
        commentsScrollPane.setScrollPanel(commentsPanel);
        commentslayoutPanel.add(commentsScrollPane,BorderLayout.CENTER);
        centerDescAndCommentPanel.add(desclayoutPanel);
        centerDescAndCommentPanel.add(commentslayoutPanel);

        /*      PRODUCT BACKLOG PANEL  - BACKLOG PANEL     */
        //create BacklogScrollPane for displaying product backlog
        BLScrollPane = new BacklogScrollPane(600,610);
        BLScrollPane.setBackground(Color.decode("#EBF0F2"));

        //create scrollPanel for displaying elements inside BacklogScrollPane
        /*
        scrollPanel = new ScrollPanel(descPanel);
        scrollPanel.setBackground(Color.decode("#EBF0F2"));
        BLScrollPane.setScrollPanel(scrollPanel); // add scrollPanel to BacklogScrollPane BLScrollPane
        */

        productBacklogScrollPanel = new ProductBacklogScrollPanel(memberType,descPanel,this,parentFrame);
        BLScrollPane.setScrollPanel(productBacklogScrollPanel); // add scrollPanel to BacklogScrollPane BLScrollPane


         /*      PRODUCT BACKLOG PANEL  - HEADER PANEL     */
        //create button for adding new Issue to product backlog
        createNewIssueButton = new CreateIssueButton("Create New Issue",parentFrame,this,parentPanel);

        //format and add create new issue button to productBacklogPanelHeader
        panelHeader.add(new JLabel());//blank buffer label for alignment
        panelHeader.add(new JLabel());//blank buffer label for alignment
        panelHeader.add(new JLabel());//blank buffer label for alignment
        panelHeader.add(createNewIssueButton); // add button
        panelHeader.add(new JLabel());//blank buffer label for alignment
        panelHeader.add(new JLabel());//blank buffer label for alignment
        panelHeader.add(new JLabel());//blank buffer label for alignment
        panelHeader.add(new JLabel());//blank buffer label for alignment
        panelHeader.setBackground(Color.decode("#EBF0F2"));
        panelHeader.setPreferredSize(new Dimension(1500,30));

        /*      PRODUCT BACKLOG PANEL  - CENTER     */
        //Panel for holding scroll panes. residing in productBacklogPanel.BorderLayout.CENTER. Split into 2 for scroll panes
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2,10,10));
        centerPanel.add(BLScrollPane); //add backlogScrollPane to Centerpanel
        centerPanel.add(centerDescAndCommentPanel);

        //commentsPanel.addComment("Hello");
        //commentsPanel.addComment("Comment");

        add(panelHeader,BorderLayout.NORTH); //add header of backlogPanel to productBacklogPanel
        add(centerPanel,BorderLayout.CENTER); //add centerPanel to productBacklogPanel
    }

    public void addElement(String data){
        productBacklogScrollPanel.addElement(data);
    }

}



class CreateIssueButton extends JButton{

    private JFrame parentFrame = null; //needed to swap out views
    private ProductBacklogView currentView; //needed to give to createIssueView as return panel
    private MemberView teamMemberView; //needed to update current view
    private CreateNewIssueView createNewIssueView; //view to transition to
    //private ScrollPanel scrollPanel; //needed to update description panel

    //need to add descPanel to constructor
    public CreateIssueButton(String text, JFrame f,ProductBacklogView curr, MemberView t){
        parentFrame = f;
        teamMemberView = t;
        currentView = curr;
        //scrollPanel = s;
        this.setText(text);
        Border margin = new EmptyBorder(0,10,0,10);
        setBorder(new CompoundBorder(BorderFactory.createRaisedBevelBorder(),margin));

        this.setFocusPainted(false);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(Color.decode("#90C3D4"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new JButton().getBackground());
            }
        });

        //add onclick listener to newIssue button
        addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.remove(currentView);
                createNewIssueView = new CreateNewIssueView("CREATE",parentFrame,currentView);
                parentFrame.add(createNewIssueView);
                parentFrame.revalidate();
                parentFrame.repaint();
                teamMemberView.setCurrentView(createNewIssueView);


                /*
                final String data = "fhdjskfhdsjadkfhjasdkgfhasjkgfdhjkfghsjafgdhsajfghdasjkfghdjkfghsdjafghdsjdkfsdhfa" +
                        "dhfjdsghfjkdasghfksdghfjaksgdhfjakdgfhjksdfhjsdgfhasdjkfgdhsjkfyuryrywuryrtyqrtywqirghkfvas" +
                        "fbdhsfkdhaskfdhasgfdhasfghsdajfhsdjagfhsdjgfhjasdkgfhdsjagfhdjsagfhdjasgfhsdjagfdhsjafghsdjafsda" +
                        "fhsafgdhsfgdhsajkfgdhajgfdhjasgdhfjasgfhdjagdfhjdgahjfkdgahsjdgfhdjaskgfhdjksgdfahjskdgfkasdf";

                scrollPanel.addElement(data); //add new issue to scrollPanel
                */
            }
        });

    }


}


class BacklogScrollPane extends JScrollPane{

    private JPanel scrollPanel;

    public BacklogScrollPane(int x, int y){
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setViewportBorder(BorderFactory.createRaisedBevelBorder());
        setPreferredSize(new Dimension(x,y));
    }

    public void setScrollPanel(JPanel sp){
        scrollPanel = sp;
        getViewport().add(sp);
    }
}

class DescPanel extends JPanel{

    private JTextArea area;

    public DescPanel(){
        setBackground(Color.white);
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setBackground(Color.white);
        area.setOpaque(true);
        area.setColumns(51);
        add(area);
    }

    public void displayIssue(String data){
        area.setText(data);
    }
}




class ProductBacklogScrollPanel extends JPanel{

    private ArrayList<JTextArea> productBacklogUI;
    private ArrayList<String> productBacklogData;

    private DescPanel descPanel;
    private ProductBacklogView returnView;
    private JFrame parentFrame;

    private String memberType;

    private Boolean wasDoubleClick = false;
    private Timer timer;

    public ProductBacklogScrollPanel(String m,DescPanel d, ProductBacklogView ret, JFrame p){
        productBacklogUI = new ArrayList<JTextArea>();
        productBacklogData = new ArrayList<String>();
        descPanel = d;
        returnView = ret;
        parentFrame = p;
        memberType = m;
    }

    public void loadProductBacklog(){
        //load sprint backlog data from DB into array
        productBacklogData.add("One");
        productBacklogData.add("Two");
        productBacklogData.add("Three");
        productBacklogData.add("Four");

        //add all elements from array into ui
        for(int i = 0; i<productBacklogData.size(); i++){
            addElement(productBacklogData.get(i));
        }

        //repaint
        revalidate();
        repaint();

    }

    public void addElement(String data){
        final JTextArea temp = new JTextArea(3,50);
        temp.setText(data);
        temp.setEditable(false);
        temp.setLineWrap(true);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        temp.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

        productBacklogUI.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - productBacklogUI.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + productBacklogUI.size(),1));
        }
        else{
            setLayout(new GridLayout(productBacklogUI.size(),1));
        }


        //reference: http://stackoverflow.com/questions/548180/java-ignore-single-click-on-double-click
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("double clicked");
                    if(memberType.equals("ProductOwner")){
                        CreateNewIssueView createNewIssueView = new CreateNewIssueView("MODIFY",parentFrame,returnView);
                        createNewIssueView.setSelectedBacklogItem(temp.getText());
                        parentFrame.remove(returnView);
                        parentFrame.add(createNewIssueView);
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    }

                    wasDoubleClick = true;
                }else{
                    Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty( "awt.multiClickInterval");
                    timer = new Timer(timerinterval.intValue(), new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (wasDoubleClick) {
                                wasDoubleClick = false; // reset flag
                            } else {
                                descPanel.displayIssue(temp.getText());
                                //remove(temp);
                                update();
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

    public void update(){
        revalidate();
        repaint();
    }

}


//standard scroll panel for product backlog (teammemberview)
class ScrollPanel extends JPanel{

    private ArrayList<JTextArea> elements;
    private DescPanel descPanel;

    public ScrollPanel( DescPanel d){
        elements = new ArrayList<JTextArea>();
        descPanel = d;
    }

    public void addElement(String data){
        final JTextArea temp = new JTextArea(3,50);
        temp.setText(data);
        temp.setEditable(false);
        temp.setLineWrap(true);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        temp.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

        elements.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - elements.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + elements.size(),1));
        }
        else{
            setLayout(new GridLayout(elements.size(),1));
        }


        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                descPanel.displayIssue(temp.getText());
            }
        });

        add(temp);
        revalidate();
        repaint();
    }

}


class CommentsPanel extends JPanel{
    private ArrayList<JTextArea> elements;

    public CommentsPanel(){
        elements = new ArrayList<JTextArea>();
    }

    public void addComment(){
        final JTextArea temp = new JTextArea(3,50);
        temp.setText("<user number>: ");
        temp.setEditable(true);
        temp.setLineWrap(true);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        temp.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

        elements.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - elements.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + elements.size(),1));
        }
        else{
            setLayout(new GridLayout(elements.size(),1));
        }

        add(temp);
        revalidate();
        repaint();
    }
}

