import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

    public InputCont(){ 
        setLayout(new BorderLayout());

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

    }

    /**
     * display expense income
     */
    private void displayExpense(){

    }

    /**
     * display options inputs
     */
    private void displayOptions(){
        add(new JPanel(){{
            setLayout(new BorderLayout());
            

        }});
        

    }

    

}
