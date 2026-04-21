import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;



/**
 * @author Isaiah Santamaria
 */
public class Banner extends JPanel{
    private JLabel title = new JLabel("Personal Fiance Tracker"); 
    private JLabel creds = new JLabel("Software Security");
    //private JLabel displayIcon;
    private ImageIcon icon = new ImageIcon("icon.png");

    private JLabel logoCont;
    

    public Banner(){
        setLayout(new BorderLayout());
        setBackground(new ColorTheme().SECONDARY);
        //setPreferredSize(new Dimension(100,getHeight()));
       

        /************** Styling and intilizing components ***********/

        //images
        Image scaledImage = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH); //scalling down image size
        logoCont = new JLabel(new ImageIcon(scaledImage));
        logoCont.setPreferredSize(new Dimension(100,75));

        //title 
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new ColorTheme().QUINARY);
        creds.setFont(new Font("Serif", Font.BOLD, 13));
        creds.setForeground(new ColorTheme().QUATERNARY);




        /** adding components to main Container */
        add(new JPanel(){{
            setOpaque(false);
            setLayout(new BorderLayout());
            add(title, "North");
            add(creds, "South");
        }}, "West");

        add(logoCont, "East");


        setVisible(true);
    }

    
}
