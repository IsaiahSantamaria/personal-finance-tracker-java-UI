import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class RecentTransaction extends JPanel{

    private ColorTheme color_theme = new ColorTheme();
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton print_csv_btn;

    public RecentTransaction(){
       
        

        //setting up JTable and its test data cases for table
        String[][] data = { {"1000", "Income", "Bill", "Income", "1/1/1"}, {"Jane", "30"} };
        String[] columns = {"Amount", "Type", "Category", "Account", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
         table = new JTable(tableModel){
            
 
        
 
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
       setBackground(color_theme.SECONDARY);
       table.setBackground(color_theme.SECONDARY);
    setVisible(true);
         
    }
    
    public static void main(String[] args) {
        //new RecentTransaction();
        
    }
//}
