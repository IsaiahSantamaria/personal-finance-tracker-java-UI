
import javax.swing.*;

/**
 * main container of root
 * @author Isaiah Santamaria
 * @version 4/16/2025
 */
public class Root extends JFrame {
    public RecentTransaction rTCont;
    public InputCont inputCont;
    public AccountsCont AcntCont;
    public ContentCont content;
    public Root(){
        setTitle("Personal Finance Tacker by Software security");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new RecentTransaction());
        
        setVisible(true);
        
    }

    public static void main(String []args){
        new Root();
    }

    



    
}
