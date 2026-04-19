import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



public class AccountsCont extends JPanel{

    private static final ColorTheme COLOR_THEME = new ColorTheme();
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton print_csv_btn;

    public AccountsCont(){
       setLayout(new BorderLayout());
        setForeground(COLOR_THEME.QUATERNARY);
        setBackground(COLOR_THEME.PRIMARY);

        //setting up JTable and its test data cases for table
        String[][] data = { {"+1000", "Income", "Bill", "Income", "1/1/1"}, {"Jane", "30"} };
        String[] columns = {"Amount", "Type", "Category", "Account", "Date"};

        String [] dataTwo =  {"-1000", "Expense", "Bill", "Expense", "1/1/1"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        table.setForeground(COLOR_THEME.QUATERNARY); //setting rest of row text color
            
 
        for(int  i = 0; i < 10; i ++){
            tableModel.addRow(data[0]);
        }

        //adding one expense for testing
        tableModel.addRow(dataTwo);
        
 
        // setting up JButton and its container
        print_csv_btn = new JButton("Print CSV");
        JPanel btn_container = new JPanel();
        btn_container.setSize(100,100);
        print_csv_btn.setSize(new Dimension(100,50));
        btn_container.add(print_csv_btn);
      
        
        // adding all JPanels
        add(new JScrollPane(table));
        add(new JPanel(new BorderLayout()){{
            add(print_csv_btn, "East");
        }}, "South");


       //initilzing colors of JPanel();
       setBackground(COLOR_THEME.SECONDARY);
       table.setBackground(COLOR_THEME.SECONDARY);

        /*setting up fonts and sizes */
        // table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 18));
        header.setForeground(COLOR_THEME.QUINARY); //controls header row table text color
        header.setBackground(COLOR_THEME.PRIMARY); 

        //setting color theme for table
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String txt = val == null ? "" : val.toString();
                setFont(new Font("Consolas", Font.BOLD, 12));
                setForeground(txt.startsWith("+") ? COLOR_THEME.INCOME :
                              txt.startsWith("-") ? COLOR_THEME.EXPENSE   : COLOR_THEME.PRIMARY);
                setBackground(sel ? COLOR_THEME.PRIMARY : (row % 2 == 0 ? COLOR_THEME.SECONDARY : COLOR_THEME.PRIMARY));
                setOpaque(true);
                setHorizontalAlignment(SwingConstants.RIGHT);
                setBorder(new EmptyBorder(0, 0, 0, 12));
                return c;
            }
        });

        //designing button
        print_csv_btn.setBackground(COLOR_THEME.QUINARY);
        print_csv_btn.setForeground(COLOR_THEME.PRIMARY);
    


        //using anomous class to give button actionListener 
        print_csv_btn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("CSV Button is Pressed");
            }
        });





        setVisible(true);
        
         
    
    }
   
}
