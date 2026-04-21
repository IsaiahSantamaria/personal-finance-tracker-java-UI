
import java.awt.BorderLayout;
import javax.swing.*;

/**
 * main container of root
 * @author Isaiah Santamaria
 * @version 4/16/2025
 */
public class Root extends JPanel {
    public RecentTransaction recentTransaction;
    public Banner BANNER;
    public InputCont inputCont;
    public AccountsCont accountsCont;
    public JContainer contentCont;

    public static final ColorTheme COLOR_THEME = new ColorTheme();
    public Root(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40)); // makes Root have auto padding
        setBackground(COLOR_THEME.PRIMARY);
        //setSize(700,500);
        recentTransaction = new RecentTransaction();
        BANNER = new Banner();
        inputCont = new InputCont();
        accountsCont = new AccountsCont();
        setBackground(COLOR_THEME.PRIMARY);


        JContainer contentCont = new JContainer(); //in charge of hosting rest of content
     
        contentCont.setLayout(new BorderLayout());
        contentCont.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //giving padding
        contentCont.add(inputCont,"West");
        contentCont.add(recentTransaction, "East");




        /** adding components to main JPanel */
        add(new JContainer(){{
            
            setLayout(new BorderLayout());
            add(BANNER, "North");
            
            add(accountsCont, "South");
        }}, BorderLayout.NORTH);

        
        add(contentCont, BorderLayout.CENTER); // stretches to fill remaining space

        
        
        
        
        
        setVisible(true);
        
    }

    public static void main(String []args){
        new Root();
    }

    
}
