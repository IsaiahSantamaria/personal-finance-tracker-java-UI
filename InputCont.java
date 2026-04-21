import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;




/**
 * This is an interactive conatiner where it prompts
 * users to put in transaction input or not
 * @author Isaiah Santamaria
 * @version 4/19/2026
 */
public class InputCont extends JPanel{
        /** display transaction variables */
        
        /**color Theme Object */
        private static final ColorTheme COLOR_THEME = new ColorTheme();

        /** Buttons components */
        private JButton income_btn;
        private JButton expense_btn;
        private JButton print_csv_btn;
        private JButton submit = new JButton("Submit");


        /**Amount that is used for income or expense */
        private JSpinner amount = new JSpinner(new SpinnerNumberModel(1.0,0.01, Double.MAX_VALUE,1));;

        /** text field for reasoning */
        private JTextField reason = new JTextField(20);

        /** text field for reasoning */
        private JTextField items = new JTextField(20);

        private ButtonGroup group = new ButtonGroup();

         /**temporary account list */
        private String [] accountNames = {"Checking","Savings","Joint"};

        /** String */
        private String [] categoryNames = {"Bills", "Shopping", "Necessities", "Utilities", "Others"};


    public InputCont(){ 
        setOpaque(false);
        setPreferredSize(new Dimension(350, 400));
        //setLayout(new GridBagLayout()); //helps center components automaticallly
    
        /**Styling Buttons */
        income_btn = new JButton("Income"){{
            setPreferredSize(new Dimension(100,50));
            setBackground(COLOR_THEME.QUATERNARY);
            setForeground(COLOR_THEME.PRIMARY);
        }};
        expense_btn = new JButton("Expense"){{
            setPreferredSize(new Dimension(100,50));
            setBackground(COLOR_THEME.QUATERNARY);
            setForeground(COLOR_THEME.PRIMARY);
        }};

        /**adding interactive buttons */
        income_btn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                displayIncome();
                System.out.println("Income btn Clicked!");
            }
        });

        expense_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayExpense();
                System.out.println("expense btn Clicked!");
            }
        });
        
        displayOptions();

        setVisible(true);
    }


    
    /**
     * 
     * makes components rounded corners
     * copied code from Stack Overflow
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Set color BEFORE drawing
        g2.setColor(COLOR_THEME.SECONDARY);
        // fillRoundRect paints the background — drawRoundRect only draws the outline
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
    }
    
    /**
     * displays the income inputs
     */
    private void displayIncome(){
        clearPanel();
        setLayout(new GridLayout(6,1));
        setBorder(BorderFactory.createEmptyBorder(0,20,40,20)); //10 pixels of padding in each side of container

        add(new JLabel("Income Transaction\\"){{
            setFont(new Font("Brush Script MT", Font.PLAIN, 20));
            setForeground(COLOR_THEME.QUINARY);

        }});

        add(new JContainer(){{
            
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Amount: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
            

            
            add(amount);
            

            
        }});
        /****************Radio section that currently has a problem */
        add(new JContainer(){{
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Source: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
            //add(amount);

            add(new JComponent(){{
                setLayout(new BorderLayout());
                for(String str : categoryNames){
                    add(new JRadioButton(str));
                }
            }});
            
            
        }});
        /****************Radio section that currently has a problem */
        add(new JContainer(){{
            
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Items: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
           

            add(items);
            

            
        }});
        add(new JContainer(){{
            
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Reason: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
            
            add(reason);
            

            
        }});

        add(new JContainer(){{
            
            
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayOptions();
                    System.out.println("submit income btn Clicked!");
                }
            });
            
            add(submit);
               
        }});



    }

    /**
     * display expense income
     */
    private void displayExpense(){
        
        clearPanel();
        setLayout(new GridLayout(6,1));
        setBorder(BorderFactory.createEmptyBorder(0,20,40,20)); //10 pixels of padding in each side of container

        add(new JLabel("Expense Transaction\\"){{
            setFont(new Font("Brush Script MT", Font.PLAIN, 20));
            setForeground(COLOR_THEME.QUINARY);

        }});

        add(new JContainer(){{
            
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Amount: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
            

            
            add(amount);
            

            
        }});
        /****************Radio section that currently has a problem */
        add(new JContainer(){{
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Category: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
            //add(amount);

            add(new JComponent(){{
                setLayout(new BorderLayout());
                for(String str : categoryNames){
                    add(new JRadioButton(str));
                }
            }});
            
            
        }});
        /****************Radio section that currently has a problem */
        add(new JContainer(){{
            
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Items: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
           

            add(items);
            

            
        }});
        add(new JContainer(){{
            
            amount.setPreferredSize(new Dimension(100,25));
            add(new JLabel("Reason: "){{
                setFont(new Font("Serif", Font.BOLD, 16));
                setForeground(COLOR_THEME.QUINARY);
            }});
            
            add(reason);
            

            
        }});

        add(new JContainer(){{
            
            
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayOptions();
                    System.out.println("submit expense btn Clicked!");
                }
            });
            
            add(submit);
               
        }});
 
    }

    /**
     * clears main panel
     */
    private void clearPanel(){
        removeAll(); //clears all child component
        revalidate(); //tells the layout manager to re-layout
        repaint(); //repaint the panel
    }

    /**
     * display options inputs
     */
    private void displayOptions(){
        clearPanel();
        setLayout(new GridBagLayout());

        add(new JContainer(){{
            add(income_btn);
            add(expense_btn);  
        }});
        

    }


    

}
