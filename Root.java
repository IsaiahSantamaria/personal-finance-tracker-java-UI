
import javax.swing.*;

/**
 * main container of root
 * @author Isaiah Santamaria
 * @version 4/16/2025
 */
public class Root extends JPanel {
    public RecentTransaction rTCont;
    public InputCont inputCont;
    public AccountsCont AcntCont;
    public ContentCont content;
    public Root(){
        

        //add(new AccountsCont()); //first container(List all of the accounts name)
        //add(new InputCont()); // input options container
        add(new RecentTransaction()); //displays recent Transaction
        
        setVisible(true);
        
    }


    
}
