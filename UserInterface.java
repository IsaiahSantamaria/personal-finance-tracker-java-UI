
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

    public UserInterface(){
        accountsCont = new JPanel();
        transInputCont = new JPanel();
        recentTransCont= new JPanel();
        displayAccounts = new JLabel("$Total");
        displayAccounts.setFont(new Font("Arial", Font.BOLD, 30));
        printCSVButton = new JButton("Print");
        expenseButton = new JButton("Expense");
        incomeButton = new JButton("Income");

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

        /** Setting input panel
         * 
        */
        
        transInputCont.setPreferredSize(new Dimension(370,370));
        
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



        /** Setting up bottom transaction section */
        recentTransCont = new JPanel(new GridLayout(6,0));
        for(int i = 0; i < 6; i ++){
            
            recentTransCont.add(new JLabel("Transaction type: ?, Amount: ?, Reason: ?"));
        }

        
        


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

    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new UserInterface());

        
    }
}