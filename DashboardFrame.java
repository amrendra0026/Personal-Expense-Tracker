import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.IdentityHashMap;

public class DashboardFrame extends JFrame {

    // =========================================================
    // USER DATA
    // =========================================================

    private final int userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userPhoto;

    private JLabel profileNameValue;
    private JLabel profileEmailValue;
    private JLabel profilePhoneValue;
    private JLabel profilePhotoLabel;
    private JLabel topUserNameLabel;

    // Theme support: original colours are retained so switching themes
    // never permanently changes the existing dashboard design.
    private JPanel rootPanel;
    private String activeTheme = "LIGHT";
    private final Map<Component, Color> originalBackgrounds =
            new IdentityHashMap<>();
    private final Map<Component, Color> originalForegrounds =
            new IdentityHashMap<>();

    // =========================================================
    // COLORS
    // =========================================================

    private final Color SIDEBAR =
            new Color(18, 29, 48);

    private final Color BLUE =
            new Color(52, 115, 220);

    private final Color LIGHT_BG =
            new Color(244, 247, 252);

    private final Color TEXT =
            new Color(25, 48, 82);

    private final Color GREEN =
            new Color(34, 178, 105);

    private final Color RED =
            new Color(228, 70, 80);

    private final Color PURPLE =
            new Color(145, 75, 205);

    // =========================================================
    // CARD LAYOUT
    // =========================================================

    private CardLayout cardLayout;
    private JPanel contentPanel;

    // =========================================================
    // DASHBOARD LABELS
    // =========================================================

    private JLabel incomeValue;
    private JLabel expenseValue;
    private JLabel balanceValue;

    private JTextArea recentArea;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DashboardFrame(
            int userId,
            String userName,
            String userEmail,
            String userPhone,
            String userPhoto
    ) {

        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPhoto = userPhoto;

        setTitle("Expense Tracker - Dashboard");

        setSize(1500, 900);

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        buildUI();

        loadDashboardData();

        setVisible(true);
    }

    // =========================================================
    // MAIN UI
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        rootPanel = root;
        root.setBackground(LIGHT_BG);

        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar =
                createSidebar();

        root.add(
                sidebar,
                BorderLayout.WEST
        );

        // =====================================================
        // RIGHT AREA
        // =====================================================

        JPanel rightSide =
                new JPanel(
                        new BorderLayout()
                );

        rightSide.setBackground(
                LIGHT_BG
        );

        // =====================================================
        // TOP BAR
        // =====================================================

        JPanel topBar =
                createTopBar();

        rightSide.add(
                topBar,
                BorderLayout.NORTH
        );

        // =====================================================
        // CARD CONTENT
        // =====================================================

        cardLayout =
                new CardLayout();

        contentPanel =
                new JPanel(cardLayout);

        contentPanel.setBackground(
                LIGHT_BG
        );

        // Dashboard
        contentPanel.add(
                createDashboardPanel(),
                "DASHBOARD"
        );

        // Expense
        contentPanel.add(
                createExpensePanel(),
                "EXPENSE"
        );

        // Income
        contentPanel.add(
                createIncomePanel(),
                "INCOME"
        );

        // Transactions
        contentPanel.add(
                createTransactionPanel(),
                "TRANSACTIONS"
        );

        // Reports
        contentPanel.add(
                createReportPanel(),
                "REPORTS"
        );

        // Profile
        contentPanel.add(
                createProfilePanel(),
                "PROFILE"
        );

        rightSide.add(
                contentPanel,
                BorderLayout.CENTER
        );

        root.add(
                rightSide,
                BorderLayout.CENTER
        );

        add(root);
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setPreferredSize(
                new Dimension(300, 0)
        );

        sidebar.setBackground(
                SIDEBAR
        );

        // =====================================================
        // BRAND
        // =====================================================

        JPanel brand =
                new JPanel();

        brand.setOpaque(false);

        brand.setLayout(
                new BoxLayout(
                        brand,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo =
                loadLogo(
                        AppPaths.assetPath("expense-logo.png"),
                        155,
                        155
                );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        brand.add(
                Box.createVerticalStrut(25)
        );

        brand.add(logo);

        brand.add(
                Box.createVerticalStrut(12)
        );

        JLabel title =
                new JLabel(
                        "EXPENSE TRACKER"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        brand.add(title);

        JLabel subtitle =
                new JLabel(
                        "PERSONAL FINANCE"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setForeground(
                new Color(
                        170,
                        190,
                        215
                )
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        brand.add(subtitle);

        brand.add(
                Box.createVerticalStrut(35)
        );

        sidebar.add(
                brand,
                BorderLayout.NORTH
        );

        // =====================================================
        // MENU
        // =====================================================

        JPanel menu =
                new JPanel();

        menu.setOpaque(false);

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );

        JButton dashboardButton =
                createMenuButton(
                        "Dashboard"
                );

        JButton expenseButton =
                createMenuButton(
                        "Expenses"
                );

        JButton incomeButton =
                createMenuButton(
                        "Income"
                );

        JButton transactionButton =
                createMenuButton(
                        "Transactions"
                );

        JButton reportButton =
                createMenuButton(
                        "Reports"
                );

        JButton profileButton =
                createMenuButton(
                        "My Profile"
                );

        menu.add(dashboardButton);
        menu.add(expenseButton);
        menu.add(incomeButton);
        menu.add(transactionButton);
        menu.add(reportButton);
        menu.add(profileButton);

        // =====================================================
        // MENU ACTIONS
        // =====================================================

        dashboardButton.addActionListener(
                e -> showPage("DASHBOARD")
        );

        expenseButton.addActionListener(
                e -> showPage("EXPENSE")
        );

        incomeButton.addActionListener(
                e -> showPage("INCOME")
        );

        transactionButton.addActionListener(
                e -> showPage("TRANSACTIONS")
        );

        reportButton.addActionListener(
                e -> showPage("REPORTS")
        );

        profileButton.addActionListener(
                e -> showPage("PROFILE")
        );

        sidebar.add(
                menu,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                new JPanel();

        footer.setOpaque(false);

        footer.setLayout(
                new BoxLayout(
                        footer,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel developer =
                new JLabel(
                        ""
                );

        developer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        developer.setForeground(
                new Color(
                        145,
                        180,
                        225
                )
        );

        developer.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        JLabel version =
                new JLabel(
                        "Expense Tracker v1.0"
                );

        version.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        version.setForeground(
                new Color(
                        120,
                        150,
                        190
                )
        );

        version.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        footer.add(developer);
        footer.add(
                Box.createVerticalStrut(5)
        );
        footer.add(version);

        footer.add(
                Box.createVerticalStrut(20)
        );

        sidebar.add(
                footer,
                BorderLayout.SOUTH
        );

        return sidebar;
    }

    // =========================================================
    // MENU BUTTON
    // =========================================================

    private JButton createMenuButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        62
                )
        );

        button.setPreferredSize(
                new Dimension(
                        300,
                        62
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                SIDEBAR
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        42,
                                        62,
                                        90
                                )
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                SIDEBAR
                        );
                    }
                }
        );

        return button;
    }

    // =========================================================
    // TOP BAR - PREMIUM ACCOUNT CONTROL
    // =========================================================

    private JPanel createTopBar() {

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(18, 40, 18, 35));

        JLabel heading = new JLabel("Dashboard");
        heading.setForeground(TEXT);
        heading.setFont(new Font("Arial", Font.BOLD, 38));
        top.add(heading, BorderLayout.WEST);

        // A compact profile chip replaces the old USER label and plain arrow.
        JPanel accountChip = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        accountChip.setOpaque(true);
        accountChip.setBackground(new Color(246, 248, 253));
        accountChip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 231, 241)),
                new EmptyBorder(7, 10, 7, 12)
        ));
        accountChip.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel avatar = new JLabel(getUserInitials());
        avatar.setPreferredSize(new Dimension(38, 38));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(103, 75, 220));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("Arial", Font.BOLD, 15));
        avatar.setBorder(BorderFactory.createLineBorder(new Color(103, 75, 220), 1));

        JPanel nameBlock = new JPanel();
        nameBlock.setOpaque(false);
        nameBlock.setLayout(new BoxLayout(nameBlock, BoxLayout.Y_AXIS));

        JLabel accountCaption = new JLabel("SIGNED IN AS");
        accountCaption.setForeground(new Color(130, 139, 157));
        accountCaption.setFont(new Font("Arial", Font.BOLD, 9));

        topUserNameLabel = new JLabel(userName == null ? "Account" : userName);
        topUserNameLabel.setForeground(TEXT);
        topUserNameLabel.setFont(new Font("Arial", Font.BOLD, 15));

        nameBlock.add(accountCaption);
        nameBlock.add(Box.createVerticalStrut(2));
        nameBlock.add(topUserNameLabel);

        JLabel arrow = new JLabel("⌄");
        arrow.setForeground(new Color(103, 75, 220));
        arrow.setFont(new Font("Arial", Font.BOLD, 22));

        accountChip.add(avatar);
        accountChip.add(nameBlock);
        accountChip.add(Box.createHorizontalStrut(5));
        accountChip.add(arrow);

        MouseAdapter accountClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showUserMenu(accountChip);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                accountChip.setBackground(new Color(235, 239, 250));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                accountChip.setBackground(new Color(246, 248, 253));
            }
        };
        accountChip.addMouseListener(accountClick);
        for (Component child : accountChip.getComponents()) {
            child.addMouseListener(accountClick);
        }

        top.add(accountChip, BorderLayout.EAST);
        return top;
    }

    private String getUserInitials() {
        if (userName == null || userName.trim().isEmpty()) return "U";
        String[] parts = userName.trim().split("\\s+");
        if (parts.length == 1) return parts[0].substring(0, 1).toUpperCase();
        return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1))
                .toUpperCase();
    }

    // =========================================================
    // PREMIUM ACCOUNT MENU
    // =========================================================

    private void showUserMenu(Component component) {

        JPopupMenu popup = new JPopupMenu();
        popup.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 223, 235)),
                new EmptyBorder(9, 9, 9, 9)
        ));
        popup.setBackground(Color.WHITE);

        JPanel menuHeader = new JPanel(new BorderLayout(10, 2));
        menuHeader.setBackground(new Color(29, 39, 65));
        menuHeader.setBorder(new EmptyBorder(12, 13, 12, 13));

        JLabel headerAvatar = new JLabel(getUserInitials(), SwingConstants.CENTER);
        headerAvatar.setPreferredSize(new Dimension(42, 42));
        headerAvatar.setOpaque(true);
        headerAvatar.setBackground(new Color(117, 91, 230));
        headerAvatar.setForeground(Color.WHITE);
        headerAvatar.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        JLabel headerName = new JLabel(userName == null ? "Account" : userName);
        headerName.setForeground(Color.WHITE);
        headerName.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel headerEmail = new JLabel(userEmail == null ? "Personal account" : userEmail);
        headerEmail.setForeground(new Color(197, 205, 224));
        headerEmail.setFont(new Font("Arial", Font.PLAIN, 10));
        headerText.add(headerName);
        headerText.add(Box.createVerticalStrut(4));
        headerText.add(headerEmail);

        menuHeader.add(headerAvatar, BorderLayout.WEST);
        menuHeader.add(headerText, BorderLayout.CENTER);
        menuHeader.setPreferredSize(new Dimension(245, 66));
        popup.add(menuHeader);
        popup.addSeparator();

        JMenuItem profile = new JMenuItem("   My Profile");
        JCheckBoxMenuItem darkMode = new JCheckBoxMenuItem(
                "   Dark Mode", "DARK".equals(activeTheme));
        JCheckBoxMenuItem nightMode = new JCheckBoxMenuItem(
                "   Night Mode", "NIGHT".equals(activeTheme));
        JMenuItem logoutItem = new JMenuItem("   Logout");

        Font menuFont = new Font("Arial", Font.PLAIN, 14);
        for (JMenuItem item : new JMenuItem[]{profile, darkMode, nightMode, logoutItem}) {
            item.setFont(menuFont);
            item.setPreferredSize(new Dimension(245, 40));
            item.setBackground(Color.WHITE);
            item.setForeground(new Color(43, 52, 75));
            item.setCursor(new Cursor(Cursor.HAND_CURSOR));
            item.setBorder(new EmptyBorder(5, 8, 5, 8));
        }

        profile.addActionListener(e -> showPage("PROFILE"));
        darkMode.addActionListener(e -> applyTheme(darkMode.isSelected() ? "DARK" : "LIGHT"));
        nightMode.addActionListener(e -> applyTheme(nightMode.isSelected() ? "NIGHT" : "LIGHT"));

        logoutItem.setForeground(new Color(202, 61, 75));
        logoutItem.addActionListener(e -> logout());

        popup.add(profile);
        popup.add(darkMode);
        popup.add(nightMode);
        popup.addSeparator();
        popup.add(logoutItem);

        popup.show(component, component.getWidth() - 255, component.getHeight());
    }

    // =========================================================
    // LIGHT / DARK / NIGHT THEME
    // =========================================================

    private void applyTheme(String theme) {
        activeTheme = theme;
        if (rootPanel == null) return;

        captureOriginalThemeColors(rootPanel);

        Color pageBackground;
        Color cardBackground;
        Color mainText;

        if ("DARK".equals(theme)) {
            pageBackground = new Color(25, 30, 42);
            cardBackground = new Color(39, 45, 59);
            mainText = new Color(238, 242, 250);
        } else if ("NIGHT".equals(theme)) {
            pageBackground = new Color(35, 29, 34);
            cardBackground = new Color(49, 41, 47);
            mainText = new Color(247, 235, 222);
        } else {
            pageBackground = LIGHT_BG;
            cardBackground = Color.WHITE;
            mainText = TEXT;
        }

        for (Component component : originalBackgrounds.keySet()) {
            Color original = originalBackgrounds.get(component);
            if (original == null) continue;

            if (LIGHT_BG.equals(original)) {
                component.setBackground(pageBackground);
            } else if (Color.WHITE.equals(original)) {
                component.setBackground(cardBackground);
            }
        }

        for (Component component : originalForegrounds.keySet()) {
            Color original = originalForegrounds.get(component);
            if (original != null && TEXT.equals(original)) {
                component.setForeground(mainText);
            }
        }

        // Improve input and table readability without changing coloured action cards.
        updateThemeForSpecialComponents(rootPanel, theme, cardBackground, mainText);
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private void captureOriginalThemeColors(Component component) {
        if (component instanceof JComponent) {
            originalBackgrounds.putIfAbsent(component, component.getBackground());
            originalForegrounds.putIfAbsent(component, component.getForeground());
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                captureOriginalThemeColors(child);
            }
        }
    }

    private void updateThemeForSpecialComponents(
            Component component, String theme, Color cardBackground, Color mainText) {

        Color inputBackground = "LIGHT".equals(theme)
                ? Color.WHITE
                : ("DARK".equals(theme)
                ? new Color(31, 37, 50)
                : new Color(40, 34, 39));

        if (component instanceof JTextField || component instanceof JTextArea
                || component instanceof JComboBox || component instanceof JTable) {
            component.setBackground(inputBackground);
            component.setForeground(mainText);
            if (component instanceof JTextField) {
                ((JTextField) component).setCaretColor(mainText);
            }
            if (component instanceof JTextArea) {
                ((JTextArea) component).setCaretColor(mainText);
            }
        }

        if (component instanceof JTable) {
            JTable table = (JTable) component;
            table.setGridColor("LIGHT".equals(theme)
                    ? new Color(190, 198, 210) : new Color(85, 94, 112));
            table.setSelectionBackground(BLUE);
            table.setSelectionForeground(Color.WHITE);
            table.getTableHeader().setBackground(cardBackground);
            table.getTableHeader().setForeground(mainText);
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                updateThemeForSpecialComponents(child, theme, cardBackground, mainText);
            }
        }
    }

    // =========================================================
    // DASHBOARD PAGE
    // =========================================================

    private JPanel createDashboardPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                LIGHT_BG
        );

        panel.setBorder(
                new EmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );

        top.setOpaque(false);

        JLabel welcome =
                new JLabel(
                        "  "
                                + userName
                                + "!"
                );

        welcome.setForeground(
                TEXT
        );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        top.add(
                welcome,
                BorderLayout.WEST
        );

        panel.add(
                top,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN DASHBOARD
        // =====================================================

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                0,
                                25
                        )
                );

        center.setOpaque(false);

        // =====================================================
        // STAT CARDS
        // =====================================================

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                25,
                                0
                        )
                );

        stats.setOpaque(false);

        JPanel incomeCard =
                createStatCard(
                        "TOTAL INCOME",
                        "₹ 0.00",
                        GREEN
                );

        JPanel expenseCard =
                createStatCard(
                        "TOTAL EXPENSE",
                        "₹ 0.00",
                        RED
                );

        JPanel balanceCard =
                createStatCard(
                        "CURRENT BALANCE",
                        "₹ 0.00",
                        BLUE
                );

        incomeValue =
                findValueLabel(
                        incomeCard
                );

        expenseValue =
                findValueLabel(
                        expenseCard
                );

        balanceValue =
                findValueLabel(
                        balanceCard
                );

        stats.add(incomeCard);
        stats.add(expenseCard);
        stats.add(balanceCard);

        center.add(
                stats,
                BorderLayout.NORTH
        );

        // =====================================================
        // QUICK ACTIONS
        // =====================================================

        JPanel actions =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                25,
                                0
                        )
                );

        actions.setOpaque(false);

        JButton addExpense =
                createActionButton(
                        "ADD EXPENSE",
                        RED
                );

        JButton addIncome =
                createActionButton(
                        "ADD INCOME",
                        GREEN
                );

        addExpense.addActionListener(
                e -> showPage("EXPENSE")
        );

        addIncome.addActionListener(
                e -> showPage("INCOME")
        );

        actions.add(addExpense);
        actions.add(addIncome);

        center.add(
                actions,
                BorderLayout.CENTER
        );

        // =====================================================
        // RECENT TRANSACTIONS
        // =====================================================

        JPanel recentPanel =
                new JPanel(
                        new BorderLayout()
                );

        recentPanel.setBackground(
                Color.WHITE
        );

        recentPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        225,
                                        235
                                )
                        ),
                        new EmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );

        JLabel recentTitle =
                new JLabel(
                        "Recent Transactions"
                );

        recentTitle.setForeground(
                TEXT
        );

        recentTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        recentPanel.add(
                recentTitle,
                BorderLayout.NORTH
        );

        recentArea =
                new JTextArea();

        recentArea.setEditable(false);

        recentArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        recentArea.setForeground(TEXT);

        recentArea.setBackground(
                Color.WHITE
        );

        recentArea.setText(
                "No transactions yet."
        );

        recentPanel.add(
                new JScrollPane(
                        recentArea
                ),
                BorderLayout.CENTER
        );

        center.add(
                recentPanel,
                BorderLayout.SOUTH
        );

        panel.add(
                center,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // EXPENSE PAGE
    // =========================================================

    private JPanel createExpensePanel() {

        JPanel panel =
                createFormPage(
                        "ADD EXPENSE",
                        "Record your new expense",
                        RED
                );

        JTextField amount =
                createField();

        JComboBox<String> category = createCategoryCombo(
                "Food & Dining", "Shopping", "Transport", "Fuel",
                "Bills", "Education", "Healthcare", "Entertainment",
                "Rent", "Travel", "Other"
        );

        JTextField date =
                createField();

        date.setText(
                LocalDate.now().toString()
        );

        JTextField description =
                createField();

        addFormRow(
                panel,
                "AMOUNT",
                amount
        );

        addFormRow(
                panel,
                "CATEGORY",
                category
        );

        addFormRow(
                panel,
                "DATE (YYYY-MM-DD)",
                date
        );

        addFormRow(
                panel,
                "DESCRIPTION",
                description
        );

        JButton save =
                createActionButton(
                        "SAVE EXPENSE",
                        RED
                );

        save.addActionListener(
                e -> saveTransaction(
                        "EXPENSE",
                        amount,
                        category,
                        date,
                        description
                )
        );

        panel.add(
                save
        );

        return panel;
    }

    // =========================================================
    // INCOME PAGE
    // =========================================================

    private JPanel createIncomePanel() {

        JPanel panel =
                createFormPage(
                        "ADD INCOME",
                        "Record your new income",
                        GREEN
                );

        JTextField amount =
                createField();

        JComboBox<String> category = createCategoryCombo(
                "Salary", "Business", "Freelance", "Investment",
                "Gift", "Bonus", "Other"
        );

        JTextField date =
                createField();

        date.setText(
                LocalDate.now().toString()
        );

        JTextField description =
                createField();

        addFormRow(
                panel,
                "AMOUNT",
                amount
        );

        addFormRow(
                panel,
                "SOURCE / CATEGORY",
                category
        );

        addFormRow(
                panel,
                "DATE (YYYY-MM-DD)",
                date
        );

        addFormRow(
                panel,
                "DESCRIPTION",
                description
        );

        JButton save =
                createActionButton(
                        "SAVE INCOME",
                        GREEN
                );

        save.addActionListener(
                e -> saveTransaction(
                        "INCOME",
                        amount,
                        category,
                        date,
                        description
                )
        );

        panel.add(
                save
        );

        return panel;
    }

    // =========================================================
    // TRANSACTION PAGE
    // =========================================================

    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("TRANSACTIONS");
        title.setForeground(TEXT);
        title.setFont(new Font("Arial", Font.BOLD, 32));

        JButton refresh = createActionButton("↻  REFRESH TRANSACTIONS", BLUE);
        refresh.setPreferredSize(new Dimension(270, 56));
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        titleBar.add(title, BorderLayout.WEST);
        titleBar.add(refresh, BorderLayout.EAST);
        panel.add(titleBar, BorderLayout.NORTH);

        String[] columns = {"ID", "TYPE", "CATEGORY", "AMOUNT", "DATE", "DESCRIPTION", "EDIT", "DELETE"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.getColumnModel().getColumn(6).setPreferredWidth(85);
        table.getColumnModel().getColumn(7).setPreferredWidth(95);

        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row < 0) return;
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                if (col == 6) editTransaction(id, model);
                else if (col == 7) deleteTransaction(id, model);
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);
        refresh.addActionListener(e -> loadTransactions(model));
        SwingUtilities.invokeLater(() -> loadTransactions(model));
        return panel;
    }

    private void editTransaction(int transactionId, DefaultTableModel model) {
        String sql = "SELECT type, category, amount, transaction_date, description FROM transactions WHERE id=? AND user_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, transactionId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return;
                JTextField amount = new JTextField(String.valueOf(rs.getDouble("amount")));
                JComboBox<String> category = new JComboBox<>(new String[]{rs.getString("category"), "Food & Dining", "Shopping", "Transport", "Fuel", "Bills", "Education", "Healthcare", "Entertainment", "Salary", "Business", "Freelance", "Other"});
                category.setEditable(true);
                JTextField date = new JTextField(rs.getDate("transaction_date").toString());
                JTextField description = new JTextField(rs.getString("description") == null ? "" : rs.getString("description"));
                JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
                form.add(new JLabel("Amount")); form.add(amount);
                form.add(new JLabel("Category / Source")); form.add(category);
                form.add(new JLabel("Date (YYYY-MM-DD)")); form.add(date);
                form.add(new JLabel("Description")); form.add(description);
                int result = JOptionPane.showConfirmDialog(this, form, "Edit Transaction", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result != JOptionPane.OK_OPTION) return;
                double value = Double.parseDouble(amount.getText().trim());
                String newCategory = String.valueOf(category.getSelectedItem()).trim();
                Date newDate = Date.valueOf(date.getText().trim());
                if (value <= 0 || newCategory.isEmpty()) throw new IllegalArgumentException("Enter a valid amount and category.");
                String update = "UPDATE transactions SET category=?, amount=?, transaction_date=?, description=? WHERE id=? AND user_id=?";
                try (PreparedStatement up = c.prepareStatement(update)) {
                    up.setString(1, newCategory); up.setDouble(2, value); up.setDate(3, newDate);
                    up.setString(4, description.getText().trim()); up.setInt(5, transactionId); up.setInt(6, userId);
                    up.executeUpdate();
                }
            }
            loadTransactions(model);
            loadDashboardData();
            JOptionPane.showMessageDialog(this, "Transaction updated successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unable to edit transaction: " + ex.getMessage(), "Edit Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTransaction(int transactionId, DefaultTableModel model) {
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this transaction permanently?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
        String sql = "DELETE FROM transactions WHERE id=? AND user_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, transactionId); ps.setInt(2, userId);
            ps.executeUpdate();
            loadTransactions(model);
            loadDashboardData();
            JOptionPane.showMessageDialog(this, "Transaction deleted successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unable to delete transaction: " + ex.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // REPORT PAGE
    // =========================================================

    private JPanel createReportPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                25
                        )
                );

        panel.setBackground(
                LIGHT_BG
        );

        panel.setBorder(
                new EmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        JLabel title =
                new JLabel(
                        "REPORTS"
                );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                25,
                                0
                        )
                );

        cards.setOpaque(false);

        JLabel income =
                new JLabel(
                        "₹ 0.00"
                );

        JLabel expense =
                new JLabel(
                        "₹ 0.00"
                );

        JLabel balance =
                new JLabel(
                        "₹ 0.00"
                );

        cards.add(
                createReportCard(
                        "TOTAL INCOME",
                        income,
                        GREEN
                )
        );

        cards.add(
                createReportCard(
                        "TOTAL EXPENSE",
                        expense,
                        RED
                )
        );

        cards.add(
                createReportCard(
                        "CURRENT BALANCE",
                        balance,
                        BLUE
                )
        );

        panel.add(
                cards,
                BorderLayout.CENTER
        );

        JButton refresh =
                createActionButton(
                        "REFRESH REPORT",
                        PURPLE
                );

        refresh.addActionListener(
                e -> loadReportValues(
                        income,
                        expense,
                        balance
                )
        );

// TOP HEADER: Title Left + Refresh Right
        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setOpaque(false);

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                refresh,
                BorderLayout.EAST
        );

        panel.add(
                header,
                BorderLayout.NORTH
        );

        SwingUtilities.invokeLater(
                () -> loadReportValues(
                        income,
                        expense,
                        balance
                )
        );

        return panel;
    }

    // =========================================================
    // PROFILE PAGE
    // =========================================================

    private JPanel createProfilePanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(LIGHT_BG);

        JPanel card = new JPanel(new BorderLayout(35, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 225, 235)),
                        new EmptyBorder(35, 40, 35, 40)
                )
        );
        card.setPreferredSize(new Dimension(850, 430));

        // LEFT SIDE: PROFILE PHOTO
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(245, 300));

        JLabel photoTitle = new JLabel("PROFILE PHOTO");
        photoTitle.setFont(new Font("Arial", Font.BOLD, 18));
        photoTitle.setForeground(TEXT);
        photoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilePhotoLabel = loadLogo(userPhoto, 200, 200);
        profilePhotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalStrut(25));
        leftPanel.add(photoTitle);
        leftPanel.add(Box.createVerticalStrut(25));
        leftPanel.add(profilePhotoLabel);

        // RIGHT SIDE: USER DETAILS
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("MY PROFILE");
        title.setForeground(TEXT);
        title.setFont(new Font("Arial", Font.BOLD, 30));

        profileNameValue = profileLabel("Name: " + safeText(userName));
        profileEmailValue = profileLabel("Email: " + safeText(userEmail));
        profilePhoneValue = profileLabel("Mobile: " + safeText(userPhone));

        JButton editButton = createActionButton("EDIT PROFILE", BLUE);
        editButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        editButton.addActionListener(e -> editProfile());

        rightPanel.add(Box.createVerticalStrut(25));
        rightPanel.add(title);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(profileNameValue);
        rightPanel.add(Box.createVerticalStrut(12));
        rightPanel.add(profileEmailValue);
        rightPanel.add(Box.createVerticalStrut(12));
        rightPanel.add(profilePhoneValue);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(editButton);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.CENTER);
        panel.add(card);

        return panel;
    }

    // =========================================================
    // EDIT PROFILE
    // =========================================================

    private void editProfile() {

        JTextField nameField = new JTextField(safeText(userName), 22);
        JTextField emailField = new JTextField(safeText(userEmail), 22);
        JTextField phoneField = new JTextField(safeText(userPhone), 22);
        JTextField photoField = new JTextField(safeText(userPhoto), 22);
        photoField.setEditable(false);

        JButton browseButton = new JButton("Choose Photo");
        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select Profile Photo");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Image files", "jpg", "jpeg", "png", "gif", "webp"
            ));

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                photoField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        JPanel photoPanel = new JPanel(new BorderLayout(8, 0));
        photoPanel.add(photoField, BorderLayout.CENTER);
        photoPanel.add(browseButton, BorderLayout.EAST);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 15));
        form.add(new JLabel("Full Name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Mobile:"));
        form.add(phoneField);
        form.add(new JLabel("Profile Photo:"));
        form.add(photoPanel);

        int choice = JOptionPane.showConfirmDialog(
                this,
                form,
                "Edit My Profile",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newPhone = phoneField.getText().trim();
        String newPhoto = photoField.getText().trim();

        if (newName.isEmpty() || newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email are required.");
            return;
        }

        if (!newEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }

        if (!newPhoto.isEmpty() && !new File(newPhoto).isFile()) {
            JOptionPane.showMessageDialog(this, "Selected profile photo was not found.");
            return;
        }

        String sql = "UPDATE users SET name=?, email=?, phone=?, photo=? WHERE id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newName);
            statement.setString(2, newEmail);
            statement.setString(3, newPhone);
            statement.setString(4, newPhoto);
            statement.setInt(5, userId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                userName = newName;
                userEmail = newEmail;
                userPhone = newPhone;
                userPhoto = newPhoto;

                profileNameValue.setText("Name: " + safeText(userName));
                profileEmailValue.setText("Email: " + safeText(userEmail));
                profilePhoneValue.setText("Mobile: " + safeText(userPhone));

                JLabel updatedPhoto = loadLogo(userPhoto, 200, 200);
                profilePhotoLabel.setIcon(updatedPhoto.getIcon());
                profilePhotoLabel.setText(updatedPhoto.getText());
                profilePhotoLabel.setPreferredSize(new Dimension(200, 200));

                if (topUserNameLabel != null) {
                    topUserNameLabel.setText(userName);
                }

                profilePhotoLabel.revalidate();
                profilePhotoLabel.repaint();

                JOptionPane.showMessageDialog(
                        this,
                        "Profile updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(this, "Profile update failed.");
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "This email address is already registered.",
                    "Email Already Exists",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update profile:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String safeText(String value) {
        return value == null ? "" : value;
    }

    // =========================================================
    // FORM PAGE
    // =========================================================

    private JPanel createFormPage(
            String title,
            String subtitle,
            Color color
    ) {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                LIGHT_BG
        );

        panel.setBorder(
                new EmptyBorder(
                        30,
                        150,
                        30,
                        150
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        titleLabel.setForeground(color);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        panel.add(titleLabel);

        JLabel sub =
                new JLabel(subtitle);

        sub.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sub.setForeground(
                new Color(
                        90,
                        110,
                        140
                )
        );

        sub.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        panel.add(sub);

        panel.add(
                Box.createVerticalStrut(30)
        );

        return panel;
    }

    // =========================================================
    // CATEGORY DROPDOWN
    // =========================================================

    private JComboBox<String> createCategoryCombo(String... categories) {
        JComboBox<String> combo = new JComboBox<>(categories);
        combo.setEditable(true);
        combo.setMaximumSize(new Dimension(700, 48));
        combo.setPreferredSize(new Dimension(700, 48));
        combo.setFont(new Font("Arial", Font.PLAIN, 16));
        combo.setBackground(Color.WHITE);
        combo.setForeground(TEXT);
        combo.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 225)));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return combo;
    }

    private void addFormRow(JPanel panel, String labelText, JComboBox<String> field) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(TEXT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        panel.add(Box.createVerticalStrut(7));
        panel.add(field);
        panel.add(Box.createVerticalStrut(18));
    }

    // =========================================================
    // FORM ROW
    // =========================================================

    private void addFormRow(
            JPanel panel,
            String labelText,
            JTextField field
    ) {

        JLabel label =
                new JLabel(labelText);

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        label.setForeground(TEXT);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        panel.add(label);

        panel.add(
                Box.createVerticalStrut(7)
        );

        panel.add(field);

        panel.add(
                Box.createVerticalStrut(18)
        );
    }

    // =========================================================
    // FIELD
    // =========================================================

    private JTextField createField() {

        JTextField field =
                new JTextField();

        field.setMaximumSize(
                new Dimension(
                        700,
                        48
                )
        );

        field.setPreferredSize(
                new Dimension(
                        700,
                        48
                )
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        200,
                                        210,
                                        225
                                )
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );

        return field;
    }

    // =========================================================
    // SAVE TRANSACTION
    // =========================================================

    private void saveTransaction(
            String type,
            JTextField amountField,
            JComboBox<String> categoryField,
            JTextField dateField,
            JTextField descriptionField
    ) {

        String amountText =
                amountField.getText().trim();

        String category = String.valueOf(categoryField.getSelectedItem()).trim();

        String date =
                dateField.getText().trim();

        String description =
                descriptionField.getText().trim();

        if (
                amountText.isEmpty()
                        ||
                        category.isEmpty()
                        ||
                        date.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return;
        }

        double amount;

        try {

            amount =
                    Double.parseDouble(
                            amountText
                    );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount must be a valid number."
            );

            return;
        }

        String sql =
                "INSERT INTO transactions " +
                        "(user_id,type,category,amount," +
                        "description,transaction_date) " +
                        "VALUES (?,?,?,?,?,?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            statement.setString(
                    2,
                    type
            );

            statement.setString(
                    3,
                    category
            );

            statement.setDouble(
                    4,
                    amount
            );

            statement.setString(
                    5,
                    description
            );

            statement.setDate(
                    6,
                    Date.valueOf(date)
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    type
                            + " saved successfully!"
            );

            loadDashboardData();

            showPage("DASHBOARD");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // DASHBOARD DATA
    // =========================================================

    private void loadDashboardData() {

        String sql =
                "SELECT " +
                        "COALESCE(SUM(CASE WHEN type='INCOME' " +
                        "THEN amount ELSE 0 END),0) AS income, " +
                        "COALESCE(SUM(CASE WHEN type='EXPENSE' " +
                        "THEN amount ELSE 0 END),0) AS expense " +
                        "FROM transactions " +
                        "WHERE user_id=?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            ResultSet rs =
                    statement.executeQuery();

            if (rs.next()) {

                double income =
                        rs.getDouble("income");

                double expense =
                        rs.getDouble("expense");

                double balance =
                        income - expense;

                if (incomeValue != null) {

                    incomeValue.setText(
                            "₹ "
                                    + String.format(
                                    "%.2f",
                                    income
                            )
                    );
                }

                if (expenseValue != null) {

                    expenseValue.setText(
                            "₹ "
                                    + String.format(
                                    "%.2f",
                                    expense
                            )
                    );
                }

                if (balanceValue != null) {

                    balanceValue.setText(
                            "₹ "
                                    + String.format(
                                    "%.2f",
                                    balance
                            )
                    );
                }
            }

            loadRecentTransactions();

        } catch (Exception ex) {

            System.out.println(
                    "Dashboard Error: "
                            + ex.getMessage()
            );
        }
    }

    // =========================================================
    // RECENT TRANSACTIONS
    // =========================================================

    private void loadRecentTransactions() {

        if (recentArea == null) {
            return;
        }

        String sql =
                "SELECT id, type, category, amount, " +
                        "transaction_date, description " +
                        "FROM transactions " +
                        "WHERE user_id=? " +
                        "ORDER BY id DESC LIMIT 10";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            ResultSet rs =
                    statement.executeQuery();

            StringBuilder text =
                    new StringBuilder();

            while (rs.next()) {

                text.append(
                        rs.getString("type")
                );

                text.append(
                        "   |   "
                );

                text.append(
                        rs.getString("category")
                );

                text.append(
                        "   |   ₹"
                );

                text.append(
                        rs.getDouble("amount")
                );

                text.append(
                        "   |   "
                );

                text.append(
                        rs.getDate(
                                "transaction_date"
                        )
                );

                text.append("\n");

                if (
                        rs.getString(
                                "description"
                        ) != null
                ) {

                    text.append(
                            "Description: "
                    );

                    text.append(
                            rs.getString(
                                    "description"
                            )
                    );

                    text.append(
                            "\n"
                    );
                }

                text.append(
                        "--------------------------------------------------\n"
                );
            }

            if (text.length() == 0) {

                recentArea.setText(
                        "No transactions yet."
                );

            } else {

                recentArea.setText(
                        text.toString()
                );
            }

        } catch (Exception ex) {

            recentArea.setText(
                    "Unable to load transactions."
            );
        }
    }

    // =========================================================
    // TRANSACTION TABLE
    // =========================================================

    private void loadTransactions(
            DefaultTableModel model
    ) {

        model.setRowCount(0);

        String sql =
                "SELECT id, type, category, amount, " +
                        "transaction_date, description " +
                        "FROM transactions " +
                        "WHERE user_id=? " +
                        "ORDER BY id DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            ResultSet rs =
                    statement.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("category"),
                                "₹ "
                                        + String.format(
                                        "%.2f",
                                        rs.getDouble(
                                                "amount"
                                        )
                                ),
                                rs.getDate(
                                        "transaction_date"
                                ),
                                rs.getString(
                                        "description"
                                ),
                                "✎ EDIT",
                                "🗑 DELETE"
                        }
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load transactions."
            );
        }
    }

    // =========================================================
    // REPORT VALUES
    // =========================================================

    private void loadReportValues(
            JLabel income,
            JLabel expense,
            JLabel balance
    ) {

        String sql =
                "SELECT " +
                        "COALESCE(SUM(CASE WHEN type='INCOME' " +
                        "THEN amount ELSE 0 END),0) AS income, " +
                        "COALESCE(SUM(CASE WHEN type='EXPENSE' " +
                        "THEN amount ELSE 0 END),0) AS expense " +
                        "FROM transactions " +
                        "WHERE user_id=?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            ResultSet rs =
                    statement.executeQuery();

            if (rs.next()) {

                double in =
                        rs.getDouble("income");

                double out =
                        rs.getDouble("expense");

                income.setText(
                        "₹ "
                                + String.format(
                                "%.2f",
                                in
                        )
                );

                expense.setText(
                        "₹ "
                                + String.format(
                                "%.2f",
                                out
                        )
                );

                balance.setText(
                        "₹ "
                                + String.format(
                                "%.2f",
                                in - out
                        )
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            Color color
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(color);

        card.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(
                Color.WHITE
        );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        34
                )
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(12)
        );

        card.add(valueLabel);

        return card;
    }

    // =========================================================
    // REPORT CARD
    // =========================================================

    private JPanel createReportCard(
            String title,
            JLabel value,
            Color color
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(color);

        card.setBorder(
                new EmptyBorder(
                        35,
                        30,
                        35,
                        30
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        value.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        value.setForeground(
                Color.WHITE
        );

        value.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(value);

        return card;
    }

    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private JButton createActionButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(color);

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        25,
                        15,
                        25
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // PROFILE LABEL
    // =========================================================

    private JLabel profileLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        label.setForeground(TEXT);

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        17
                )
        );

        label.setBorder(
                new EmptyBorder(
                        8,
                        0,
                        8,
                        0
                )
        );

        return label;
    }

    // =========================================================
    // LOGO
    // =========================================================

    private JLabel loadLogo(
            String path,
            int width,
            int height
    ) {

        JLabel label =
                new JLabel();

        if (
                path != null
                        &&
                        !path.isEmpty()
        ) {

            File file =
                    new File(path);

            if (file.exists()) {

                ImageIcon icon =
                        new ImageIcon(
                                file.getAbsolutePath()
                        );

                Image image =
                        icon.getImage()
                                .getScaledInstance(
                                        width,
                                        height,
                                        Image.SCALE_SMOOTH
                                );

                label.setIcon(
                        new ImageIcon(image)
                );

                return label;
            }
        }

        label.setText("💰");

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        55
                )
        );

        return label;
    }

    // =========================================================
    // FIND VALUE LABEL
    // =========================================================

    private JLabel findValueLabel(
            JPanel card
    ) {

        for (
                Component component :
                card.getComponents()
        ) {

            if (
                    component
                            instanceof JLabel
                            &&
                            component !=
                                    card.getComponents()[0]
            ) {

                return (JLabel) component;
            }
        }

        return null;
    }

    // =========================================================
    // SHOW PAGE
    // =========================================================

    private void showPage(
            String page
    ) {

        cardLayout.show(
                contentPanel,
                page
        );

        if (
                page.equals("DASHBOARD")
        ) {

            loadDashboardData();
        }
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                result ==
                        JOptionPane.YES_OPTION
        ) {

            dispose();

            new LoginFrame();
        }
    }
}
