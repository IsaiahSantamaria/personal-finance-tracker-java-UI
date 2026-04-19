import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



public class RecentTransaction extends JPanel{

    private static final ColorTheme COLOR_THEME = new ColorTheme();
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton print_csv_btn;
    //setting up JTable and its test data cases for table
    private String[][] data = { {"+1000", "Income", "Bill", "Income", "1/1/1"}, {"Jane", "30"} };
    private String[] columns = {"Amount", "Type", "Category", "Account", "Date"};
    private String [] dataTwo =  {"-1000", "Expense", "Bill", "Expense", "1/1/1"};

    public RecentTransaction(){
        setOpaque(false); //ESSENTIAL for making container have rounded corners
        setLayout(new BorderLayout()); //helps controls layout of components
        setPreferredSize(new Dimension(350, 400));//sets size of whole container
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); //10 pixels of padding in each side of container

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setSize(new Dimension(250, 250));
        table.setForeground(COLOR_THEME.QUATERNARY); //setting all foregrounds colors
       
       
        //adding data to make table visible and testing purposes
        for(int  i = 0; i < 10; i ++){
            tableModel.addRow(data[0]);
        }
        //adding one expense for testing
        tableModel.addRow(dataTwo);
        
 
        // setting up JButton and its container
        print_csv_btn = new JButton("Print CSV");
        JContainer btn_container = new JContainer();
        btn_container.setSize(100,100);
        print_csv_btn.setSize(new Dimension(100,50));
        btn_container.add(print_csv_btn);


        /******* Desiging header of the table ************/
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 13));
        header.setForeground(COLOR_THEME.QUINARY); //controls header row table text color
        header.setBackground(COLOR_THEME.PRIMARY); 

        /********** desiging table *****************/
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String txt = val == null ? "" : val.toString();
                setFont(new Font("Consolas", Font.BOLD, 12));
                setForeground(txt.startsWith("+") ? COLOR_THEME.INCOME :
                              txt.startsWith("-") ? COLOR_THEME.EXPENSE   : COLOR_THEME.QUINARY);
                setBackground(sel ? COLOR_THEME.PRIMARY : (row % 2 == 0 ? COLOR_THEME.SECONDARY : COLOR_THEME.PRIMARY));
                setOpaque(true);
                setHorizontalAlignment(SwingConstants.RIGHT);
                setBorder(new EmptyBorder(0, 0, 0, 12));
                return c;
            }
        });
        table.setBackground(COLOR_THEME.SECONDARY);


        /********* designing button section *******/
        print_csv_btn.setBackground(COLOR_THEME.QUINARY);
        print_csv_btn.setForeground(COLOR_THEME.PRIMARY);
    
        /************ adding action Listener  **************/
        print_csv_btn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("CSV Button is Pressed");
            }
        });

        //********** adding components to main JPanel ************/

        add(new JLabel("Recent Transaction History\\"){{
            setFont(new Font("Brush Script MT", Font.PLAIN, 20));
            setForeground(COLOR_THEME.QUINARY);
            setBorder(BorderFactory.createEmptyBorder(4,0,10,0)); //10 pixels of padding in each side of container
        }}, "North");

        // adding table (JSCrollPane assisst with handling table and also makes header visible)_
        add(new JScrollPane(table){{
            getViewport().setBackground(COLOR_THEME.SECONDARY); //setting background to dark theme color since just setting background color is not working
            setBorder(BorderFactory.createLineBorder(COLOR_THEME.SECONDARY)); //prevents white border lines to be visible
        }});

        //print cs
        add(new JContainer(new BorderLayout()){{
            add(print_csv_btn, "East");
        }}, "South");

        setVisible(true);
    }

    //makes components rounded corners
    // copied code from Stack Overflow
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

    
}
