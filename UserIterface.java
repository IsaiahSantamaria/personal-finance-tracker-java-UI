
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
/**
 * 
 * 
 * User interface for the java finance tracker
 * @author Isaiah Santamaria 
 * @version 3/31/2026
 */


public class UserIterface implements Runnable, ActionListener{


    @Override 
    public void run(){
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Personal Finance Tracker");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

    }

    

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new UserIterface());

        
    }
    

}