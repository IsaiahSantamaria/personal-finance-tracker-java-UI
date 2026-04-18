import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Component; // Imports the base Component class
import java.awt.Button;    // Imports the Button class



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
        table = new JTable(tableModel);
            
 
        for(int  i = 0; i < 10; i ++){
            tableModel.addRow(data[0]);
        }
 
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


        /*setting up fonts and sizes */
        // table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Verdana", Font.BOLD, 14));
        header.setForeground(color_theme.QUINARY);
        header.setBackground(color_theme.PRIMARY);

        //rows color
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String txt = val == null ? "" : val.toString();
                setFont(new Font("Consolas", Font.BOLD, 12));
                setForeground(txt.startsWith("+") ? ACCENT_GREEN :
                              txt.startsWith("-") ? ACCENT_RED   : TEXT_PRIMARY);
                setBackground(sel ? HOVER_COLOR : (row % 2 == 0 ? BG_CARD : BG_DARK));
                setOpaque(true);
                setHorizontalAlignment(SwingConstants.RIGHT);
                setBorder(new EmptyBorder(0, 0, 0, 12));
                return c;
            }
        });




        setVisible(true);
         
    
    
    }
}
