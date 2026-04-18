import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * Personal Finance Tracker Dashboard
 * Pure UI — no backend logic. Wire up your own backend via the public methods
 * and the TransactionListener / ExportListener interfaces.
 */
public class FinanceDashboard extends JFrame {

    // ── Color Palette ──────────────────────────────────────────────────────────
    private static final Color BG_DARK       = new Color(13,  17,  23);
    private static final Color BG_CARD       = new Color(22,  27,  34);
    private static final Color BG_INPUT      = new Color(33,  38,  45);
    private static final Color ACCENT_GREEN  = new Color(63, 185, 80);
    private static final Color ACCENT_RED    = new Color(248, 81, 73);
    private static final Color ACCENT_BLUE   = new Color(88, 166, 255);
    private static final Color ACCENT_GOLD   = new Color(255, 196, 0);
    private static final Color TEXT_PRIMARY  = new Color(230, 237, 243);
    private static final Color TEXT_MUTED    = new Color(139, 148, 158);
    private static final Color BORDER_COLOR  = new Color(48,  54,  61);
    private static final Color HOVER_COLOR   = new Color(33,  38,  45);

    // ── Fonts ──────────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_HEADING   = new Font("Segoe UI", Font.BOLD,  14);
    private static final Font FONT_LABEL     = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_MONO      = new Font("Consolas", Font.BOLD,  18);
    private static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BTN       = new Font("Segoe UI", Font.BOLD,  12);

    // ── Account Balance Labels (for external updates) ──────────────────────────
    private JLabel checkingBalanceLabel;
    private JLabel savingsBalanceLabel;
    private JLabel jointsBalanceLabel;
    private JLabel totalBalanceLabel;

    // ── Transaction Form Fields ────────────────────────────────────────────────
    private JComboBox<String> typeCombo;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> accountCombo;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTextField dateField;
    private JButton submitTransactionBtn;

    // ── Recent Transactions Table ──────────────────────────────────────────────
    private DefaultTableModel tableModel;
    private JTable transactionTable;
    private JButton exportCsvBtn;

    // ── Listener Interfaces ────────────────────────────────────────────────────
    public interface TransactionListener {
        void onTransactionSubmitted(String type, String category, String account,
                                    String amount, String description, String date);
    }
    public interface ExportListener {
        void onExportRequested();
    }

    private TransactionListener transactionListener;
    private ExportListener exportListener;

    // ══════════════════════════════════════════════════════════════════════════
    //  Constructor
    // ══════════════════════════════════════════════════════════════════════════
    public FinanceDashboard() {
        setTitle("Finance Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 820);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DARK);

        root.add(buildTopBar(),     BorderLayout.NORTH);
        root.add(buildMainContent(), BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Top Bar
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_CARD);
        bar.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_COLOR),
            new EmptyBorder(14, 24, 14, 24)
        ));

        // Left: logo + title
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel logo = new JLabel("◈");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(ACCENT_GOLD);

        JLabel title = new JLabel("Personal Finance Tracker");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_PRIMARY);

        left.add(logo);
        left.add(title);

        // Right: date/time chip
        JLabel dateLbl = new JLabel(new SimpleDateFormat("EEEE, MMM d, yyyy").format(new Date()));
        dateLbl.setFont(FONT_SMALL);
        dateLbl.setForeground(TEXT_MUTED);
        dateLbl.setBorder(new EmptyBorder(0, 0, 0, 4));

        bar.add(left, BorderLayout.WEST);
        bar.add(dateLbl, BorderLayout.EAST);
        return bar;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Main Content — three columns
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildMainContent() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(BG_DARK);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill    = GridBagConstraints.BOTH;
        gc.insets  = new Insets(0, 8, 0, 8);
        gc.gridy   = 0;
        gc.weighty = 1.0;

        // Column 1 – Accounts (weight 0.28)
        gc.gridx   = 0;
        gc.weightx = 0.28;
        content.add(buildAccountsPanel(), gc);

        // Column 2 – Transaction Entry (weight 0.32)
        gc.gridx   = 1;
        gc.weightx = 0.32;
        content.add(buildTransactionPanel(), gc);

        // Column 3 – Recent Transactions (weight 0.40)
        gc.gridx   = 2;
        gc.weightx = 0.40;
        content.add(buildRecentPanel(), gc);

        return content;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL 1 – Account Balances
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildAccountsPanel() {
        JPanel card = createCard("Account Balances");

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);

        // Individual accounts
        checkingBalanceLabel = new JLabel("$0.00");
        savingsBalanceLabel  = new JLabel("$0.00");
        jointsBalanceLabel   = new JLabel("$0.00");

        body.add(buildAccountRow("Checking", "🏦", ACCENT_BLUE,  checkingBalanceLabel));
        body.add(Box.createVerticalStrut(10));
        body.add(buildAccountRow("Savings",  "💰", ACCENT_GREEN, savingsBalanceLabel));
        body.add(Box.createVerticalStrut(10));
        body.add(buildAccountRow("Joints",   "🤝", ACCENT_GOLD,  jointsBalanceLabel));

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        sep.setBackground(BORDER_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        body.add(Box.createVerticalStrut(16));
        body.add(sep);
        body.add(Box.createVerticalStrut(16));

        // Total
        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        totalRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel totalLbl = new JLabel("NET WORTH");
        totalLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        totalLbl.setForeground(TEXT_MUTED);

        totalBalanceLabel = new JLabel("$0.00");
        totalBalanceLabel.setFont(new Font("Consolas", Font.BOLD, 26));
        totalBalanceLabel.setForeground(ACCENT_GREEN);

        totalRow.add(totalLbl,         BorderLayout.NORTH);
        totalRow.add(totalBalanceLabel, BorderLayout.CENTER);
        body.add(totalRow);

        // Spacer
        body.add(Box.createVerticalGlue());

        // Quick-stat strip
        body.add(buildStatStrip());

        ((JPanel) card.getComponent(1)).add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildAccountRow(String name, String icon, Color accent, JLabel balLabel) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        row.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        row.setBackground(BG_INPUT);
        row.setOpaque(true);

        // Icon blob
        JLabel iconLbl = new JLabel(icon) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(accent.darker().darker());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);
        iconLbl.setPreferredSize(new Dimension(40, 40));

        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(FONT_HEADING);
        nameLbl.setForeground(TEXT_PRIMARY);

        JLabel typeLbl = new JLabel("Account");
        typeLbl.setFont(FONT_SMALL);
        typeLbl.setForeground(TEXT_MUTED);

        JPanel nameCol = new JPanel();
        nameCol.setLayout(new BoxLayout(nameCol, BoxLayout.Y_AXIS));
        nameCol.setOpaque(false);
        nameCol.add(nameLbl);
        nameCol.add(typeLbl);

        balLabel.setFont(FONT_MONO);
        balLabel.setForeground(accent);
        balLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(iconLbl,  BorderLayout.WEST);
        row.add(nameCol,  BorderLayout.CENTER);
        row.add(balLabel, BorderLayout.EAST);
        return row;
    }

    private JPanel buildStatStrip() {
        JPanel strip = new JPanel(new GridLayout(1, 2, 8, 0));
        strip.setOpaque(false);
        strip.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));

        strip.add(buildMiniStat("Monthly In",  "+$0.00", ACCENT_GREEN));
        strip.add(buildMiniStat("Monthly Out", "-$0.00", ACCENT_RED));
        return strip;
    }

    private JPanel buildMiniStat(String label, String value, Color color) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_INPUT);
        p.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Consolas", Font.BOLD, 14));
        val.setForeground(color);
        val.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(val);
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL 2 – Add Transaction
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildTransactionPanel() {
        JPanel card = createCard("Add Transaction");

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);

        // Type toggle (Income / Expense)
        JPanel typeToggle = buildTypeToggle();
        body.add(typeToggle);
        body.add(Box.createVerticalStrut(16));

        // Amount
        body.add(buildFieldLabel("Amount ($)"));
        body.add(Box.createVerticalStrut(4));
        amountField = styledTextField("0.00");
        amountField.setFont(new Font("Consolas", Font.BOLD, 16));
        body.add(amountField);
        body.add(Box.createVerticalStrut(12));

        // Description
        body.add(buildFieldLabel("Description"));
        body.add(Box.createVerticalStrut(4));
        descriptionField = styledTextField("e.g. Grocery shopping");
        body.add(descriptionField);
        body.add(Box.createVerticalStrut(12));

        // Category
        body.add(buildFieldLabel("Category"));
        body.add(Box.createVerticalStrut(4));
        String[] incomeCategories = {
            "Salary", "Freelance", "Investment", "Gift", "Refund", "Other Income"
        };
        String[] expenseCategories = {
            "Housing", "Food & Dining", "Transport", "Utilities",
            "Healthcare", "Entertainment", "Shopping", "Education", "Other"
        };
        categoryCombo = styledCombo(expenseCategories);
        body.add(categoryCombo);
        body.add(Box.createVerticalStrut(12));

        // Account
        body.add(buildFieldLabel("Account"));
        body.add(Box.createVerticalStrut(4));
        accountCombo = styledCombo(new String[]{"Checking", "Savings", "Joints"});
        body.add(accountCombo);
        body.add(Box.createVerticalStrut(12));

        // Date
        body.add(buildFieldLabel("Date"));
        body.add(Box.createVerticalStrut(4));
        dateField = styledTextField(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        body.add(dateField);
        body.add(Box.createVerticalStrut(20));

        // Submit Button
        submitTransactionBtn = buildPrimaryButton("＋  Add Transaction", ACCENT_GREEN);
        submitTransactionBtn.addActionListener(e -> handleSubmitTransaction());
        body.add(submitTransactionBtn);

        body.add(Box.createVerticalGlue());

        // Wire type toggle to category list update
        typeCombo = new JComboBox<>(new String[]{"Expense", "Income"});
        // The toggle handles state — see buildTypeToggle

        ((JPanel) card.getComponent(1)).add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildTypeToggle() {
        JPanel wrapper = new JPanel(new GridLayout(1, 2, 0, 0));
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        wrapper.setBackground(BG_INPUT);
        wrapper.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JButton expenseBtn = new JButton("Expense");
        JButton incomeBtn  = new JButton("Income");

        styleToggleButton(expenseBtn, true,  ACCENT_RED);
        styleToggleButton(incomeBtn,  false, ACCENT_GREEN);

        expenseBtn.addActionListener(e -> {
            styleToggleButton(expenseBtn, true,  ACCENT_RED);
            styleToggleButton(incomeBtn,  false, ACCENT_GREEN);
            updateCategoryCombo(false);
        });
        incomeBtn.addActionListener(e -> {
            styleToggleButton(incomeBtn,  true,  ACCENT_GREEN);
            styleToggleButton(expenseBtn, false, ACCENT_RED);
            updateCategoryCombo(true);
        });

        wrapper.add(expenseBtn);
        wrapper.add(incomeBtn);
        return wrapper;
    }

    private void styleToggleButton(JButton btn, boolean active, Color color) {
        btn.setFont(FONT_BTN);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (active) {
            btn.setBackground(color.darker());
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(BG_INPUT);
            btn.setForeground(TEXT_MUTED);
        }
    }

    private void updateCategoryCombo(boolean income) {
        categoryCombo.removeAllItems();
        if (income) {
            for (String s : new String[]{"Salary","Freelance","Investment","Gift","Refund","Other Income"})
                categoryCombo.addItem(s);
        } else {
            for (String s : new String[]{"Housing","Food & Dining","Transport","Utilities",
                                          "Healthcare","Entertainment","Shopping","Education","Other"})
                categoryCombo.addItem(s);
        }
    }

    private void handleSubmitTransaction() {
        if (transactionListener != null) {
            // Determine type by checking which toggle is active —
            // simplified: read from categoryCombo items to infer type
            String type  = categoryCombo.getItemAt(0).equals("Salary") ? "Income" : "Expense";
            transactionListener.onTransactionSubmitted(
                type,
                (String) categoryCombo.getSelectedItem(),
                (String) accountCombo.getSelectedItem(),
                amountField.getText().trim(),
                descriptionField.getText().trim(),
                dateField.getText().trim()
            );
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL 3 – Recent Transactions
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildRecentPanel() {
        JPanel card = createCard("Recent Transactions");

        JPanel body = (JPanel) card.getComponent(1);
        body.setLayout(new BorderLayout(0, 12));

        // Table
        String[] cols = {"Date", "Description", "Category", "Account", "Type", "Amount"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        transactionTable = new JTable(tableModel);
        styleTable(transactionTable);

        JScrollPane scroll = new JScrollPane(transactionTable);
        scroll.setBackground(BG_CARD);
        scroll.getViewport().setBackground(BG_CARD);
        scroll.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        scroll.setOpaque(false);

        // Scroll bar styling
        scroll.getVerticalScrollBar().setBackground(BG_CARD);
        scroll.getVerticalScrollBar().setForeground(BORDER_COLOR);
        scroll.getHorizontalScrollBar().setBackground(BG_CARD);

        body.add(scroll, BorderLayout.CENTER);

        // Export button row
        JPanel btnRow = new JPanel(new BorderLayout(10, 0));
        btnRow.setOpaque(false);

        JLabel countLbl = new JLabel("0 transactions");
        countLbl.setFont(FONT_SMALL);
        countLbl.setForeground(TEXT_MUTED);

        exportCsvBtn = buildPrimaryButton("⬇  Export to CSV", ACCENT_BLUE);
        exportCsvBtn.setPreferredSize(new Dimension(160, 36));
        exportCsvBtn.addActionListener(e -> {
            if (exportListener != null) exportListener.onExportRequested();
        });

        btnRow.add(countLbl,    BorderLayout.WEST);
        btnRow.add(exportCsvBtn, BorderLayout.EAST);
        body.add(btnRow, BorderLayout.SOUTH);

        // Seed with placeholder rows for visual preview
        addSampleRows();

        return card;
    }

    private void styleTable(JTable table) {
        table.setFont(FONT_LABEL);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(BG_CARD);
        table.setRowHeight(38);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(HOVER_COLOR);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setForeground(TEXT_MUTED);
        header.setBackground(BG_DARK);
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.LEFT);

        // Amount column renderer — color by sign
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String txt = val == null ? "" : val.toString();
                setFont(new Font("Consolas", Font.BOLD, 12));
                setForeground(txt.startsWith("+") ? ACCENT_GREEN :
                              txt.startsWith("-") ? ACCENT_RED   : TEXT_PRIMARY);
                setBackground(sel ? HOVER_COLOR : (row % 2 == 0 ? BG_CARD : BG_DARK));
                setOpaque(true);
                setHorizontalAlignment(SwingConstants.RIGHT);
                setBorder(new EmptyBorder(0, 0, 0, 12));
                return c;
            }
        });

        // Type column renderer — pill badge
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String type = val == null ? "" : val.toString();
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
                lbl.setForeground(type.equals("Income") ? ACCENT_GREEN : ACCENT_RED);
                lbl.setBackground(sel ? HOVER_COLOR : (row % 2 == 0 ? BG_CARD : BG_DARK));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Zebra stripe all other columns
        DefaultTableCellRenderer stripe = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(sel ? HOVER_COLOR : (row % 2 == 0 ? BG_CARD : BG_DARK));
                setForeground(TEXT_PRIMARY);
                setOpaque(true);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        };
        for (int i = 0; i < 4; i++) table.getColumnModel().getColumn(i).setCellRenderer(stripe);
    }

    private void addSampleRows() {
        tableModel.addRow(new Object[]{"04/16/2026", "Netflix Subscription",   "Entertainment", "Checking", "Expense", "-$15.99"});
        tableModel.addRow(new Object[]{"04/15/2026", "April Salary",           "Salary",        "Checking", "Income",  "+$3,200.00"});
        tableModel.addRow(new Object[]{"04/14/2026", "Grocery Store",          "Food & Dining", "Checking", "Expense", "-$87.43"});
        tableModel.addRow(new Object[]{"04/13/2026", "Electric Bill",          "Utilities",     "Joints",   "Expense", "-$112.00"});
        tableModel.addRow(new Object[]{"04/12/2026", "Freelance Project",      "Freelance",     "Savings",  "Income",  "+$500.00"});
        tableModel.addRow(new Object[]{"04/10/2026", "Gas Station",            "Transport",     "Checking", "Expense", "-$54.20"});
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Shared UI Helpers
    // ══════════════════════════════════════════════════════════════════════════

    /** Creates a titled card panel. Component index 1 is the body BorderLayout panel. */
    private JPanel createCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(0, 0, 0, 0)
        ));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_COLOR),
            new EmptyBorder(14, 18, 14, 18)
        ));
        JLabel lbl = new JLabel(title);
        lbl.setFont(FONT_HEADING);
        lbl.setForeground(TEXT_PRIMARY);
        header.add(lbl, BorderLayout.WEST);

        // Body
        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(16, 18, 16, 18));

        card.add(header, BorderLayout.NORTH);
        card.add(body,   BorderLayout.CENTER);
        return card;
    }

    private JLabel buildFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField styledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder) {
            private boolean firstFocus = true;
            {
                addFocusListener(new FocusAdapter() {
                    @Override public void focusGained(FocusEvent e) {
                        if (firstFocus) { setText(""); firstFocus = false; }
                        setForeground(TEXT_PRIMARY);
                    }
                });
            }
        };
        field.setFont(FONT_LABEL);
        field.setForeground(TEXT_MUTED);
        field.setBackground(BG_INPUT);
        field.setCaretColor(ACCENT_BLUE);
        field.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(FONT_LABEL);
        combo.setForeground(TEXT_PRIMARY);
        combo.setBackground(BG_INPUT);
        combo.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setFocusable(false);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val,
                    int idx, boolean sel, boolean foc) {
                JLabel c = (JLabel) super.getListCellRendererComponent(list, val, idx, sel, foc);
                c.setBackground(sel ? HOVER_COLOR : BG_INPUT);
                c.setForeground(TEXT_PRIMARY);
                c.setBorder(new EmptyBorder(6, 10, 6, 10));
                return c;
            }
        });
        return combo;
    }

    private JButton buildPrimaryButton(String text, Color accent) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover()
                    ? accent.darker()
                    : accent.darker().darker();
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Public API — call these from your backend
    // ══════════════════════════════════════════════════════════════════════════

    /** Update the displayed balance for a given account. */
    public void setAccountBalance(String account, double balance) {
        String fmt = String.format("$%,.2f", balance);
        SwingUtilities.invokeLater(() -> {
            switch (account.toLowerCase()) {
                case "checking": checkingBalanceLabel.setText(fmt); break;
                case "savings":  savingsBalanceLabel.setText(fmt);  break;
                case "joints":   jointsBalanceLabel.setText(fmt);   break;
            }
        });
    }

    /** Update the net worth total displayed at the bottom of the accounts panel. */
    public void setTotalBalance(double total) {
        SwingUtilities.invokeLater(() ->
            totalBalanceLabel.setText(String.format("$%,.2f", total)));
    }

    /**
     * Add a row to the Recent Transactions table.
     * @param date        "MM/dd/yyyy"
     * @param description transaction description
     * @param category    category string
     * @param account     "Checking" | "Savings" | "Joints"
     * @param type        "Income" | "Expense"
     * @param amount      formatted amount, e.g. "+$200.00" or "-$45.00"
     */
    public void addTransactionRow(String date, String description,
                                  String category, String account,
                                  String type, String amount) {
        SwingUtilities.invokeLater(() ->
            tableModel.insertRow(0, new Object[]{date, description, category, account, type, amount}));
    }

    /** Clear all rows from the transaction table. */
    public void clearTransactions() {
        SwingUtilities.invokeLater(() -> tableModel.setRowCount(0));
    }

    /** Register a listener for when the user submits a transaction form. */
    public void setTransactionListener(TransactionListener l) { this.transactionListener = l; }

    /** Register a listener for when the user clicks Export to CSV. */
    public void setExportListener(ExportListener l) { this.exportListener = l; }

    /** Access raw table model for programmatic row iteration during CSV export. */
    public DefaultTableModel getTableModel() { return tableModel; }

    // ══════════════════════════════════════════════════════════════════════════
    //  Entry Point (for standalone preview)
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        // Use system look and feel as base, then override everything
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}

        // Global UI defaults
        UIManager.put("Panel.background",            BG_DARK);
        UIManager.put("ScrollPane.background",       BG_CARD);
        UIManager.put("ScrollBar.thumb",             BORDER_COLOR);
        UIManager.put("ScrollBar.track",             BG_DARK);
        UIManager.put("ComboBox.background",         BG_INPUT);
        UIManager.put("ComboBox.foreground",         TEXT_PRIMARY);
        UIManager.put("ComboBox.selectionBackground",HOVER_COLOR);
        UIManager.put("ComboBox.selectionForeground",TEXT_PRIMARY);
        UIManager.put("PopupMenu.background",        BG_INPUT);
        UIManager.put("MenuItem.background",         BG_INPUT);
        UIManager.put("MenuItem.foreground",         TEXT_PRIMARY);
        UIManager.put("MenuItem.selectionBackground",HOVER_COLOR);

        SwingUtilities.invokeLater(() -> {
            FinanceDashboard dash = new FinanceDashboard();

            // Example: wire up the export button to print CSV to console
            dash.setExportListener(() -> {
                DefaultTableModel model = dash.getTableModel();
                StringBuilder csv = new StringBuilder("Date,Description,Category,Account,Type,Amount\n");
                for (int r = 0; r < model.getRowCount(); r++) {
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        csv.append(model.getValueAt(r, c));
                        if (c < model.getColumnCount() - 1) csv.append(",");
                    }
                    csv.append("\n");
                }
                System.out.println(csv);
                JOptionPane.showMessageDialog(dash,
                    "CSV exported to console output.\nWire setExportListener() to save a file.",
                    "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            });

            // Example: wire up transaction submit
            dash.setTransactionListener((type, category, account, amount, desc, date) -> {
                System.out.printf("[NEW TX] %s | %s | %s | %s | %s | %s%n",
                    date, desc, category, account, type, amount);
            });
        });
    }
}