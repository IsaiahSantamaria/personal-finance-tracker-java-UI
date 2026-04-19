import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
   
/**
 * @author Isaiah Santamaria
 */
public class InputCont extends JPanel{
        /** display transaction variables */
        private JTable table;
        private DefaultTableModel tableModel;
        private JButton print_csv_btn;

        /**color Theme Object */
        private static final ColorTheme COLOR_THEME = new ColorTheme();

        //


    public InputCont(){ 
        setLayout(new BorderLayout());


        


        setVisible(true);
    }

    public void displayIncome(){

    }

    public void displayExpense(){

    }

}
