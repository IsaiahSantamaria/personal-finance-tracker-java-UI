
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * 
 * 
 * User interface for the java finance tracker
 * @author Isaiah Santamaria 
 * @version 3/31/2026
 */


public class UserInterface implements Runnable, ActionListener{

    private static final int PADDING = 20;

    /** Accounts Display Container */
    private JPanel accountsCont;

    /** Transaction Input Container */
    private JPanel transInputCont; 

    /** Recent Transaction Container */
    private JPanel recentTransCont;

    /**text display content for accounts */
    private JLabel displayAccounts;

    /** Print transaction history into CSV file format */
    private JButton printCSVButton;

    /**Income Transaction Button*/
    private JButton incomeButton;

    /**Expense Transaction Button */
    private JButton expenseButton;

    /**Amount that is used for income or expense */
    private JSpinner amount;

    /** text field for reasoning */
    private JTextField reason;

    /**temporary account list */
    private String [] accountNames = {"Checking","Savings","Joint"};

    private String [] categoryNames = {"Bills", "Shopping", "Necessities", "Utilities", "Others"};

    private ButtonGroup group;

    private JButton submit = new JButton("Submit");

    

    public UserInterface(){
        accountsCont = new JPanel();
        transInputCont = new JPanel();
        recentTransCont= new JPanel();
        displayAccounts = new JLabel("$Total");
        displayAccounts.setFont(new Font("Arial", Font.BOLD, 30));
        printCSVButton = new JButton("Print");
        expenseButton = new JButton("Expense");
        incomeButton = new JButton("Income");
        reason = new JTextField(20);

        group = new ButtonGroup();

        submit.addActionListener(this);
        expenseButton.addActionListener(this);
        incomeButton.addActionListener(this);
    }

    @Override 
    public void run(){
        
        JFrame frame = new JFrame("Personal Finance Tracker");
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setPreferredSize(new Dimension(750,750));

        //setting up panels ands inter borderlayout
        JPanel framePanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        framePanel.setLayout(new BorderLayout());

        //adding top and bottom panel to frame
        
        framePanel.add(topPanel, BorderLayout.NORTH);
        framePanel.add(bottomPanel, BorderLayout.SOUTH);

        /** setting up accounts display options(TOP LEFT CORNER*/
        accountsCont.setLayout(new BorderLayout());
        accountsCont.setPreferredSize(new Dimension(370,370));
        displayAccounts.setHorizontalAlignment(SwingConstants.CENTER);
        accountsCont.add(displayAccounts, BorderLayout.CENTER);
        accountsCont.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        /** Setting input panel
         * 
        */
        
        transInputCont.setPreferredSize(new Dimension(370,370));
        
        
        resetInputTransCont();



        /** Setting up bottom transaction section */
        recentTransCont = new JPanel(new GridLayout(10,0));
        for(int i = 0; i < 9; i ++){
            JLabel tmpTrans = new JLabel("Transaction type: ?, Amount: ?, Reason:  ?");
            tmpTrans.setFont(new Font("Arial", Font.BOLD, 25));
            recentTransCont.add(tmpTrans);

        }
        recentTransCont.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        recentTransCont.add(printCSVButton);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.RED));


        // having top panel add totalAccount panel and input transaction 
        // by using borderlayout to organize them both)
        topPanel.setLayout(new BorderLayout());
        
        topPanel.add(accountsCont, BorderLayout.WEST);
        topPanel.add(transInputCont, BorderLayout.EAST);

        
        bottomPanel.add(recentTransCont);

       
        //ading framepanel to frame and setting window visable
        frame.add(framePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //if instance a JButton
        if(e.getSource() instanceof JButton ){
            JButton buttonPressed = (JButton) e.getSource();
            String buttonPressedText = buttonPressed.getText();
            if(buttonPressedText.equals("Income")){ // if incomeButton is pressed
                //call out repaint income function
                incomePressed();
            }else if(buttonPressedText.equals("Expense")){ // if expenseButton is pressed
                //call out repaint expense function
                expensePressed();
            }else if(buttonPressedText.equals("Submit")){
                resetInputTransCont();
                
            }

        }

    }
    /**
     * when JButton income is pressed,
     * it changes the transInputCont panel 
     * for income inputs
     */
    private void incomePressed(){
        clearTransInputCont(); //clears panel
        amount = new JSpinner(new SpinnerNumberModel(1.0,0.01, Double.MAX_VALUE,1));
        JPanel container = new JPanel(new GridLayout(3,3));

        container.add(new JLabel("Amount: "));
        container.add(amount);
        container.add(new JLabel("Source: "));
        container.add(reason);
        container.add(new JLabel("Destination: "));

        JPanel tmp = new JPanel();

        //JPanel tmp = new JPanel(new GridLayout(1,accountNames.length));
        for(int i =0; i < accountNames.length;i ++){
            JRadioButton account = new JRadioButton(accountNames[i]);
            group.add(account);
            tmp.add(account);
        }

        container.add(tmp);

        //setting up submit button
        JPanel option = new JPanel(new FlowLayout(FlowLayout.CENTER));
        option.add(submit, BorderLayout.SOUTH);
        //GridBagLayout is more dynamic/flexible than GridLayout
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(option,new GridBagConstraints());
        transInputCont.add(centerWrapper ,BorderLayout.SOUTH);
        transInputCont.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        transInputCont.add(container, BorderLayout.NORTH);
    
        
    }

    /**
     * 
     */
    private void expensePressed(){
        clearTransInputCont(); //clears panel
        amount = new JSpinner(new SpinnerNumberModel(1.0,0.01, Double.MAX_VALUE,1));
        JPanel container = new JPanel(new GridLayout(3,3));

        container.add(new JLabel("Amount: "));
        container.add(amount);

        container.add(new JLabel("Category: "));

        JPanel tmp = new JPanel();

        //JPanel tmp = new JPanel(new GridLayout(1,accountNames.length));
        for(int i =0; i < categoryNames.length;i ++){
            JRadioButton categories = new JRadioButton(categoryNames[i]);
            group.add(categories);
            tmp.add(categories);
        }
        container.add(tmp);

        container.add(new JLabel("Further Specification: "));
        container.add(reason);
        

        //setting up submit button
        JPanel option = new JPanel(new FlowLayout(FlowLayout.CENTER));
        option.add(submit, BorderLayout.SOUTH);
        //GridBagLayout is more dynamic/flexible than GridLayout
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(option,new GridBagConstraints());
        transInputCont.add(centerWrapper ,BorderLayout.SOUTH);
        transInputCont.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        transInputCont.add(container, BorderLayout.NORTH);

    }

    private void resetInputTransCont(){
        clearTransInputCont(); // clear panel
        
        transInputCont.setLayout(new BorderLayout());
        expenseButton.setPreferredSize(new Dimension(100,50));
        incomeButton.setPreferredSize(new Dimension(100,50));
        JPanel option = new JPanel(new FlowLayout(FlowLayout.CENTER));
        option.add(expenseButton, BorderLayout.WEST);
        option.add(incomeButton, BorderLayout.EAST );
        //GridBagLayout is more dynamic/flexible than GridLayout
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(option,new GridBagConstraints());
        transInputCont.add(centerWrapper ,BorderLayout.CENTER);
        transInputCont.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        transInputCont.repaint();
    }

   

    /**
     * clears transInputCont Panel
     */
    private void clearTransInputCont(){
        transInputCont.removeAll(); //clears all child component
        transInputCont.revalidate(); //tells the layout manager to re-layout
        transInputCont.repaint(); //repaint the panel
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new UserInterface());

    }
}