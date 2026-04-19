
import javax.swing.*;

/**
 * main container of root
 * @author Isaiah Santamaria
 * @version 4/16/2025
 */
public class UserInterfaceTwo extends JFrame {
    public RecentTransaction rTCont;
    public InputCont inputCont;
    public AccountsCont AcntCont;
    public ContentCont content;

    public UserInterfaceTwo(){
        setTitle("Personal Finance Tacker by Software security");
        setSize(1500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new ColorTheme().PRIMARY);

        add(new Root());
        
        setVisible(true);
    }

    public static void main(String []args){
        new UserInterfaceTwo();
    }

    



    
}
