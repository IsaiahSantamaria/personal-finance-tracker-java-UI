import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.*; // Imports BoxLayout, JPanel, and Box
import java.awt.*;    // Imports Component and Container


public class AccountsCont extends JPanel{

    public double checkingNum;
    public double savingNum;
    public double jointNum;
    public ArrayList<Profile> profiles = new ArrayList<Profile>();
    private Color [] profileColors = {Color.GREEN, Color.RED, Color.CYAN};

    private static final ColorTheme COLOR_THEME = new ColorTheme();
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton print_csv_btn;

    public AccountsCont(){
        setOpaque(false);

        setLayout(new GridLayout(1,3, 30, 10));
        //setting up profiles
        profiles.add(new Profile(profileColors[0], "Checking", 10000));
        profiles.add(new Profile(profileColors[1], "Savings", 10000));
        profiles.add(new Profile(profileColors[2], "Joint", 10000));
        
        //setLayout(new BorderLayout());
        setForeground(COLOR_THEME.QUATERNARY);
        setBackground(COLOR_THEME.PRIMARY);
        for(Profile profile : profiles){
            profile.setOpaque(false);
            //profile.setAlignmentX(Component.CENTER_ALIGNMENT); // centers each profile
            add(profile);
            //add(Box.createVerticalStrut(10)); // adds spacing between profiles
        }

        
        setVisible(true);
        
    }

    @Override
    protected void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      Graphics2D var2 = (Graphics2D)var1;
      var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var2.setColor(COLOR_THEME.PRIMARY);
      var2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 50, 50);
   }

   class Profile extends JPanel{
        private Color theme;

        private JLabel amountLabel;

        private JLabel nameLabel;

        public Profile(Color theme, String name, double amount){
            setOpaque(false);

            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(150,115));
            setBorder(BorderFactory.createEmptyBorder(15,15,5,5)); //10 pixels of padding in each side of container

            //intializing JLabel names
            nameLabel = new JLabel(name);
            amountLabel = new JLabel("$" + amount);
            JLabel account = new JLabel("Account");

            //styling label
            nameLabel.setFont(new Font("Serif", Font.BOLD, 15));
            nameLabel.setForeground(new ColorTheme().QUINARY);
            amountLabel.setFont(new Font("Serif", Font.BOLD, 22));
            amountLabel.setForeground(new ColorTheme().QUINARY);
            account.setFont(new Font("Serif", Font.BOLD, 12));
            account.setForeground(new ColorTheme().QUINARY);
            
            

            add(nameLabel,"North");
            //setting bottom half with number total and small label of account
            add(new JPanel(){{
                setOpaque(false);
                setLayout(new BorderLayout());
                add(new JPanel(){{
                    setOpaque(false);
                    setLayout(new GridLayout(2,0,2,2));
                    add(amountLabel);
                    add(account);
                }},"West");
            }},"South");
            
            this.theme = theme;
        }
        @Override
        protected void paintComponent(Graphics var1) {
            super.paintComponent(var1);
            Graphics2D var2 = (Graphics2D)var1;
            var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            var2.setColor(new ColorTheme().TERTIARY);
            var2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
        }
   }


   
}
