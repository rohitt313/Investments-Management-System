/*
Student Name: Rohit Dalal
Student ID: 1258479
Course: CIS*2430
Email: rdalal@uoguelph.ca

NOTE: Sometimes, when you initially run it, the frame might be unresponsive and in that case kindly close it
by pressing CTRL+C in the terminal and run it again

Run Command for JAR file:
* Go to directory rdalal_a3
* Enter the following command:
    java -jar ePortfolio.jar fileName.txt

Compilation Command (without JAR file):
* Go to directory rdalal_a3
* Enter the following command:
    javac ePortfolio/Portfolio.java ePortfolio/Stock.java ePortfolio/MutualFund.java ePortfolio/Investment.java

Run Command (without JAR file):
* Go to directory rdalal_a3
* Enter the following command:
    java ePortfolio/Portfolio fileName.txt
*/


package ePortfolio;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.InputMismatchException;  
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Portfolio extends JFrame implements ActionListener{
    private static final int WIDTH=810;
    private static final int HEIGHT=690;
    private static final String[] typeStrings={"stock","mutualfund"};
    private JComboBox <String> typeList;
    private static final double STOCKCOMMISSION=9.99;     //This is a constant value 
    private static final int MUTUALFUNDCOMMISSION=45;     //This is a constant value

    private JPanel homePane;
    private JPanel buyMain;
    private JPanel sellMain;
    private JPanel updateMain;
    private JPanel getGainMain;
    private JPanel searchMain;
    private JPanel buyPane1;
    private JPanel buyPane2;
    private JPanel buySubPane1;
    private JPanel buySubPane2;
    private JPanel buySubPane3;
    private JPanel buySubPane4;
    private JPanel buySubPane5;
    private JPanel sellPane1;
    private JPanel sellPane2;
    private JPanel sellSubPane1;
    private JPanel sellSubPane2;
    private JPanel sellSubPane3;
    private JPanel sellSubPane4;
    private JPanel sellSubPane5;
    private JPanel sellSubPane6;
    private JPanel sellPane3;
    private JPanel updatePane1;
    private JPanel updatePane2;
    private JPanel updateSubPane1;
    private JPanel updateSubPane2;
    private JPanel updateSubPane3;
    private JPanel updateSubPane4;
    private JPanel updateSubPane5;
    private JPanel searchPane1;
    private JPanel searchPane2;
    private JPanel searchSubPane1;
    private JPanel searchSubPane2;
    private JPanel searchSubPane3;
    private JPanel searchSubPane4;
    private JPanel searchSubPane5;
    private JPanel getGainPane1;
    private JPanel getGainPane2;
    private JPanel getGainSubPane1;
    private JPanel getGainSubPane2;
    private JPanel getGainSubPane3;

    private JPanel createPane1;
    private JPanel createPane2;

    private JTextField sellTextField1;
    private JTextField sellTextField2;
    private JTextField sellTextField3;
    private JTextArea sellMessage;

    private JTextField updateTextField1;
    private JTextField updateTextField2;
    private JTextField updateTextField3;
    private JTextArea updateMessage;

    private JTextField searchTextField1;
    private JTextField searchTextField2;
    private JTextField searchTextField3;
    private JTextField searchTextField4;
    private JTextArea searchMessage;

    private JTextField getGainTextField1;
    private JTextArea getGainMessage;

    private JTextField buyTextField1;
    private JTextField buyTextField2;
    private JTextField buyTextField3;
    private JTextField buyTextField4;
    private JTextField buyTextField5;
    private JTextArea buyMessage;

    private JButton updateButton1;
    private JButton updateButton2;
    private JButton updateButton3;

    private String args;

    private ArrayList<Investment> investments=new ArrayList<Investment>();
    private HashMap<String,ArrayList<Integer>> nameAndIndex=new HashMap<>();     //This HashMap stores each investment's keywords and their index positions
    private ArrayList<Integer> investmentsSearch=new ArrayList<Integer>();       //This ArrayList will store the index/indices of investments which match the criteria user gives for search command

    
    public static void main(String[] args){
        Portfolio ePortfolio=new Portfolio(args);
        ePortfolio.setVisible(true);                //Creating a new Portfolio frame and making it visible
    }

    public Portfolio(String[] args){
        super("ePortfolio");
        if(args.length==1){                   //If the user provided the file name as command-line arguement
                readFile(args[0],investments);
                loadFromFile(investments,nameAndIndex);
                this.args=args[0];
        }
        else{                                 //Else we print an appropriate message and terminate the program
            System.out.println("You did not provide the file name");
            System.exit(0);
        }

        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(1,1));                //Setting the frame's layout as GridLayout

       //Creating the Main Menu, different panels for each option and setting each frame with GUI components
        createPanels();           
        createMainMenu();          
        setHomePanel();
        setBuyMainPanel();
        setSellMainPanel();
        setUpdateMainPanel();
        setGetGainMainPanel();
        setSearchMainPanel();
    }


    /**
     * This method creates the Main Menu for the JFrame and adds options for it
     */
    private void createMainMenu(){
        JMenuBar bar=new JMenuBar();                //Creating the main and options for it
        JMenu choiceMenu=new JMenu("Commands");
        JMenuItem buyChoice=new JMenuItem("Buy");
        JMenuItem sellChoice=new JMenuItem("Sell");
        JMenuItem updateChoice=new JMenuItem("Update");
        JMenuItem getGainChoice=new JMenuItem("getGain");
        JMenuItem searchChoice=new JMenuItem("Search");
        JMenuItem quitChoice=new JMenuItem("quit");

        buyChoice.setActionCommand("BuyM");            //Changing the Command name for the options
        sellChoice.setActionCommand("SellM");
        updateChoice.setActionCommand("UpdateM");
        getGainChoice.setActionCommand("GetGainM");
        searchChoice.setActionCommand("SearchM");

        buyChoice.addActionListener(this);             //Adding the options to the ActionListener
        sellChoice.addActionListener(this);
        updateChoice.addActionListener(this);
        getGainChoice.addActionListener(this);
        searchChoice.addActionListener(this);
        quitChoice.addActionListener(this);

        choiceMenu.add(buyChoice);                    //Adding the options to the Main Menu
        choiceMenu.add(sellChoice);
        choiceMenu.add(updateChoice);
        choiceMenu.add(getGainChoice);
        choiceMenu.add(searchChoice);
        choiceMenu.add(quitChoice);

        bar.add(choiceMenu);                 //Adding the Main Menu to the menu bar
        setJMenuBar(bar);
    }


    /**
     * This method creates all the panels to be used for the Jframe
     */
    private void createPanels(){
        homePane=new JPanel();          //Creating a panel for the first Jframe that the user sees and all the other options from the Main Menu(except quit)
        buyMain=new JPanel();
        sellMain=new JPanel();
        updateMain=new JPanel();
        getGainMain=new JPanel();
        searchMain=new JPanel();

        homePane.setVisible(true);      //Initally only the homePane should be visible to the user
        sellMain.setVisible(false);
        buyMain.setVisible(false);
        updateMain.setVisible(false);
        getGainMain.setVisible(false);
        searchMain.setVisible(false);

        add(homePane);                  //Adding all the panels to the JFrame
    }


    /**
     * This method sets up the first panel that is visible to the user before choosing
     * any option from the Main Menu
     */
    private void setHomePanel(){
        homePane.setLayout(new BoxLayout(homePane,BoxLayout.PAGE_AXIS));

        //Creating labels for the welcome message to be displayed on the JPanel
        JLabel message1=new JLabel("Welcome to ePortfolio.");
        JLabel message2=new JLabel("Choose a command from the \"Commands\" menu to buy or sell");
        JLabel message3=new JLabel("an investment, update prices for all investments, get gain for the");
        JLabel message4=new JLabel("portfolio, search for relevant investments, or quit the program.");

        //Changing the font of every label's text value
        message1.setFont(new Font("Times New Roman",Font.PLAIN,20));
        message2.setFont(new Font("Times New Roman",Font.PLAIN,20));
        message3.setFont(new Font("Times New Roman",Font.PLAIN,20));
        message4.setFont(new Font("Times New Roman",Font.PLAIN,20));

        //Adding all the JLabels to homePane
        homePane.add(Box.createVerticalStrut(30));    //To provide vertical spacing
        homePane.add(Box.createRigidArea(new Dimension(20, 0)));   //To provide a fixed horizontal margin
        homePane.add(message1);
        homePane.add(Box.createVerticalStrut(50));
        homePane.add(message2);
        homePane.add(Box.createVerticalStrut(5));
        homePane.add(message3);
        homePane.add(Box.createVerticalStrut(5));
        homePane.add(message4);
    }


    /**
     * This method sets up the panel that is visible to the user when the "Sell" option is selected
     */
    private void setSellMainPanel(){

        //Creating sub-panels and sub-sub-panels which will contain different GUI objects
        sellPane1=new JPanel();
        sellPane2=new JPanel();
        sellSubPane1=new JPanel();
        sellSubPane2=new JPanel();
        sellSubPane3=new JPanel();
        sellSubPane4=new JPanel();
        sellSubPane5=new JPanel();

        //Creating labels for the textFields to be displayed on the JPanel
        JLabel label1=new JLabel("Selling an investment");
        JLabel label2=new JLabel("Symbol");
        JLabel label3=new JLabel("Quantity");
        JLabel label4=new JLabel("Price");
        JLabel label5=new JLabel("Messages");

        //Creating buttons to "Sell" an investment
        JButton button1=new JButton("Reset");
        button1.setBackground(new Color(135,206,251));
        JButton button2=new JButton("Sell");
        button2.setBackground(new Color(135,206,251));

        //Changing the Command name for the "Reset" button and adding all buttons to the ActionListener
        button1.setActionCommand("ResetSel");
        button1.addActionListener(this);
        button2.addActionListener(this);
        
        //Creating text fields to "Sell" an investment
        sellTextField1=new JTextField();
        sellTextField2=new JTextField();
        sellTextField3=new JTextField();

        //Setting the dimension for each text field and aligning it
        sellTextField1.setMaximumSize(new Dimension(200,25));
        sellTextField1.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellTextField2.setMaximumSize(new Dimension(110,25));
        sellTextField2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        sellTextField3.setMaximumSize(new Dimension(110,25));
        sellTextField3.setAlignmentX(Component.RIGHT_ALIGNMENT);

        //Changing the font of every label's text value
        label1.setFont(new Font("Times New Roman",Font.BOLD,16));
        label2.setFont(new Font("Times New Roman",Font.BOLD,16));
        label3.setFont(new Font("Times New Roman",Font.BOLD,16));
        label4.setFont(new Font("Times New Roman",Font.BOLD,16));
        label5.setFont(new Font("Times New Roman",Font.BOLD,16));

        //Setting the layout and dimensions for all the JPanels,sub-JPanels and sub-sub-JPanels to be used
        sellMain.setLayout(new GridLayout(2,1));
        sellPane1.setLayout(new GridLayout(1,2));
        sellPane2.setLayout(new BoxLayout(sellPane2,BoxLayout.PAGE_AXIS));
        sellSubPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
        sellSubPane1.setPreferredSize(new Dimension(100,100));
        sellSubPane2.setLayout(new BoxLayout(sellSubPane2,BoxLayout.PAGE_AXIS));
        sellSubPane3.setLayout(new BoxLayout(sellSubPane3,BoxLayout.PAGE_AXIS));
        sellSubPane3.setPreferredSize(new Dimension(150, 200));
        sellSubPane4.setLayout(new BoxLayout(sellSubPane4,BoxLayout.PAGE_AXIS));
        sellSubPane5.setLayout(new BoxLayout(sellSubPane5,BoxLayout.Y_AXIS));

        //Setting the JTextArea for "Sell" and making it scrollable 
        sellMessage=new JTextArea(5,20);
        JScrollPane scrollPane=new JScrollPane(sellMessage);
        scrollPane.setMaximumSize(new Dimension(1300,900));
        sellMessage.setEditable(false);         //It cannot be edited by the user
        
        //Adding all the labels to the sub-sub-JPanel:sellSubPane2 with appropriate vertical spacing
        sellSubPane2.add(label1);
        sellSubPane2.add(Box.createVerticalStrut(30));
        sellSubPane2.add(label2);
        sellSubPane2.add(Box.createVerticalStrut(15));
        sellSubPane2.add(label3);
        sellSubPane2.add(Box.createVerticalStrut(15));
        sellSubPane2.add(label4);
        sellSubPane2.add(Box.createVerticalStrut(60));
        sellSubPane2.add(label5);

        //Adding all the text fields to the sub-sub-JPanel: sellSubPane3 with appropriate vertical spacing
        sellSubPane3.add(Box.createVerticalStrut(45));
        sellSubPane3.add(sellTextField1);
        sellSubPane3.add(Box.createVerticalStrut(10));
        sellSubPane3.add(sellTextField2);
        sellSubPane3.add(Box.createVerticalStrut(10));
        sellSubPane3.add(sellTextField3);

        //Adding all the buttons to the sub-sub-JPanel: sellSubPane4 with appropriate vertical spacing and button alignment
        sellSubPane4.add(Box.createVerticalStrut(58));
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellSubPane4.add(button1);
        sellSubPane4.add(Box.createVerticalStrut(45));
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellSubPane4.add(button2);
        
        //Adding all the sub-sub-JPanels to sub-JPanels: sellPane1 and sellPane2
        sellSubPane1.add(sellSubPane2);
        sellSubPane1.add(sellSubPane3);
        sellPane1.add(sellSubPane1);
        sellPane1.add(sellSubPane4);
        sellPane2.add(scrollPane);

        //Adding the sub JPanels: sellPane1 and sellPane2 to the main JPanel for "Sell" option
        sellMain.add(sellPane1);
        sellMain.add(sellPane2);
    }

    /**
     * This method sets up the panel that is visible to the user when the "Buy" option is selected
     */
    private void setBuyMainPanel(){

        //Creating a new JComboBox which contains two options: stock and mutualfund and adding it to its own JPanel
        JPanel comboBoxPanel = new JPanel();
        typeList=new JComboBox<>(typeStrings);
        typeList.setSelectedIndex(0);     //stock is the default option
        comboBoxPanel.setLayout(new FlowLayout());
        comboBoxPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));  //adding padding
        comboBoxPanel.add(typeList);

        //Creating sub-panels and sub-sub-panels which will contain different GUI objects
        buyPane1=new JPanel();
        buyPane2=new JPanel();
        buySubPane1=new JPanel();
        buySubPane2=new JPanel();
        buySubPane3=new JPanel();
        buySubPane4=new JPanel();
        buySubPane5=new JPanel();

        //Creating labels for the textFields to be displayed on the JPanel
        JLabel label1=new JLabel("Buying an investment");
        JLabel label2=new JLabel("Type");
        JLabel label3=new JLabel("Symbol");
        JLabel label4=new JLabel("Name");
        JLabel label5=new JLabel("Quantity");
        JLabel label6=new JLabel("Price");
        JLabel label7=new JLabel("Messages");

        //Creating buttons to "Buy" an investment
        JButton button1=new JButton("Reset");
        button1.setBackground(new Color(135,206,251));
        JButton button2=new JButton("Buy");
        button2.setBackground(new Color(135,206,251));

        //Changing the Command name for the "Reset" button and adding all buttons to the ActionListener
        button1.setActionCommand("ResetB");
        button1.addActionListener(this);
        button2.addActionListener(this);
        typeList.addActionListener(this);

        //Creating text fields to "Buy" an investment
        buyTextField1=new JTextField();
        buyTextField2=new JTextField();
        buyTextField3=new JTextField();
        buyTextField4=new JTextField();
        buyTextField5=new JTextField();

        //Setting the dimension for each text field and aligning it
        buyTextField1.setMaximumSize(new Dimension(200,25));
        buyTextField1.setAlignmentX(Component.CENTER_ALIGNMENT);
        typeList.setPreferredSize(new Dimension(200,25));
        buyTextField2.setMaximumSize(new Dimension(300,35));
        buyTextField2.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyTextField3.setMaximumSize(new Dimension(110,25));
        buyTextField3.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buyTextField4.setMaximumSize(new Dimension(110,25));
        buyTextField4.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buyTextField5.setMaximumSize(new Dimension(110,25));
        buyTextField5.setAlignmentX(Component.RIGHT_ALIGNMENT);

        //Changing the font of every label's text value
        label1.setFont(new Font("Times New Roman",Font.BOLD,16));
        label2.setFont(new Font("Times New Roman",Font.BOLD,16));
        label3.setFont(new Font("Times New Roman",Font.BOLD,16));
        label4.setFont(new Font("Times New Roman",Font.BOLD,16));
        label5.setFont(new Font("Times New Roman",Font.BOLD,16));
        label6.setFont(new Font("Times New Roman",Font.BOLD,16));
        label7.setFont(new Font("Times New Roman",Font.BOLD,16));
        typeList.setFont(new Font("Times New Roman",Font.BOLD,16));

        //Setting the layout and dimensions for all the JPanels,sub-JPanels and sub-sub-JPanels to be used
        buyMain.setLayout(new GridLayout(2,1));
        buyPane1.setLayout(new GridLayout(1,2));
        buyPane2.setLayout(new BoxLayout(buyPane2,BoxLayout.PAGE_AXIS));
        buySubPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
        buySubPane1.setPreferredSize(new Dimension(100,100));
        buySubPane2.setLayout(new BoxLayout(buySubPane2,BoxLayout.PAGE_AXIS));
        buySubPane3.setLayout(new BoxLayout(buySubPane3,BoxLayout.PAGE_AXIS));
        buySubPane3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        buySubPane3.setPreferredSize(new Dimension(150,155));
        buySubPane4.setLayout(new BoxLayout(buySubPane4,BoxLayout.PAGE_AXIS));
        buySubPane5.setLayout(new BoxLayout(buySubPane5,BoxLayout.PAGE_AXIS));

        //Setting the JTextArea for "Buy" and making it scrollable 
        buyMessage=new JTextArea(5,20);
        JScrollPane scrollPane=new JScrollPane(buyMessage);
        scrollPane.setMaximumSize(new Dimension(1300,900));
        buyMessage.setEditable(false);       //It cannot be edited by the user
        
        //Adding all the labels to the sub-sub-JPanel:buySubPane2 with appropriate vertical spacing
        buySubPane2.add(Box.createVerticalStrut(7));
        buySubPane2.add(label1);
        buySubPane2.add(Box.createVerticalStrut(45));
        buySubPane2.add(label2);
        buySubPane2.add(Box.createVerticalStrut(18));
        buySubPane2.add(label3);
        buySubPane2.add(Box.createVerticalStrut(15));
        buySubPane2.add(label4);
        buySubPane2.add(Box.createVerticalStrut(8));
        buySubPane2.add(label5);
        buySubPane2.add(Box.createVerticalStrut(10));
        buySubPane2.add(label6);
        buySubPane2.add(Box.createVerticalStrut(40));
        buySubPane2.add(label7);

        //Adding all the text fields and the combo box to the sub-sub-JPanel: buySubPane3 with appropriate vertical spacing
        buySubPane3.add(Box.createVerticalStrut(7));
        buySubPane3.add(typeList);
        buySubPane3.add(Box.createVerticalStrut(10));
        buySubPane3.add(buyTextField2);
        buySubPane3.add(Box.createVerticalStrut(10));
        buySubPane3.add(buyTextField3);
        buySubPane3.add(Box.createVerticalStrut(10));
        buySubPane3.add(buyTextField4);
        buySubPane3.add(Box.createVerticalStrut(15));
        buySubPane3.add(buyTextField5);

        //Adding all the buttons to the sub-sub-JPanel: buySubPane4 with appropriate vertical spacing and button alignment
        buySubPane4.add(Box.createVerticalStrut(75));
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buySubPane4.add(button1);
        buySubPane4.add(Box.createVerticalStrut(45));
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        buySubPane4.add(button2);
        
        //Adding all the sub-sub-JPanels to sub-JPanels: buyPane1 and buyPane2
        buySubPane1.add(buySubPane2);
        buySubPane1.add(buySubPane3);
        buyPane1.add(buySubPane1);
        buyPane1.add(buySubPane4);
        buyPane2.add(scrollPane);

        //Adding the sub JPanels: buyPane1 and buyPane2 to the main JPanel for "Buy" option
        buyMain.add(buyPane1);
        buyMain.add(buyPane2);
    }

    /**
     * This method sets up the panel that is visible to the user when the "getGain" option is selected
     */
    private void setGetGainMainPanel(){
        //Creating sub-panels and sub-sub-panels which will contain different GUI objects
        getGainPane1=new JPanel();
        getGainPane2=new JPanel();
        getGainSubPane1=new JPanel();
        getGainSubPane2=new JPanel();
        getGainSubPane3=new JPanel();
        
        //Creating labels for the textFields to be displayed on the JPanel
        JLabel label1=new JLabel("Getting total gain");
        JLabel label2=new JLabel("Total gain");
        JLabel label3=new JLabel("Individual gains");

        //Creating the text field which displays the total gain
        getGainTextField1=new JTextField();
        getGainTextField1.setMaximumSize(new Dimension(110,25));
        getGainTextField1.setEditable(false);
        getGainTextField1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Changing the font of every label's text value
        label1.setFont(new Font("Times New Roman",Font.BOLD,16));
        label2.setFont(new Font("Times New Roman",Font.BOLD,16));
        label3.setFont(new Font("Times New Roman",Font.BOLD,16));

        //Setting the layout and dimensions for all the JPanels,sub-JPanels and sub-sub-JPanels to be used
        getGainMain.setLayout(new GridLayout(2,1));
        getGainPane1.setLayout(new GridLayout(1,2));
        getGainPane2.setLayout(new BoxLayout(getGainPane2,BoxLayout.PAGE_AXIS));
        getGainSubPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
        getGainSubPane1.setPreferredSize(new Dimension(100,100));
        getGainSubPane2.setLayout(new BoxLayout(getGainSubPane2,BoxLayout.PAGE_AXIS));
        getGainSubPane3.setLayout(new BoxLayout(getGainSubPane3,BoxLayout.PAGE_AXIS));
        getGainSubPane3.setPreferredSize(new Dimension(150, 200));

        //Setting the JTextArea for "getGain" and making it scrollable 
        getGainMessage=new JTextArea(5,20);
        JScrollPane scrollPane=new JScrollPane(getGainMessage);
        scrollPane.setMaximumSize(new Dimension(1300,900));
        getGainMessage.setEditable(false);      //It cannot be edited by the user
        
        //Adding all the labels to the sub-sub-JPanel:getGainSubPane2 with appropriate vertical spacing
        getGainSubPane2.add(label1);
        getGainSubPane2.add(Box.createVerticalStrut(30));
        getGainSubPane2.add(label2);
        getGainSubPane2.add(Box.createVerticalStrut(15));
        getGainSubPane2.add(label3);

        //Adding the text field to the sub-sub-JPanel: getGainSubPane3 with appropriate vertical spacing
        getGainSubPane3.add(Box.createVerticalStrut(100));
        getGainSubPane3.add(getGainTextField1);
        
        //Adding all the sub-sub-JPanels to sub-JPanels: getGainPane1 and getGainPane2
        getGainSubPane1.add(getGainSubPane2);
        getGainSubPane1.add(getGainSubPane3);
        getGainPane1.add(getGainSubPane1);
        getGainPane2.add(scrollPane);

        //Adding the sub JPanels: getGainPane1 and getGainPane2 to the main JPanel for "getGain" option
        getGainMain.add(getGainPane1);
        getGainMain.add(getGainPane2);
    }


    /**
     * This method sets up the panel that is visible to the user when the "Update" option is selected
     */
    private void setUpdateMainPanel(){
        //Creating sub-panels and sub-sub-panels which will contain different GUI objects
        updatePane1=new JPanel();
        updatePane2=new JPanel();
        updateSubPane1=new JPanel();
        updateSubPane2=new JPanel();
        updateSubPane3=new JPanel();
        updateSubPane4=new JPanel();
        updateSubPane5=new JPanel();

        //Creating labels for the textFields to be displayed on the JPanel
        JLabel label1=new JLabel("Updating investments");
        JLabel label2=new JLabel("Symbol");
        JLabel label3=new JLabel("Name");
        JLabel label4=new JLabel("Price");
        JLabel label5=new JLabel("Messages");

        //Creating buttons to "Update" an investment
        updateButton1=new JButton("Prev");
        updateButton1.setBackground(new Color(135,206,251));
        updateButton2=new JButton("Next");
        updateButton2.setBackground(new Color(135,206,251));
        updateButton3=new JButton("Save");
        updateButton3.setBackground(new Color(135,206,251));

        //Adding all buttons to the ActionListener and disabling the "Prev" Button
        updateButton1.addActionListener(this);
        updateButton1.setEnabled(false);  
        updateButton2.addActionListener(this);
        updateButton3.addActionListener(this);

        //Creating text fields to "Update" an investment
        updateTextField1=new JTextField();
        updateTextField2=new JTextField();
        updateTextField3=new JTextField();

        //Setting the dimension for each text field and aligning it
        updateTextField1.setMaximumSize(new Dimension(200,25));
        updateTextField1.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateTextField2.setMaximumSize(new Dimension(200,25));
        updateTextField3.setMaximumSize(new Dimension(110,25));
        updateTextField3.setAlignmentX(Component.RIGHT_ALIGNMENT);

        //Making the "Symbol" and "Name" text fields not editable by the user
        updateTextField1.setEditable(false);
        updateTextField2.setEditable(false);

        //Changing the font of every label's text value
        label1.setFont(new Font("Times New Roman",Font.BOLD,16));
        label2.setFont(new Font("Times New Roman",Font.BOLD,16));
        label3.setFont(new Font("Times New Roman",Font.BOLD,16));
        label4.setFont(new Font("Times New Roman",Font.BOLD,16));
        label5.setFont(new Font("Times New Roman",Font.BOLD,16));

        //Setting the layout and dimensions for all the JPanels,sub-JPanels and sub-sub-JPanels to be used
        updateMain.setLayout(new GridLayout(2,1));
        updatePane1.setLayout(new GridLayout(1,2));
        updatePane2.setLayout(new BoxLayout(updatePane2,BoxLayout.PAGE_AXIS));
        updateSubPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
        updateSubPane1.setPreferredSize(new Dimension(100,100));
        updateSubPane2.setLayout(new BoxLayout(updateSubPane2,BoxLayout.PAGE_AXIS));
        updateSubPane3.setLayout(new BoxLayout(updateSubPane3,BoxLayout.PAGE_AXIS));
        updateSubPane3.setPreferredSize(new Dimension(150,200));
        updateSubPane4.setLayout(new BoxLayout(updateSubPane4,BoxLayout.PAGE_AXIS));
        updateSubPane5.setLayout(new BoxLayout(updateSubPane5,BoxLayout.Y_AXIS));

        //Setting the JTextArea for "Update" and making it scrollable 
        updateMessage=new JTextArea(5,20);
        JScrollPane scrollPane=new JScrollPane(updateMessage);
        scrollPane.setMaximumSize(new Dimension(1300,900));
        updateMessage.setEditable(false);    //It cannot be edited by the user
        
        //Adding all the labels to the sub-sub-JPanel:updateSubPane2 with appropriate vertical spacing
        updateSubPane2.add(label1);
        updateSubPane2.add(Box.createVerticalStrut(30));
        updateSubPane2.add(label2);
        updateSubPane2.add(Box.createVerticalStrut(15));
        updateSubPane2.add(label3);
        updateSubPane2.add(Box.createVerticalStrut(15));
        updateSubPane2.add(label4);
        updateSubPane2.add(Box.createVerticalStrut(60));
        updateSubPane2.add(label5);

        //Adding all the text fields to the sub-sub-JPanel: updateSubPane3 with appropriate vertical spacing
        updateSubPane3.add(Box.createVerticalStrut(45));
        updateSubPane3.add(updateTextField1);
        updateSubPane3.add(Box.createVerticalStrut(10));
        updateSubPane3.add(updateTextField2);
        updateSubPane3.add(Box.createVerticalStrut(10));
        updateSubPane3.add(updateTextField3);

        //Adding all the buttons to the sub-sub-JPanel: updateSubPane4 with appropriate vertical spacing and button alignment
        updateSubPane4.add(Box.createVerticalStrut(58));
        updateButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateSubPane4.add(updateButton1);
        updateSubPane4.add(Box.createVerticalStrut(10));
        updateButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateSubPane4.add(updateButton2);
        updateSubPane4.add(Box.createVerticalStrut(10));
        updateButton3.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateSubPane4.add(updateButton3);
        
        //Adding all the sub-sub-JPanels to sub-JPanels: updatePane1 and updatePane2
        updateSubPane1.add(updateSubPane2);
        updateSubPane1.add(updateSubPane3);
        updatePane1.add(updateSubPane1);
        updatePane1.add(updateSubPane4);
        updatePane2.add(scrollPane);

        //Adding the sub JPanels: updatePane1 and updatePane2 to the main JPanel for "Buy" option
        updateMain.add(updatePane1);        
        updateMain.add(updatePane2);
    }

    /**
     * This method sets up the panel that is visible to the user when the "Search" option is selected
     */
    private void setSearchMainPanel(){
        //Creating sub-panels and sub-sub-panels which will contain different GUI objects
        searchPane1=new JPanel();
        searchPane2=new JPanel();
        searchSubPane1=new JPanel();
        searchSubPane2=new JPanel();
        searchSubPane3=new JPanel();
        searchSubPane4=new JPanel();
        searchSubPane5=new JPanel();

        //Creating labels for the textFields to be displayed on the JPanel
        JLabel label1=new JLabel("Searching investments");
        JLabel label2=new JLabel("Symbol");
        JLabel label3=new JLabel("Name");
        JLabel label4=new JLabel("Keywords");
        JLabel label5=new JLabel("Low Price");
        JLabel label6=new JLabel("High Price");
        JLabel label7=new JLabel("Search Results");

        //Creating buttons to "Search" an investment
        JButton button1=new JButton("Reset");
        button1.setBackground(new Color(135,206,251));
        JButton button2=new JButton("Search");
        button2.setBackground(new Color(135,206,251));

        //Changing the Command name for the "Reset" button and adding all buttons to the ActionListener
        button1.setActionCommand("ResetSea");
        button1.addActionListener(this);
        button2.addActionListener(this);

        //Creating text fields to "Search" an investment
        searchTextField1=new JTextField();
        searchTextField2=new JTextField();
        searchTextField3=new JTextField();
        searchTextField4=new JTextField();

        //Setting the dimension for each text field and aligning it
        searchTextField1.setMaximumSize(new Dimension(200,25));
        searchTextField1.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchTextField2.setMaximumSize(new Dimension(300,25));
        searchTextField2.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchTextField3.setMaximumSize(new Dimension(110,25));
        searchTextField3.setAlignmentX(Component.RIGHT_ALIGNMENT);
        searchTextField4.setMaximumSize(new Dimension(110,25));
        searchTextField4.setAlignmentX(Component.RIGHT_ALIGNMENT);

        //Changing the font of every label's text value
        label1.setFont(new Font("Times New Roman",Font.BOLD,16));
        label2.setFont(new Font("Times New Roman",Font.BOLD,16));
        label3.setFont(new Font("Times New Roman",Font.BOLD,16));
        label4.setFont(new Font("Times New Roman",Font.BOLD,16));
        label5.setFont(new Font("Times New Roman",Font.BOLD,16));
        label6.setFont(new Font("Times New Roman",Font.BOLD,16));
        label7.setFont(new Font("Times New Roman",Font.BOLD,16));

        //Setting the layout and dimensions for all the JPanels,sub-JPanels and sub-sub-JPanels to be used
        searchMain.setLayout(new GridLayout(2,1));
        searchPane1.setLayout(new GridLayout(1,2));
        searchPane2.setLayout(new BoxLayout(searchPane2,BoxLayout.PAGE_AXIS));
        searchSubPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchSubPane1.setPreferredSize(new Dimension(100,100));
        searchSubPane2.setLayout(new BoxLayout(searchSubPane2,BoxLayout.PAGE_AXIS));
        searchSubPane3.setLayout(new BoxLayout(searchSubPane3,BoxLayout.PAGE_AXIS));
        searchSubPane3.setPreferredSize(new Dimension(150, 200));
        searchSubPane4.setLayout(new BoxLayout(searchSubPane4,BoxLayout.PAGE_AXIS));
        searchSubPane5.setLayout(new BoxLayout(searchSubPane5,BoxLayout.PAGE_AXIS));

        //Setting the JTextArea for "Update" and making it scrollable 
        searchMessage=new JTextArea(5,20);
        JScrollPane scrollPane=new JScrollPane(searchMessage);
        scrollPane.setMaximumSize(new Dimension(1300,900));
        searchMessage.setEditable(false);     //it cannot be edited by the user
        
        //Adding all the labels to the sub-sub-JPanel:searchSubPane2 with appropriate vertical spacing
        searchSubPane2.add(label1);
        searchSubPane2.add(Box.createVerticalStrut(30));
        searchSubPane2.add(label2);
        searchSubPane2.add(Box.createVerticalStrut(15));
        searchSubPane2.add(label3);
        searchSubPane2.add(Box.createVerticalStrut(7));
        searchSubPane2.add(label4);
        searchSubPane2.add(Box.createVerticalStrut(15));
        searchSubPane2.add(label5);
        searchSubPane2.add(Box.createVerticalStrut(15));
        searchSubPane2.add(label6);
        searchSubPane2.add(Box.createVerticalStrut(60));
        searchSubPane2.add(label7);

        //Adding all the text fields to the sub-sub-JPanel: searchSubPane3 with appropriate vertical spacing
        searchSubPane3.add(Box.createVerticalStrut(17));
        searchSubPane3.add(searchTextField1);
        searchSubPane3.add(Box.createVerticalStrut(15));
        searchSubPane3.add(searchTextField2);
        searchSubPane3.add(Box.createVerticalStrut(17));
        searchSubPane3.add(searchTextField3);
        searchSubPane3.add(Box.createVerticalStrut(15));
        searchSubPane3.add(searchTextField4);

        //Adding all the buttons to the sub-sub-JPanel: searchSubPane4 with appropriate vertical spacing and button alignment
        searchSubPane4.add(Box.createVerticalStrut(58));
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchSubPane4.add(button1);
        searchSubPane4.add(Box.createVerticalStrut(45));
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchSubPane4.add(button2);
        
        //Adding all the sub-sub-JPanels to sub-JPanels: searchPane1 and searchPane2
        searchSubPane1.add(searchSubPane2);
        searchSubPane1.add(searchSubPane3);
        searchPane1.add(searchSubPane1);
        searchPane1.add(searchSubPane4);
        searchPane2.add(scrollPane);

        //Adding the sub JPanels: searchPane1 and searchPane2 to the main JPanel for "Search" option
        searchMain.add(searchPane1);
        searchMain.add(searchPane2);  
    }


    /**
     * This method determines which event was generated if the user clicked any button,
     * selected an option from the main menu or the ComboBox
     * @param e ActionEvent contains the Command name and its source
     */
    public void actionPerformed(ActionEvent e){
        boolean retVal1=false;
        boolean retVal2=false;
        String buttonStr=e.getActionCommand();
        JComboBox cb;

        if(buttonStr.equals("BuyM")){      //If the user selected "Buy", we remove all the components from the JFrame and make only its JPanel visible
            getContentPane().removeAll();
            add(buyMain);                  //Removing all components from the JFrame and adding only the JPanel for "Buy"

            homePane.setVisible(false);
            buyMain.setVisible(true);      //Only the JPanel for "Buy" should be visible
            sellMain.setVisible(false);
            updateMain.setVisible(false);
            getGainMain.setVisible(false);
            searchMain.setVisible(false);

            buyMessage.setText("");
        }
        else if(buttonStr.equals("SellM")){   //If the user selected "Sell", we remove all the components from the JFrame and make only its JPanel visible
            getContentPane().removeAll();
            add(sellMain);                    //Removing all components from the JFrame and adding only the JPanel for "Buy"

            homePane.setVisible(false);
            buyMain.setVisible(false);
            sellMain.setVisible(true);        //Only the JPanel for "Buy" should be visible
            updateMain.setVisible(false);
            getGainMain.setVisible(false);
            searchMain.setVisible(false);

            sellMessage.setText("");

            if(investments.size()==0){        //If we have no investments, we print this message in its message area
                sellMessage.setText("There are currently no investments to sell\n");
            }
        }
        else if(buttonStr.equals("UpdateM")){     //If the user selected "Update", we remove all the components from the JFrame and make only its JPanel visible
            getContentPane().removeAll();
            add(updateMain);                 //Removing all components from the JFrame and adding only the JPanel for "Update"

            homePane.setVisible(false);
            buyMain.setVisible(false);
            sellMain.setVisible(false);
            updateMain.setVisible(true);     //Only the JPanel for "Update" should be visible
            getGainMain.setVisible(false);
            searchMain.setVisible(false);

            //All the text fields will initally display no characters
            updateTextField1.setText("");
            updateTextField2.setText("");
            updateTextField3.setText("");
            updateMessage.setText("");
            
            updateButton1.setEnabled(false);   //Initally the "Prev" button will always be disabled 
            updateButton2.setEnabled(true);    //Initally the "Next" button will be enabled and the user will have to press it to start seeing the investments
            updateButton3.setEnabled(true);    //Initally the "Save" button will be enabled
            updateTextField3.setEnabled(false);   //Initally the "Price" text field will be disaled as there will be no investment to update, and only when the user presses the "Next" button for the first time, will this be enabled

            if(investments.size()==0){         //If we have no investments, then we disable all the buttons
                updateButton1.setEnabled(false);
                updateButton2.setEnabled(false);
                updateButton3.setEnabled(false);
                updateMessage.setText("There are currently no investments to update");
            }
        }
        else if(buttonStr.equals("GetGainM")){     //If the user selected "getGain", we remove all the components from the JFrame and make only its JPanel visible
            getContentPane().removeAll();
            add(getGainMain);                      //Removing all components from the JFrame and adding only the JPanel for "getGain"

            homePane.setVisible(false);
            buyMain.setVisible(false);
            sellMain.setVisible(false);
            updateMain.setVisible(false);
            getGainMain.setVisible(true);          //Only the JPanel for "getGain" should be visible
            searchMain.setVisible(false);

            getGainMethod(investments,getGainTextField1,getGainMessage);    //this method calcualtes total gain
        }
        else if(buttonStr.equals("SearchM")){      //If the user selected "Search", we remove all the components from the JFrame and make only its JPanel visible
            getContentPane().removeAll();
            add(searchMain);

            homePane.setVisible(false);
            buyMain.setVisible(false);
            sellMain.setVisible(false);
            updateMain.setVisible(false);
            getGainMain.setVisible(false);
            searchMain.setVisible(true);            //Removing all components from the JFrame and adding only the JPanel for "Search"

            searchMessage.setText("");
            if(investments.size()==0){   //If we have no investments, this message is printed in the message area
                searchMessage.setText("There are currently no investments to search\n");
            }
        }
        else if(buttonStr.equals("quit")){        //If the user selected "quit"
            writeToFile(args,investments);        //We write all the investments to the txt file
            System.exit(0);    //Terminating the program immediately
        }
        else if(buttonStr.equals("Prev")){     //If the user pressed the "Prev" button from the "Update" JPanel
            prevButton(investments,updateTextField1,updateTextField2,updateTextField3,updateButton1,updateButton2);
        }
        else if(buttonStr.equals("Next")){     //If the user pressed the "Next" button from the "Update" JPanel
            nextButton(investments,updateTextField1,updateTextField2,updateTextField3,updateButton2,updateButton1);
        }
        else if(buttonStr.equals("Save")){     //If the user pressed the "Save" button from the "Update" JPanel
            saveButton(investments,updateTextField1,updateTextField3,updateMessage);
        }
        else if(buttonStr.equals("Buy")){      //If the user pressed the "Buy" button from the "Buy" JPanel
            buyButton(investments,nameAndIndex,typeList,buyTextField2,buyTextField3,buyTextField4,buyTextField5,buyMessage);
        }
        else if(buttonStr.equals("Sell")){     //If the user pressed the "Sell" button from the "Sell" JPanel
            sellButton(investments,nameAndIndex,sellTextField1,sellTextField2,sellTextField3,sellMessage);
        }
        else if(buttonStr.equals("Search")){   //If the user pressed the "Search" button from the "Search" JPanel
            searchButton(investments,nameAndIndex,investmentsSearch,searchTextField1,searchTextField2,searchTextField3,searchTextField4,searchMessage);
            
        }
        else if(buttonStr.equals("ResetSel")){   //If the user pressed the "Reset" button from the "Sell" JPanel
            sellTextField1.setText("");          //clearing all the text fields
            sellTextField2.setText("");
            sellTextField3.setText("");
        }
        else if(buttonStr.equals("ResetSea")){   //If the user pressed the "Reset" button from the "Search" JPanel
            searchTextField1.setText("");        //Clearing all the text fields
            searchTextField2.setText("");
            searchTextField3.setText("");
            searchTextField4.setText("");
        }

        else if(buttonStr.equals("ResetB")){     //If the user pressed the "Reset" button from the "Buy" JPanel
            buyTextField2.setText("");           //Clearing all the text fields
            buyTextField3.setText("");
            buyTextField4.setText("");
            buyTextField5.setText("");
        }
    }

    /**
     * This method fetches the investment's name,symbol,quantity and price from their respective text fields, 
     * buys that investment, adds it to the ArrayList of investments,add its keywords to the HashMap:
     * nameAndIndex and prints appropriate messages to its respective text area when the "Buy" button from the
     * "Buy" JPanel is pressed
     * @param investments Arraylist representing all the investments
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * @param typeList JComboBox which contains two elements: "stock" and "mutualfund"
     * @param buyTextField2 JTextField which contains the investment's symbol
     * @param buyTextField3 JTextField which contains the investment's name
     * @param buyTextField4 JTextField which contains the investment's quantity
     * @param buyTextField5 JTextField which contains the investment's price
     * @param buyMessage JTextArea which is the message area for the "Buy" JFrame
     */

    public static void buyButton(ArrayList<Investment> investments,HashMap<String,ArrayList<Integer>> nameAndIndex,JComboBox<String> typeList,JTextField buyTextField2,JTextField buyTextField3,JTextField buyTextField4,JTextField buyTextField5,JTextArea buyMessage){
        String type=(String)typeList.getSelectedItem();
        String textField1=buyTextField2.getText();
        String textField2=buyTextField3.getText();
        String textField3=buyTextField4.getText();
        String textField4=buyTextField5.getText();
        int quantity;
        int duplicateFound;
        int index=Investment.symbolExists(textField1,investments);
        double[] retArray=new double[2];
        double price;
        double bookValue=0;
        boolean retVal=false;
        boolean retVal2=true;
        Stock newStock;
        MutualFund newMutualFund;
        String customMsg="";

        if(textField1.isEmpty() || textField2.isEmpty() || textField3.isEmpty() || textField4.isEmpty()){
            buyMessage.append("One of the fields was left blank\n");
        }
        else{         //If all the fields were given values  
            if(index==-1){             //A new investment is to be added
                if(type.equalsIgnoreCase("stock")){
                    try{
                        newStock=new Stock(textField1.toUpperCase(),textField2,textField3,textField4,bookValue);   //A new instance of Stock is created
                        investments.add(newStock);      //The new stock(investment) is added to its arrayList
                        buyMessage.append("You bought "+textField3+" shares of "+"the stock: "+textField1.toUpperCase()+" for $"+((Integer.parseInt(textField3)*Double.parseDouble(textField4))+STOCKCOMMISSION)+"\n");
                        buyHashIndex(newStock,nameAndIndex,investments);     //Updating the hashMap
                    }
                    catch(IllegalArgumentException e){    //If price or quantity was invalid, we print an appropriate message
                        buyMessage.append(e.getMessage()+"\n");
                    }
                }
                else if(type.equalsIgnoreCase("mutualfund")){
                    try{
                        newMutualFund=new MutualFund(textField1.toUpperCase(),textField2,textField3,textField4,bookValue);   //A new instance of MutualFund is created
                        investments.add(newMutualFund);     //The new mutualfund(investment) is added to its arrayList
                        buyMessage.append("You bought "+textField3+" shares of "+"the mutualfund: "+textField1.toUpperCase()+" for $"+((Integer.parseInt(textField3)*Double.parseDouble(textField4)))+"\n");
                        buyHashIndex(newMutualFund,nameAndIndex,investments);    //Updating the hashMap
                    }
                    catch(IllegalArgumentException e){          //If price or quantity was invalid, we print an appropriate message
                        buyMessage.append(e.getMessage()+"\n");
                    }
                }
            }
            else{     //If shares of an existing investment are to be bought
                duplicateFound=Investment.duplicateInvestment(type,textField1,investments);    //Returns 1 if it is a duplicate symbol
                if(duplicateFound==1){
                    if(type.equalsIgnoreCase("stock")){    //We are trying to add a new stock with the same symbol as that of a mutualfund
                        buyMessage.append("A mutualfund with this symbol already exists\n");
                    }
                    else if(type.equalsIgnoreCase("mutualfund")){     //We are trying to add a new mutualfund with the same symbol as that of a stock
                        buyMessage.append("A stock with this symbol already exists\n");
                    }
                }
                else{  //If we are buying existing shares of a stock/mutualfund
                    if(type.equalsIgnoreCase("stock")){
                        try{            //When quantity does not have digits as characters or if the digit is less than 1
                            validQuantity(textField3);
                        }
                        catch(IllegalArgumentException e){   //We print the appropriate message
                            retVal2=false;        //valdidity check for quantity failed
                            customMsg=e.getMessage().replace("price range","quantity field");
                            buyMessage.append(customMsg+"\n");
                        }
                        try{             //When price does not have digits as characters or if the digit is less than 1
                            validPrice(textField4);
                        }
                        catch(IllegalArgumentException e){  //We print the appropriate message
                            retVal2=false;         //valdidity check for price failed
                            buyMessage.append(e.getMessage()+"\n");
                        }
                        if(retVal2){      //If both price and quantity are valid
                            retArray=Stock.buy(index,textField3,textField4,investments);     //Storing the updated bookValue and quantity in retArray
                            investments.get(index).setPrice(textField4);         //Changing the price,bookValue and quantity of that investment(stock)
                            investments.get(index).setBookValue(retArray[0]);
                            investments.get(index).setQuantity(String.valueOf((int)retArray[1]));
                            buyMessage.append("You bought "+textField3+" shares of "+"the stock: "+textField1.toUpperCase()+" for $"+String.format("%.2f",(Integer.parseInt(textField3)*Double.parseDouble(textField4)+STOCKCOMMISSION))+"\n");
                        }    
                        
                    }
                    else if(type.equalsIgnoreCase("mutualfund")){
                        try{               //When quantity does not have digits as characters or if the digit is less than 1
                            validQuantity(textField3);
                        }
                        catch(IllegalArgumentException e){     //We print the appropriate message
                            retVal2=false;       //valdidity check for quantity failed
                            customMsg=e.getMessage().replace("price range","quantity field");
                            buyMessage.append(customMsg+"\n");
                        }
                        try{              //When price does not have digits as characters or if the digit is less than 1
                            validPrice(textField4);
                        }
                        catch(IllegalArgumentException e){     //We print the appropriate message
                            retVal2=false;      //valdidity check for price failed
                            buyMessage.append(e.getMessage()+"\n");
                        }
                        if(retVal2){   //If both price and quantity are valid
                            retArray=MutualFund.buy(index,textField3,textField4,investments);     //Storing the updated bookValue and quantity in retArray
                            investments.get(index).setPrice(textField4);              //Changing the price,bookValue and quantity of that investment(mutualfund)
                            investments.get(index).setBookValue(retArray[0]);
                            investments.get(index).setQuantity(String.valueOf((int)retArray[1]));
                            buyMessage.append("You bought "+textField3+" shares of "+"the mutualfund: "+textField1.toUpperCase()+" for $"+String.format("%.2f",(Integer.parseInt(textField3)*Double.parseDouble(textField4)))+"\n");  
                        }    
                    }
                }
            }
        }          
    }


    /**
     * This method fetches the investment's symbol,quantity and price from their respective text fields, 
     * sells that investment, removes it from the ArrayList of investments,removes its keywords from the HashMap:
     * nameAndIndex and prints appropriate messages to its respective text area when the "Sell" button from the
     * "Sell" JPanel is pressed
     * @param investments Arraylist representing all the investments
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * @param sellTextField1 JTextField which contains the investment's symbol
     * @param sellTextField2 JTextField which contains the investment's quantity
     * @param sellTextField3 JTextField which contains the investment's price
     * @param sellMessage JTextArea which is the message area for the "Sell" JFrame
     */

    public static void sellButton(ArrayList<Investment> investments,HashMap<String,ArrayList<Integer>> nameAndIndex,JTextField sellTextField1,JTextField sellTextField2,JTextField sellTextField3,JTextArea sellMessage){
        String textField1=sellTextField1.getText();
        String textField2=sellTextField2.getText();
        String textField3=sellTextField3.getText();
        int index=Investment.symbolExists(textField1,investments);
        int availQuantity;
        boolean retVal=false;
        int quantity;
        double price;
        double[] retArray=new double[2];
        double[] retArray2=new double[3];

        if(textField1.isEmpty() || textField2.isEmpty() || textField3.isEmpty()){   //If any text field was left blank
            sellMessage.append("One of the fields was left blank\n");
        }
        else{     //If all the fields were given values
            if(index==-1){     //If the investment to be sold does not exist
                sellMessage.append("The symbol does not exist\n");

                try{      //When quantity does not have digits as characters or if the digit is less than 1
                    quantityException(textField2);
                }
                catch(IllegalArgumentException e){   //We print the appropriate message
                    sellMessage.append(e.getMessage());
                }
                try{      //When price does not have digits as characters or if the digit is less than 1
                    priceException(textField3);
                }
                catch(IllegalArgumentException e){    //We print the appropriate message
                    sellMessage.append(e.getMessage());
                }
            }
            else{    //If the investment to be sold exists
                availQuantity=Integer.parseInt(Investment.quantityExists(index,investments));   //Returning the available quantity of that investment
                try{                //When quantity does not have digits as characters or if the digit is less than 1
                    quantityException(textField2);
                }
                catch(IllegalArgumentException e){      //We print the appropriate message
                    sellMessage.append(e.getMessage());
                    retVal=true;     //valdidity check for quantity failed
                }
                try{                //When price does not have digits as characters or if the digit is less than 1
                    priceException(textField3);
                }
                catch(IllegalArgumentException e){   //We print the appropriate message
                    sellMessage.append(e.getMessage());
                    retVal=true;    //valdidity check for price failed
                }

                if(retVal==false){   //If both price and quantity are valid         
                    quantity=Integer.parseInt(textField2);
                    price=Double.parseDouble(textField3);

                    if(quantity>availQuantity){         
                        sellMessage.append("The requested quantity is greater than the available quantity\n");
                    }

                    else if(quantity==availQuantity){  //If the available and requested quantity are the same
                        retArray=investments.get(index).remove(investments,quantity,price);  //Storing the payment and gain in retArray
                        sellMessage.append("Sold "+quantity+" shares of "+textField1.toUpperCase()+" for "+String.format("%.2f",retArray[0]));
                        sellMessage.append("\nYour gain is: "+String.format("%.2f",retArray[1])+"\n\n");

                        sellHashIndex(index,nameAndIndex,investments);     //Updating the hashMap
                        investments.remove(index);          //Removing that investment from its arrayList
                    }

                    else{             //If partial shares are sold
                        retArray2=investments.get(index).sell(quantity,availQuantity,price,investments);  //Storing the payment,gain and the updated bookValue in retArray  
                        sellMessage.append("Sold "+quantity+" shares of "+textField1.toUpperCase()+" for "+String.format("%.2f",retArray2[0]));
                        sellMessage.append("\nYour gain is: "+String.format("%.2f",retArray2[1])+"\n\n");

                        investments.get(index).setPrice(textField3);                 //Updating the price,quantity and bookValue of that investment
                        investments.get(index).setQuantity(String.valueOf(availQuantity-quantity));
                        investments.get(index).setBookValue(retArray2[2]);
                    }
                }
            }
        }   
    }

    /**
     * This method enables/disables the "Prev" button based on the index position of the investment being 
     * displayed and when pressed displays the previous investment
     * @param investments Arraylist representing all the investments
     * @param updateTextField1 JTextField which contains the investment's symbol
     * @param updateTextField2 JTextField which contains the investment's name
     * @param updateTextField3 JTextField which contains the investment's price
     * @param button JButton which is the "Prev" button from the "Update" JFrame
     * @param button2 JButton which is the "Next" button from the "Update" JFrame
     */

    public static void prevButton(ArrayList<Investment> investments,JTextField updateTextField1,JTextField updateTextField2,JTextField updateTextField3,JButton button,JButton button2){
        String symbol=updateTextField1.getText();
        int index=Investment.symbolExists(symbol,investments);
        String strPrice;

        if(index!=investments.size()-2){      //Enabling the "Next" button when the "Prev" button is pressed to allow traversal of investments in both ways
            button2.setEnabled(true);
        }
        
        if(investments.size()>1){       //Displaying each investment's details and updating its price
            strPrice=String.valueOf(investments.get(index-1).getPrice());
            updateTextField1.setText(investments.get(index-1).getSymbol());
            updateTextField2.setText(investments.get(index-1).getName());
            updateTextField3.setText(strPrice);

            if(index-1==0){            //Disabling the "Prev" button when the first investment is being displayed
                button.setEnabled(false); 
            }
        }
    }

    /**
     * This method enables/disables the "Next" button based on the index position of the investment being 
     * displayed and when pressed displays the next investment
     * @param investments Arraylist representing all the investments
     * @param updateTextField1 JTextField which contains the investment's symbol
     * @param updateTextField2 JTextField which contains the investment's name
     * @param updateTextField3 JTextField which contains the investment's price
     * @param button JButton which is the "Prev" button from the "Update" JFrame
     * @param button2 JButton which is the "Next" button from the "Update" JFrame
     */

    public static void nextButton(ArrayList<Investment> investments,JTextField updateTextField1,JTextField updateTextField2,JTextField updateTextField3,JButton button,JButton button2){
        String symbol=updateTextField1.getText();
        int index=Investment.symbolExists(symbol,investments);
        String strPrice;

        if(index==-1){     //The first time, user presses "Next", the "Price" field should be editable
            updateTextField3.setEnabled(true);   
        }

        if(index+1==1){    //When user selects the "Update" option, "Next" button should be enabled
            button2.setEnabled(true); 
        }
        
        //If there is only one investment, "Prev" button is disabled and "Next" button is alrady disabled
        if(investments.size()==1){    
            strPrice=String.valueOf(investments.get(0).getPrice());
            updateTextField1.setText(investments.get(0).getSymbol());
            updateTextField2.setText(investments.get(0).getName());
            updateTextField3.setText(strPrice);
            button.setEnabled(false);  
        }
        //The first time user pressed the "Next" button, the first investment's details are shown and price is updated
        else if(updateTextField1.getText().isEmpty()){
            strPrice=String.valueOf(investments.get(0).getPrice());
            updateTextField1.setText(investments.get(0).getSymbol());
            updateTextField2.setText(investments.get(0).getName());
            updateTextField3.setText(strPrice);          //Updating the first investment's price
        }
        else if(investments.size()>1){               //If we have more than one investment
            if(index==investments.size()-1){         //Displaying each investment's details and updating its price
                strPrice=String.valueOf(investments.get(index).getPrice());
                updateTextField1.setText(investments.get(index).getSymbol());
                updateTextField2.setText(investments.get(index).getName());
                updateTextField3.setText(strPrice);
            }
            else{                                   //Displaying last investment's details and updating its price
                strPrice=String.valueOf(investments.get(index+1).getPrice());
                updateTextField1.setText(investments.get(index+1).getSymbol());
                updateTextField2.setText(investments.get(index+1).getName());
                updateTextField3.setText(strPrice);

                if(index==investments.size()-2){    //We disable the "Prev" button when the first investment is displayed 
                    button.setEnabled(false);
                }
            }  
        }  
    }

    /**
     * This method updates the investment's price and prints appropriate messsages to its respective text area
     * when the "Update" button from the "Update" JPanel is pressed 
     * @param investments Arraylist representing all the investments
     * @param updateTextField1 JTextField which contains the investment's symbol
     * @param updateTextField3 JTextField which contains the investment's price
     * @param updateMessage JTextArea which is the message area for the "Update" JFrame
     */
    public static void saveButton(ArrayList<Investment> investments,JTextField updateTextField1,JTextField updateTextField3,JTextArea updateMessage){
        boolean retVal;
        String symbol=updateTextField1.getText();
        String strPrice=updateTextField3.getText();
        int index=Investment.symbolExists(symbol,investments);

        if(strPrice.isEmpty()){                 //If the "Price" field was left blank
            updateMessage.append("You left the price field blank\n");
        }
        else{
            try{    //If the entered "Price" is valid, we update the price
                investments.get(index).setPrice(strPrice);
                updateMessage.append("The updated investment is:\n"+investments.get(index).toString()+"\n\n");
            }
            catch(IllegalArgumentException e){    //If "Price" does not consist of valid digits greater than 1
                updateMessage.append(e.getMessage()+"\n");
            }
        }   
    }

    /**
     * This method displays the total gain if all investments were to be sold and displays the potential profit
     * in the "Total gain" text field and also prints indivudal gain of all the investments to its 
     * respective text area
     * @param investments Arraylist representing all the investments
     * @param getGainTextField1 JTextField which will display the total gain of the Portfolio
     * @param getGainMessage JTextArea which is the message area for the "getGain" JFrame
     */
    public static void getGainMethod(ArrayList<Investment> investments,JTextField getGainTextField1,JTextArea getGainMessage){
        double investmentGain=0;
        getGainMessage.setText("");      //Initally the text field and area should be cleared
        getGainTextField1.setText("");

        if(investments.isEmpty()){    //We have no investments to calculate the gain for
            getGainMessage.setText("There are currently no investments");
        }

        else{                                               
            investmentGain=Investment.calculateGain(investments,getGainMessage);      //Storing the total gain for all the investments by calling its related method
            getGainTextField1.setText(String.format("%.2f",investmentGain));
        }
    }

    /**
     * This method fetches the investment's symbol,name(KeyWord),low price and high price from their
     * respective text fields, searches for investment/investments that match the criteria and
     * prints appripriate messages to its respective text area when the "Search" button from the
     * "Search" JPanel is pressed
     * @param investments Arraylist representing all the investments
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * @param investmentsSearch Arraylist represting the index/indices of investments which match the criteria
     * @param textField1 JTextField which contains the investment's symbol
     * @param textField2 JTextField which contains the investment's name(KeyWord)
     * @param textField3 JTextField which contains the investment's low price
     * @param textField4 JTextField which contains the investment's high price
     * @param searchMessage JTextArea which is the message area for the "Search" JFrame
     */
    public static void searchButton(ArrayList<Investment> investments,HashMap<String,ArrayList<Integer>> nameAndIndex,ArrayList<Integer> investmentsSearch,JTextField textField1, JTextField textField2,JTextField textField3,JTextField textField4,JTextArea searchMessage){
        int i;
        int rangeType;
        boolean retVal=true;
        boolean check=true;
        String symbol=textField1.getText();
        String keyWord=textField2.getText();
        String lower=textField3.getText();
        String upper=textField4.getText();
        String priceRange="-";
        String customMsg="";

        if(symbol.isEmpty() && keyWord.isEmpty() && lower.isEmpty() && upper.isEmpty()){   //If all the 3 fields were left blank         
            searchMessage.append("The following investments match this criteria:"+"\n");
            for(i=0;i<investments.size();++i){            //We print all the investments
                searchMessage.append((i+1)+". "+investments.get(i)+"\n");
            }
            searchMessage.append("\n");
        }
        else{              //If even a single field was given a value
            if(!lower.isEmpty()){       //If a lower range was entered
                try{
                    validQuantity(lower);     //Validating the lower range which may throw an exception
                }
                catch(IllegalArgumentException e){
                    check=false;              //If it is invalid, we cannot search
                    customMsg=e.getMessage().replace("price","lower price");    //Printing the message to search's text area
                    searchMessage.append(customMsg+"\n");
                }
            }
            if(!upper.isEmpty()){    //If an upper range was entered
                try{
                    validQuantity(upper);     //Validating the upper range which may throw an exception
                }
                catch(IllegalArgumentException e){
                    check=false;              //If it is invalid, we cannot search
                    customMsg=e.getMessage().replace("price","upper price");      //Printing the message to search's text area
                    searchMessage.append(customMsg+"\n");
                }
            }

            //If either the lower or upper range was entered and is/are valid
            if(check){    
                priceRange=lower+priceRange+upper;         //priceRange=lower-upper
                if(priceRange.equals("-")){      //If user gave no lower and upper range but enterd value for any other fields
                    priceRange="";               //This means that price Range would contain just '-' but we make it empty
                }
                rangeType=validPriceRange(priceRange);       //Finding the kind of price range that was entered

                if(rangeType==3){                      //If a lower and upper range is entered, this validates it
                    retVal=validUpperAndLower(priceRange,searchMessage);
                    
                }
                if(retVal){    //retVal is true by default but if an invalid lower and upper range was entered, it is false
                    investmentsSearch=investmentSearch(symbol,keyWord,priceRange,rangeType,investments,nameAndIndex);       //This method returns indices of investments which match the criteria

                    if(investmentsSearch.isEmpty()){       //If it is empty, then no investments match the criteria
                            searchMessage.append("There are no investments with this criteria\n");
                    }
                    else{       //Else it contains index/indices of investments that match the criteria
                        searchMessage.append("\nThe following investments match this criteria:\n");
                        for(i=0;i<investmentsSearch.size();++i){           //Printing the investments at those indices
                            searchMessage.append((i+1)+". "+investments.get(investmentsSearch.get(i))+"\n");
                        }
                    } 
                }
            }
        }    
    }

    /**
     * This method will write the investments from its ArrayList to the text file 
     * when the program is terminated
     * @param fileName String representing the file name which will be paseed as command-line arguement
     * by the user
     * @param investments Arraylist representing all the investments
     */

    public static void writeToFile(String fileName,ArrayList<Investment> investments){
        int i;
        PrintWriter fileOutput=null;
        try{                                   //Creating a new fileOutput object to write data to the file
            fileOutput=new PrintWriter(new FileOutputStream(fileName));
        }
        catch(FileNotFoundException e){        //Catch the exception if we do get one
            System.out.println("There was an error in accessing the file");
        }

        for(i=0;i<investments.size();++i){
            if(investments.get(i) instanceof Stock){          //If this investment is an instance of Stock
                fileOutput.println("type = stock");
            }
            else if(investments.get(i) instanceof MutualFund){     //If this investment is an instance of MutualFund
                fileOutput.println("type = mutualfund");
            }                                                      //Adding all the other relevant data to the file
            fileOutput.println("symbol = "+investments.get(i).getSymbol());
            fileOutput.println("name = "+investments.get(i).getName());
            fileOutput.println("quantity = "+investments.get(i).getQuantity());
            fileOutput.println("price = "+investments.get(i).getPrice());
            fileOutput.println("bookValue = "+investments.get(i).getBookValue());
            fileOutput.print("\n");      //To denote the end of an investment entry
        }
        fileOutput.close();            
    }

    /**
     * This method will read the investments from the text file and store it in the investments ArrayList
     * at the beginning of the program
     * @param fileName String representing the file name which will be paseed as command-line arguement
     * by the user
     * @param investments Arraylist representing all the investments
     */

    public static void readFile(String fileName,ArrayList<Investment> investments){
        int i;
        Scanner fileInput=null;
        Stock newStock;
        MutualFund newMutualFund;
        String[] tokens;
        String investment="";
        String symbol="";
        String name="";
        String type="";
        String quantity="";
        String price="";
        double bookValue=0;
        int count=0;

        try{
            fileInput=new Scanner(new FileInputStream(fileName));  //Creating a new Scanner object to read input from the file
            while(fileInput.hasNextLine()){             //While the file still has lines to be read
                count=count+1;
                investment=fileInput.nextLine();        //Getting a specific line from the text file
                tokens=investment.split("[ ]+");        //Splitting it based on spaces

                if(count==1){                           //The first word is always the investment type
                    if(tokens[2].equalsIgnoreCase("stock")){
                        type="stock";
                    }
                    else if(tokens[2].equalsIgnoreCase("mutualfund")){
                        type="mutualfund";
                    }
                }
                else if(count==2){                      //The second word is always the investment symbol
                    symbol=tokens[2];
                }
                else if(count==3){                      //The third word is always the investment name
                    name="";
                    if(tokens.length>3){                //If the name is more than a word long
                        for(i=2;i<tokens.length;++i){
                            if(name.isEmpty()){
                                name=name+tokens[i];
                            }
                            else{
                                name=name+" "+tokens[i];
                            }
                        }
                    }
                    else{                            //If the name is just one word long
                        name=tokens[2];
                    }
                }
                else if(count==4){                  //The fourth word is always the investment quantity
                    quantity=tokens[2];
                }
                else if(count==5){                  //The fifth word is always the investment price
                    price=tokens[2];
                }
                else if(count==6){                  //The sixth word is always the investment bookValue
                    bookValue=Double.parseDouble(tokens[2]);
                }
                else if(count==7){                  //The next line will always be an empty line
                    count=0;                        //We reset the counter
                                            
                    if(type.equalsIgnoreCase("stock")){  //Creating a new Stock object and adding it to the investments ArrayList
                        newStock=new Stock(symbol,name,quantity,price,bookValue);
                        newStock.setBookValue(bookValue);     //Changing the bookValue as the Stock constructor will calculate the bookValue based on price and quantity and change it 
                        investments.add(newStock);
                    }
                    else if(type.equalsIgnoreCase("mutualfund")){     //Creating a new MutualFund object and adding it to the investments ArrayList
                        newMutualFund=new MutualFund(symbol,name,quantity,price,bookValue);
                        newMutualFund.setBookValue(bookValue);      //Changing the bookValue as the MutualFund constructor will calculate the bookValue based on price and quantity and change it
                        investments.add(newMutualFund);
                    }
                }
            }
        }
        catch(FileNotFoundException e){         //The first time the program is run the file does not exist, so we catch that exception
            System.out.println("\nOur Portfolio is Currently Empty");
        }
        finally{
            if(fileInput!=null){                //We only close the file if it was opened(only possible if it exists)
               fileInput.close(); 
            }
        }  
    }

    /**
     * This method will initialize the HashMap with values based on the values in the text file
     * at the beginning of the program
     * @param investments Arraylist representing all the investments
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * Each keyword is represented by a string while the index/indices of that keyword are represented by
     * the ArrayList of Integers
     */

    public static void loadFromFile(ArrayList<Investment> investments,HashMap<String,ArrayList<Integer>> nameAndIndex){
        String[] tokens;
        ArrayList<Integer> indices;
        String name="";
        int i;
        int j;

        for(i=0;i<investments.size();++i){               //iterating through all the investments
            tokens=investments.get(i).getName().split("[ ]+");    //Getting the name(word tokens) of the investment which will be added to the hashMap
            for(j=0;j<tokens.length;++j){                         //iterating through all the keyWords
                if(nameAndIndex.containsKey(tokens[j])==false){   //If that keyWord is not a key in the hashMap
                    indices=new ArrayList<Integer>();             //We create a new ArrayList for its indices
                    indices.add(i);                               //Adding its index position
                    nameAndIndex.put(tokens[j],indices);          //Adding a new Key,Value pair
                }
                else if(nameAndIndex.containsKey(tokens[j])){     //If that keyWord is a key in the hashMap
                    indices=new ArrayList<Integer>();
                    indices=nameAndIndex.get(tokens[j]);          //Getting the indices of that keyWord from the hashMap
                    indices.add(i);                               //Adding the new index position of that keyWord
                    nameAndIndex.put(tokens[j],indices);          //Updating that Key,Value pair
                }
            }
        }
        
    }

    /**
     * This method will update the HashMap when a new investment is added to its ArrayList
     * @param investment Investment representing the specific investment to be added to its ArrayList
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * Each keyword is represented by a string while the index/indices of that keyword are represented by
     * the ArrayList of Integers
     * @param investments Arraylist representing all the investments
     */

    public static void buyHashIndex(Investment investment,HashMap<String,ArrayList<Integer>> nameAndIndex,ArrayList<Investment> investments){
        int i;
        int index;
        String[] tokens;
        ArrayList<Integer> indices;
        tokens=investment.getName().split("[ ]+");           //Getting the name(word tokens) of the investment which will be added
        index=Investment.symbolExists(investment.getSymbol(),investments);    //Getting the new investment's index position

        for(i=0;i<tokens.length;++i){                        //Iterating through each word(keyWord) of the name
            if(nameAndIndex.containsKey(tokens[i])==false){  //If that keyWord is not a key in the hashMap
                indices=new ArrayList<Integer>();            //We create a new ArrayList for its indices
                indices.add(index);                          //Adding its index position
                nameAndIndex.put(tokens[i],indices);         //Adding a new Key,Value pair
            }
            else if(nameAndIndex.containsKey(tokens[i])){    //If that keyWord is a key in the hashMap
                indices=new ArrayList<Integer>();
                indices=nameAndIndex.get(tokens[i]);         //Getting the indices of that keyWord from the hashMap
                indices.add(index);                          //Adding the new index position of that keyWord
                nameAndIndex.put(tokens[i],indices);         //Updating that Key,Value pair
            }
        }
    }

    /**
     * This method will update the HashMap when an investment is sold and removed from its ArrayList
     * @param investmentFound Integer representing the index position of the investment in its ArrayList
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * Each keyword is represented by a string while the index/indices of that keyword are represented by
     * the ArrayList of Integers
     * @param investments Arraylist representing all the investments
     */

    public static void sellHashIndex(int investmentFound,HashMap<String,ArrayList<Integer>> nameAndIndex,ArrayList<Investment> investments){
        int i;
        String[] tokens;
        String name="";             //Used to store each keyWord 
        ArrayList<Integer> indices; 
        tokens=investments.get(investmentFound).getName().split("[ ]+");    //Getting the name(word tokens) of the investment which will be removed
        Iterator <String> entries=nameAndIndex.keySet().iterator();         //Getting all the keys(keyWords) from the hashMap

        while(entries.hasNext()){
            name=entries.next();
            for(i=0;i<tokens.length;++i){             //Iterating through each word(keyWord) of the name
                if(tokens[i].equalsIgnoreCase(name)){
                    indices=nameAndIndex.get(name);   //Getting the indices of that keyWord
                    indices.remove(Integer.valueOf(investmentFound));     //Removing the index position of that keyWord

                    if(indices.isEmpty()){           //If that keyWord has no indices
                        entries.remove();            //We remove the entire Key,Value pair
                    }
                }   
            }
        }
    }

    /**
     * This method verifies if the price is a valid string consisting of digits and is greater than 0 and
     * throws an exception if one of the criterias is not fulfilled
     * @param price String representing the price of an investment
     * @throws IllegalArgumentException when price does not have digits as characters or
     * if the digit is less than 1
     */

    public static void validPrice(String price){   
        double numPrice;
        try{
            numPrice=Double.parseDouble(price);
            if(numPrice<1) {    //If a string of characters was entered as price
                throw new IllegalArgumentException("Please enter a positive value for the price field");
            }
        }
        catch(NumberFormatException e){   //If price does not consist of valid digits greater than 1
            throw new IllegalArgumentException("You did not enter valid digits for the price field");
        }
    }

    /**
     * This method verifies if the quantity is a valid string consisting of digits and is greater than 0 and
     * throws an exception if one of the criterias is not fulfilled
     * @param quantity String representing the quantity of an investment
     * @throws IllegalArgumentException when quantity does not have digits as characters or
     * if the digit is less than 1
     */

    public static void validQuantity(String quantity){   //This method ensures that the entered quantity is greater than 0
        int numQuantity;
        try{
            numQuantity=Integer.parseInt(quantity);
            if(numQuantity<1) {  //If lower/upper range does not consist of valid digits greater than 1

                throw new IllegalArgumentException("Please enter a positive value for the price range");
            }
        }
        catch(NumberFormatException e){   //If lower/upper range string of characters was entered as quantity
            throw new IllegalArgumentException("You did not enter valid digits for the price range");
        }
    }

    /**
     * This method determines what kind of price range was provided when the user selected the "Search"
     * option and returns an int value based on that
     * Valid Range examples: 10-10,-10,10-,10-20
     * @param priceRange String representing the price range of an investment
     * @return Int,which refers to the kind of price range of an investment 
     * if retVal=-1,price range field was left blank
     * if retVal=1, price range has only an upper range
     * if retVal=2, price range has only a lower range
     * if retVal=3, price range has a lower and upper limit
     */

    public static int validPriceRange(String priceRange){  
        int retVal=0;
        String range="";
        double lowerRange=0;
        double upperRange=0;
        String[] tokens;
        tokens=priceRange.split("[-]+");
        
        if(tokens.length==1){                          //tokens will contain 2 elements if an upper and lower range was given or just an upper range was given
            if(tokens[0].isEmpty()){                   //If the price range field was empty, -1 is returned
                retVal=-1;
            }
            else if(priceRange.charAt(priceRange.length()-1)=='-'){ //If the price range has only a lower range, 2 is returned
                lowerRange=Double.parseDouble(tokens[0]);
                retVal=2;
            }
        }
        else{
            if(tokens[0].isEmpty() && !tokens[1].isEmpty()){        //If the price range has only an upper range, 1 is returned
                upperRange=Double.parseDouble(tokens[1]);
                retVal=1;
            }
            else{                                                   //Else, the price range has a lower and upper limit,3 is returned
                retVal=3;
            }
        }
        return retVal;
    }

    /**
     * This method verifies if the user entered a valid lower and upper range 
     * when the user selected the "Search" option and prints an appropriate message 
     * to its respective text area if it is invalid
     * @param lowerAndUpper String representing the lower and upper price range of an investment
     * @param message JTextArea which is the message area for the "Search" JFrame
     * @return boolean,true if it was valid, else false
     */

    public static boolean validUpperAndLower(String lowerAndUpper,JTextArea message){  //This method ensures that the user gives a valid lower and upper range
        boolean retVal=true;
        String[]tokens;
        tokens=lowerAndUpper.split("[-]+");     //Splitting the price range based on '-'
        if(Double.parseDouble(tokens[0])>Double.parseDouble(tokens[1])){     //If lower range is greater than upper range
            message.append("You entered an invalid lower and upper range\n");
            retVal=false;
        }
        return retVal;
    }

    /**
     * This method returns an ArrayList which contains the index/indices of investments which
     * match the keyword given by the user
     * @param keyWord String representing the keyword to be searched in the ArrayList for investments
     * @param keyWordSearchList Arraylist representing an empty Arraylist which will be used to store
     * the index/indices of investments that match the keyword given by the user and returned later 
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * Each keyword is represented by a string while the index/indices of that keyword are represented by
     * the ArrayList of Integers
     * @return ArrayList,which contains the indices of the matched investments
     */

    public static ArrayList<Integer> keyWordSearch(String keyWord,ArrayList<Integer> keyWordSearchList,HashMap<String,ArrayList<Integer>> nameAndIndex){     //This method returns an ArrayList which contains the index/indices of investments which match the keyword given by the user

        int i;
        int j;
        String[] tokens;
        ArrayList<Integer> indices;
        ArrayList<Integer> indices2;
        HashMap<String, ArrayList<Integer>> lowerHashMap = new HashMap<>();
        keyWord=keyWord.toLowerCase();

        for(HashMap.Entry<String,ArrayList<Integer>> entry: nameAndIndex.entrySet()){        //Creating a duplicate HashMap
            lowerHashMap.put(entry.getKey().toLowerCase(),new ArrayList<>(entry.getValue()));  //Converting each keyWord to lowerCase
        }
        
        tokens=keyWord.split("[ ]+");       //Splitting each keyword based of spaces
        if(tokens.length==1){               //If the string has just one keyWord
            if(lowerHashMap.containsKey(tokens[0])){      //Checking if the HashMap contains that key
                indices=nameAndIndex.get(tokens[0]);      //Getting the indices of that keyWord

                for(i=0;i<indices.size();++i){            //Adding those indices to keyWordSearchList
                    keyWordSearchList.add(indices.get(i));
                }
            }
        }
        else{                               //If the string has more than one word
            for(i=0;i<tokens.length;++i){  
                if(lowerHashMap.containsKey(tokens[i])){
                    indices=new ArrayList<Integer>();
                    indices2=new ArrayList<Integer>();
                    indices=lowerHashMap.get(tokens[i]);      //Getting the indices of that keyWord
                    if(i+1<tokens.length){                    //Getting the indices of the next KeyWord in that string
                        indices2=lowerHashMap.get(tokens[i+1]);
                    }
                    
                    for(j=0;j<indices.size();++j){            //Checking if any two consective keyWords of the string have any common indices
                        if(indices2.contains(indices.get(j))){
                            keyWordSearchList.add(indices.get(j));
                        }
                    }
                }
            }
        }

        return keyWordSearchList;
    }

    /**
     * This method returns an ArrayList which contains the index/indices of investments which
     * match the price range given by the user
     * @param priceRangeSearchList Arraylist representing an empty Arraylist which will be used to store
     * the index/indices of investments that match the criteria given by the user and returned later 
     * @param priceRange String representing the priceRange to be searched in the ArrayList for investments
     * @param retVal Integer representing the format for price range
     * @param investments Arraylist representing all the investments
     * @return ArrayList,which contains the indices of the matched investments
     */

    public static ArrayList<Integer> priceRangeSearch(ArrayList<Integer> priceRangeSearchList,String priceRange, int retVal,ArrayList<Investment> investments){   //This method returns an ArrayList which contains the index/indices of the price range for investments which match the price range user gives for search command
        int i;
        String[]tokens;
        double price;
        double whole;
        double lowerRange;
        double upperRange;
        tokens=priceRange.split("[-]+");
        if(retVal==0){                                   //If the input was a whole number
            whole=Double.parseDouble(tokens[0]);
            for(i=0;i<investments.size();++i){           //Comparing with every investment's quantity     
                price=Integer.parseInt(investments.get(i).getPrice());
                if(price==whole){
                    priceRangeSearchList.add(i);
                }
            }
        }
        else if(retVal==1){                             //If the input was just an upper range
            upperRange=Double.parseDouble(tokens[1]);
            for(i=0;i<investments.size();++i){              //Comparing with every investment's quantity
                price=Integer.parseInt(investments.get(i).getPrice());
                if(price<=upperRange){
                    priceRangeSearchList.add(i);
                }
            }
        }
        else if(retVal==2){                         //If the input was just a lower range
            lowerRange=Double.parseDouble(tokens[0]);
            for(i=0;i<investments.size();++i){           //Comparing with every investment's quantity
                price=Integer.parseInt(investments.get(i).getPrice());
                if(price>=lowerRange){
                    priceRangeSearchList.add(i);
                }
            }
        }
                        
        else if(retVal==3){                      //If the input was an upper and lower range
            lowerRange=Double.parseDouble(tokens[0]);
            upperRange=Double.parseDouble(tokens[1]);
            for(i=0;i<investments.size();++i){        //Comparing with every investment's quantity
                price=Integer.parseInt(investments.get(i).getPrice());
                if(price>=lowerRange && price<=upperRange ){
                    priceRangeSearchList.add(i);
                }
            }
        }
            
        return priceRangeSearchList;
    }

    /**
     * This method will return the index/indices of investments which match the criteria user gives for search command
     * @param symbol String representing the symbol to be searched in the ArrayList for investments (if applicable)
     * @param keyWord String representing the keyword to be searched in the ArrayList for investments (if applicable)
     * @param priceRange String representing the keyword to be searched in the ArrayList for investments (if applicable)
     * @param retVal Integer representing the format for price range
     * if retVal=0, price range contains a whole number
     * if retVal=1, price range has only an upper range
     * if retVal=2, price range has only a lower range
     * if retVal=3, price range has a lower and upper limit
     * @param investments Arraylist representing all the investments
     * @param nameAndIndex HashMap where key,value pair is: (String,ArrayList(Integer))
     * Each keyword is represented by a string while the index/indices of that keyword are represented by
     * the ArrayList of Integers
     * @return ArrayList,which contains the indices of the matched investments
     */

    public static ArrayList<Integer> investmentSearch(String symbol,String keyWord,String priceRange,int retVal,ArrayList<Investment> investments,HashMap<String,ArrayList<Integer>> nameAndIndex){  //This method will return the index/indices of investments which match the criteria user gives for search command
        int symbolFound;
        int i;
        int j;
        int elem1;
        int elem2;
        int count;
        ArrayList<Integer> keyWordSearchList=new ArrayList<Integer>();
        ArrayList<Integer> priceRangeSearchList=new ArrayList<Integer>();
        ArrayList<Integer> investmentsSearch=new ArrayList<Integer>();
        ArrayList<Integer> nonDuplicates=new ArrayList<Integer>();
        if(!symbol.isEmpty() && !keyWord.isEmpty() && !priceRange.isEmpty()){   //If the user did not leave any field blank
            symbolFound=Investment.symbolExists(symbol,investments);
            keyWordSearchList=keyWordSearch(keyWord,keyWordSearchList,nameAndIndex);
            priceRangeSearchList=priceRangeSearch(priceRangeSearchList,priceRange,retVal,investments);

            if(keyWordSearchList.contains(symbolFound) && priceRangeSearchList.contains(symbolFound)){  //If the index of the symbol is present in both the ArrayLists
                investmentsSearch.add(symbolFound);
            }
            
        }
        else if((symbol.isEmpty() && keyWord.isEmpty()) || (keyWord.isEmpty() && priceRange.isEmpty()) || (symbol.isEmpty() && priceRange.isEmpty())){   //If any two fields were left blank
            if(symbol.isEmpty() && keyWord.isEmpty()){                      //If the user entered just the price range
                priceRangeSearchList=priceRangeSearch(priceRangeSearchList,priceRange,retVal,investments);
                investmentsSearch.addAll(priceRangeSearchList);
            }
            else if(keyWord.isEmpty() && priceRange.isEmpty()){             //If the user entered just the symbol
                symbolFound=Investment.symbolExists(symbol,investments);
                if(symbolFound!=-1){                   //That index is only added if it actually exists and is not -1
                    investmentsSearch.add(symbolFound);
                }
            }
            else if(symbol.isEmpty() && priceRange.isEmpty()){              //If the user entered just the keyword
                keyWordSearchList=keyWordSearch(keyWord,keyWordSearchList,nameAndIndex);
                investmentsSearch.addAll(keyWordSearchList);
            }
        }
        else if((!symbol.isEmpty() && !keyWord.isEmpty()) || (!keyWord.isEmpty() && !priceRange.isEmpty()) || (!symbol.isEmpty() && !priceRange.isEmpty())){ //If the user left any one field blank
            if(!symbol.isEmpty() && !keyWord.isEmpty()){            //If the user entered symbol and keyword                 
                symbolFound=Investment.symbolExists(symbol,investments);
                keyWordSearchList=keyWordSearch(keyWord,keyWordSearchList,nameAndIndex);

                if(keyWordSearchList.contains(symbolFound)){        //If keyWordSearchList contains the index position of the symbol
                    investmentsSearch.add(symbolFound);
                } 
            }
            else if(!keyWord.isEmpty() && !priceRange.isEmpty()){    //If the user entered keyword and price range
                keyWordSearchList=keyWordSearch(keyWord,keyWordSearchList,nameAndIndex);
                priceRangeSearchList=priceRangeSearch(priceRangeSearchList,priceRange,retVal,investments);
                keyWordSearchList.addAll(priceRangeSearchList);      //keyWordSearchList now has the indices of investments that match with the user input but this can contain duplicate indices

                for(i=0;i<keyWordSearchList.size();++i){             //Getting unique indices
                    count=0;
                    for(j=0;j<keyWordSearchList.size();++j){
                        elem1=keyWordSearchList.get(i);
                        elem2=keyWordSearchList.get(j);
                        if(elem1==elem2){
                            count=count+1;
                        }

                        if(count==2 && !nonDuplicates.contains(elem1)){    //Adding non-duplicate values to arraylist nonDuplicates
                            nonDuplicates.add(elem1);
                            break;
                        }
                    }
                }
                investmentsSearch.addAll(nonDuplicates);                       //Adding the index/indices of investments which match the criteria
            }
            else if(!symbol.isEmpty() && !priceRange.isEmpty()){         //If the user entered symbol and price range
                symbolFound=Investment.symbolExists(symbol,investments);
                priceRangeSearchList=priceRangeSearch(priceRangeSearchList,priceRange,retVal,investments);

                if(priceRangeSearchList.contains(symbolFound)){         //If priceRangeSearchList contains the index position of the symbol
                    investmentsSearch.add(symbolFound);
                }
            }
        }
        return investmentsSearch;
    }

    /**
     * This method verifies if the price is a valid string consisting of digits and is greater than 0 and
     * throws an exception if one of the criterias is not fulfilled
     * @param strPrice String representing the price of an investment
     * @throws IllegalArgumentException when price does not have digits as characters or
     * if the digit is less than 1
     * //If a string of characters was entered as price
     * //If price does not consist of valid digits greater than 1
     */
    public static void priceException(String strPrice){
        double numPrice;

        try{
            numPrice=Double.parseDouble(strPrice);
            if(numPrice<1){     //If price does not consist of valid digits greater than 1
                throw new IllegalArgumentException("Please enter a positive value for the price field\n");
            }
        }
        catch(NumberFormatException e){  //If a string of characters was entered as price
            throw new IllegalArgumentException("You did not enter valid digits for the price field\n");
        } 
    }

    /**
     * This method verifies if the quantity is a valid string consisting of digits and is greater than 0 and
     * throws an exception if one of the criterias is not fulfilled
     * @param strQuantity String representing the quantity of an investment
     * @throws IllegalArgumentException when quantity does not have digits as characters or
     * if the digit is less than 1
     */
    public static void quantityException(String strQuantity){
        int numQuantity;

        try{
            numQuantity=Integer.parseInt(strQuantity);
            if(numQuantity<1){  //If quantity does not consist of valid digits greater than 1
                throw new IllegalArgumentException("Please enter a positive value for the quantity field\n");
            }
        }
        catch(NumberFormatException e){  //If a string of characters was entered as quantity
            throw new IllegalArgumentException("You did not enter valid digits for the quantity field\n");
        } 
    }
}


