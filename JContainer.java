import java.awt.BorderLayout;
import javax.swing.*;
/**
 * This panels purpose is used to shape and position other components
 * The reason why this is created instead of just creating regular JPanel
 * is that to force JPanels to be the ColorTheme Primary color
 * it is not meant to have any other purposes
 */
public class JContainer extends JPanel{

    
    public JContainer(){

        super.setOpaque(false);
    }

    public JContainer(BorderLayout e){
        super(e);
        super.setOpaque(false);
    }
    
}
