import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class RecentTransaction extends JFrame{

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton print_csv_btn;

    public RecentTransaction(){
        setTitle("Personal Finance Tacker by Software security");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //setting up JTable
        String[][] data = { {"John", "25"}, {"Jane", "30"} };
        String[] columns = {"Transaction Type", "Amount"};

        tableModel = new DefaultTableModel(columns, 0);
        for(String [] row: data){
          tableModel.addRow(row);

        }
        table = new JTable(tableModel);
 
        // setting up JButton
        
        print_csv_btn = new JButton("Print CSV");
        JPanel btn_container = new JPanel();
        btn_container.setSize(100,100);

        print_csv_btn.setSize(new Dimension(100,50));
        btn_container.add(print_csv_btn);
       // print_csv_btn.setPreferredSize(new Dimension(100,50)); // Width
        
       // adding all JPanels
       add(new JScrollPane(table));

       add(new JPanel(new BorderLayout()){{

        add(print_csv_btn, "East");

       }}, "South");

        setVisible(true);
         
    }
    
    public static void main(String[] args) {
        new RecentTransaction();
        
    }
}
