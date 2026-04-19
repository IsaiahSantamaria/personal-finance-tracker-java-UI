
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
    public static final ColorTheme COLOR_THEME = new ColorTheme();
    public Root(){

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // makes Root have auto padding
        setBackground(COLOR_THEME.PRIMARY);



        
        setSize(1500,500);

     
        add(new RecentTransaction());
        add(new RecentTransaction());
        add(new RecentTransaction());
        
        
        
        
        setVisible(true);
        
    }

    public static void main(String []args){
        new Root();
    }

    



    
}
