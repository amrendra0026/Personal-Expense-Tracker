import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class AdminDashboardFrame extends JFrame {

    private final int adminId;
    private final String adminName;
    private final String adminEmail;
    private final String adminPhone;
    private final String adminPhoto;

    private JLabel usersValue;
    private JLabel transactionsValue;
    private JLabel incomeValue;
    private JLabel expenseValue;

    public AdminDashboardFrame(
            int adminId,
            String adminName,
            String adminEmail,
            String adminPhone,
            String adminPhoto
    ) {

        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPhone = adminPhone;
        this.adminPhoto = adminPhoto;

        setTitle("Expense Tracker - Admin Dashboard");
        setSize(1250, 780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();
        loadStatistics();

        setVisible(true);
    }

    // =====================================================
    // MAIN UI
    // =====================================================

    private void buildUI() {

        JPanel mainPanel = new JPanel(
                new BorderLayout()
        );

        mainPanel.setBackground(
                new Color(245, 247, 250)
        );

        // =================================================
        // SIDEBAR
        // =================================================

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(245, 0)
        );

        sidebar.setBackground(
                new Color(20, 28, 45)
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.add(
                Box.createVerticalStrut(25)
        );

        JLabel logo = new JLabel("🛡");

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        42
                )
        );

        sidebar.add(logo);

        JLabel title = new JLabel(
                "EXPENSE TRACKER"
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        sidebar.add(title);

        JLabel adminText = new JLabel(
                "ADMIN PANEL"
        );

        adminText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        adminText.setForeground(
                new Color(120, 180, 255)
        );

        adminText.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        sidebar.add(adminText);

        sidebar.add(
                Box.createVerticalStrut(35)
        );

        sidebar.add(
                createMenuButton(
                        "📊   Dashboard",
                        true
                )
        );

        sidebar.add(
                createMenuButton(
                        "👥   Users",
                        false
                )
        );

        sidebar.add(
                createMenuButton(
                        "💳   Transactions",
                        false
                )
        );

        sidebar.add(
                createMenuButton(
                        "📈   Reports",
                        false
                )
        );

        sidebar.add(
                createMenuButton(
                        "⚙   Settings",
                        false
                )
        );

        sidebar.add(
                Box.createVerticalGlue()
        );

        JLabel version = new JLabel(
                "Admin • Expense Tracker v1.0"
        );

        version.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        version.setForeground(
                new Color(120, 135, 160)
        );

        version.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        sidebar.add(version);

        sidebar.add(
                Box.createVerticalStrut(18)
        );

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        // =================================================
        // RIGHT AREA
        // =================================================

        JPanel rightPanel = new JPanel(
                new BorderLayout()
        );

        rightPanel.setBackground(
                new Color(245, 247, 250)
        );

        // =================================================
        // TOP BAR
        // =================================================

        JPanel topBar = new JPanel(
                new BorderLayout()
        );

        topBar.setBackground(Color.WHITE);

        topBar.setBorder(
                new EmptyBorder(
                        15,
                        25,
                        15,
                        25
                )
        );

        JLabel pageTitle = new JLabel(
                "Admin Dashboard"
        );

        pageTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        pageTitle.setForeground(
                new Color(30, 40, 55)
        );

        topBar.add(
                pageTitle,
                BorderLayout.WEST
        );

        // =================================================
        // ADMIN PROFILE
        // =================================================

        JPanel profilePanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        0
                )
        );

        profilePanel.setOpaque(false);

        JLabel profileImage =
                createProfileImage(
                        adminPhoto,
                        45,
                        45
                );

        profilePanel.add(profileImage);

        JLabel nameLabel = new JLabel(
                adminName
        );

        nameLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        nameLabel.setForeground(
                new Color(35, 45, 60)
        );

        profilePanel.add(nameLabel);

        JButton menuButton =
                new JButton("▼");

        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        profilePanel.add(menuButton);

        JPopupMenu menu =
                new JPopupMenu();

        JMenuItem profileItem =
                new JMenuItem(
                        "👤  Admin Profile"
                );

        JMenuItem logoutItem =
                new JMenuItem(
                        "🚪  Logout"
                );

        menu.add(profileItem);
        menu.addSeparator();
        menu.add(logoutItem);

        menuButton.addActionListener(e ->
                menu.show(
                        menuButton,
                        0,
                        menuButton.getHeight()
                )
        );

        profileItem.addActionListener(e ->
                showProfile()
        );

        logoutItem.addActionListener(e ->
                logout()
        );

        topBar.add(
                profilePanel,
                BorderLayout.EAST
        );

        rightPanel.add(
                topBar,
                BorderLayout.NORTH
        );

        // =================================================
        // CONTENT
        // =================================================

        JPanel content =
                new JPanel();

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBackground(
                new Color(245, 247, 250)
        );

        content.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        JLabel welcome =
                new JLabel(
                        "Welcome " + adminName + " 👋"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        welcome.setForeground(
                new Color(30, 40, 55)
        );

        content.add(welcome);

        JLabel subtitle =
                new JLabel(
                        "Monitor your Expense Tracker system"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(110, 120, 135)
        );

        content.add(
                Box.createVerticalStrut(5)
        );

        content.add(subtitle);

        content.add(
                Box.createVerticalStrut(25)
        );

        // =================================================
        // STATISTICS CARDS
        // =================================================

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                18,
                                18
                        )
                );

        cards.setOpaque(false);

        usersValue =
                new JLabel("0");

        transactionsValue =
                new JLabel("0");

        incomeValue =
                new JLabel("₹ 0.00");

        expenseValue =
                new JLabel("₹ 0.00");

        cards.add(
                createStatCard(
                        "👥",
                        "TOTAL USERS",
                        usersValue,
                        new Color(
                                65,
                                120,
                                220
                        )
                )
        );

        cards.add(
                createStatCard(
                        "📋",
                        "TRANSACTIONS",
                        transactionsValue,
                        new Color(
                                145,
                                90,
                                210
                        )
                )
        );

        cards.add(
                createStatCard(
                        "💰",
                        "TOTAL INCOME",
                        incomeValue,
                        new Color(
                                35,
                                160,
                                105
                        )
                )
        );

        cards.add(
                createStatCard(
                        "💸",
                        "TOTAL EXPENSE",
                        expenseValue,
                        new Color(
                                225,
                                85,
                                85
                        )
                )
        );

        content.add(cards);

        content.add(
                Box.createVerticalStrut(25)
        );

        // =================================================
        // ADMIN INFORMATION
        // =================================================

        JPanel infoPanel =
                new JPanel(
                        new BorderLayout()
                );

        infoPanel.setBackground(Color.WHITE);

        infoPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        238
                                )
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        JLabel infoTitle =
                new JLabel(
                        "System Overview"
                );

        infoTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        infoTitle.setForeground(
                new Color(
                        35,
                        45,
                        60
                )
        );

        infoPanel.add(
                infoTitle,
                BorderLayout.NORTH
        );

        JLabel info =
                new JLabel(
                        "<html>" +
                                "Welcome to the Expense Tracker Admin Panel.<br>" +
                                "Here you can monitor registered users, " +
                                "transactions, income and expenses." +
                                "</html>"
                );

        info.setForeground(
                new Color(
                        100,
                        110,
                        125
                )
        );

        info.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        info.setBorder(
                new EmptyBorder(
                        15,
                        0,
                        0,
                        0
                )
        );

        infoPanel.add(
                info,
                BorderLayout.CENTER
        );

        content.add(infoPanel);

        rightPanel.add(
                new JScrollPane(
                        content
                ),
                BorderLayout.CENTER
        );

        mainPanel.add(
                rightPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    // =====================================================
    // STAT CARD
    // =====================================================

    private JPanel createStatCard(
            String icon,
            String title,
            JLabel value,
            Color accent
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        238
                                )
                        ),
                        new EmptyBorder(
                                18,
                                22,
                                18,
                                22
                        )
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        28
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                new Color(
                        120,
                        130,
                        145
                )
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        value.setForeground(accent);

        value.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        card.add(iconLabel);

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(value);

        return card;
    }

    // =====================================================
    // SIDEBAR BUTTON
    // =====================================================

    private JButton createMenuButton(
            String text,
            boolean active
    ) {

        JButton button =
                new JButton(text);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        button.setBorder(
                new EmptyBorder(
                        12,
                        25,
                        12,
                        10
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        if (active) {

            button.setBackground(
                    new Color(
                            50,
                            115,
                            220
                    )
            );

            button.setForeground(
                    Color.WHITE
            );

        } else {

            button.setBackground(
                    new Color(
                            20,
                            28,
                            45
                    )
            );

            button.setForeground(
                    new Color(
                            190,
                            200,
                            215
                    )
            );
        }

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =====================================================
    // PROFILE IMAGE
    // =====================================================

    private JLabel createProfileImage(
            String path,
            int width,
            int height
    ) {

        JLabel label =
                new JLabel();

        if (path != null &&
                !path.isEmpty()) {

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

        label.setText("👤");

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        30
                )
        );

        return label;
    }

    // =====================================================
    // LOAD ADMIN STATISTICS
    // =====================================================

    private void loadStatistics() {

        String usersSQL =
                "SELECT COUNT(*) FROM users";

        String transactionsSQL =
                "SELECT COUNT(*) FROM transactions";

        String incomeSQL =
                "SELECT COALESCE(SUM(amount),0) " +
                        "FROM transactions " +
                        "WHERE type='INCOME'";

        String expenseSQL =
                "SELECT COALESCE(SUM(amount),0) " +
                        "FROM transactions " +
                        "WHERE type='EXPENSE'";

        try (
                Connection connection =
                        DBConnection.getConnection()
        ) {

            // USERS
            try (
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    usersSQL
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    usersValue.setText(
                            String.valueOf(
                                    rs.getInt(1)
                            )
                    );
                }
            }

            // TRANSACTIONS
            try (
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    transactionsSQL
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    transactionsValue.setText(
                            String.valueOf(
                                    rs.getInt(1)
                            )
                    );
                }
            }

            // INCOME
            try (
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    incomeSQL
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    incomeValue.setText(
                            "₹ " +
                                    String.format(
                                            "%.2f",
                                            rs.getDouble(1)
                                    )
                    );
                }
            }

            // EXPENSE
            try (
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    expenseSQL
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    expenseValue.setText(
                            "₹ " +
                                    String.format(
                                            "%.2f",
                                            rs.getDouble(1)
                                    )
                    );
                }
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load admin statistics:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // =====================================================
    // PROFILE
    // =====================================================

    private void showProfile() {

        JOptionPane.showMessageDialog(
                this,
                "Name: " + adminName +
                        "\nEmail: " + adminEmail +
                        "\nPhone: " + adminPhone +
                        "\nRole: ADMIN",
                "Admin Profile",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // LOGOUT
    // =====================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            dispose();

            new LoginFrame();
        }
    }
}