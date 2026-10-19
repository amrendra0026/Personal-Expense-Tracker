


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private final Color WHITE = Color.WHITE;
    private final Color CYAN = new Color(0, 220, 255);
    private final Color BLUE = new Color(45, 125, 245);
    private final Color DARK = new Color(5, 15, 30, 225);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField emailField;
    private JPasswordField passwordField;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LoginFrame() {

        setTitle("Expense Tracker - Login");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1550, 950);

        setMinimumSize(new Dimension(1100, 700));

        setLocationRelativeTo(null);

        setResizable(true);

        // =====================================================
        // BACKGROUND
        // =====================================================

        BackgroundPanel background =
                new BackgroundPanel(
                        AppPaths.assetPath("login-background1.png")
                );

        background.setLayout(new BorderLayout());

        setContentPane(background);

        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        mainPanel.setOpaque(false);

        background.add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // LEFT SIDE
        // =====================================================

        JPanel leftPanel = new JPanel();

        leftPanel.setOpaque(false);

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS
                )
        );

        leftPanel.setBorder(
                new EmptyBorder(
                        100,
                        75,
                        60,
                        40
                )
        );

        // Space
        leftPanel.add(
                Box.createVerticalGlue()
        );

        JLabel title =
                new JLabel(
                        "EXPENSE TRACKER"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        45
                )
        );

        title.setForeground(WHITE);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leftPanel.add(title);

        leftPanel.add(
                Box.createVerticalStrut(12)
        );

        JLabel subtitle =
                new JLabel(
                        "PERSONAL FINANCE MANAGEMENT"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        22
                )
        );

        subtitle.setForeground(WHITE);

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leftPanel.add(subtitle);

        leftPanel.add(
                Box.createVerticalStrut(300)
        );

        JLabel controlTitle =
                new JLabel(
                        "Take control of your money."
                );

        controlTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        controlTitle.setForeground(WHITE);

        controlTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leftPanel.add(controlTitle);

        leftPanel.add(
                Box.createVerticalStrut(12)
        );

        JLabel description =
                new JLabel(
                        "Track your income, expenses and transactions in one simple application."
                );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        description.setForeground(WHITE);

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leftPanel.add(description);

        leftPanel.add(
                Box.createVerticalGlue()
        );

        mainPanel.add(leftPanel);

        // =====================================================
        // RIGHT LOGIN CARD
        // =====================================================

        JPanel rightWrapper =
                new JPanel(
                        new GridBagLayout()
                );

        rightWrapper.setOpaque(false);

        rightWrapper.setBorder(
                new EmptyBorder(
                        35,
                        20,
                        35,
                        60
                )
        );

        JPanel loginCard =
                new JPanel();

        loginCard.setLayout(
                new BoxLayout(
                        loginCard,
                        BoxLayout.Y_AXIS
                )
        );

        loginCard.setBackground(DARK);

        loginCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        100
                                ),
                                1
                        ),
                        new EmptyBorder(
                                35,
                                55,
                                35,
                                55
                        )
                )
        );

        // =====================================================
        // LOGO INSIDE LOGIN CARD
        // =====================================================

        JLabel logo =
                createLogo(
                        AppPaths.assetPath("expense-logo.png"),
                        110,
                        110
                );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginCard.add(logo);

        loginCard.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // WELCOME
        // =====================================================

        JLabel welcome =
                new JLabel(
                        "WELCOME"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        34
                )
        );

        welcome.setForeground(WHITE);

        welcome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginCard.add(welcome);

        loginCard.add(
                Box.createVerticalStrut(5)
        );

        JLabel loginSubtitle =
                new JLabel(
                        "Login to manage your expenses"
                );

        loginSubtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        17
                )
        );

        loginSubtitle.setForeground(WHITE);

        loginSubtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginCard.add(loginSubtitle);

        loginCard.add(
                Box.createVerticalStrut(38)
        );

        // =====================================================
        // EMAIL LABEL
        // =====================================================

        JLabel emailLabel =
                new JLabel(
                        "EMAIL ADDRESS"
                );

        emailLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        emailLabel.setForeground(WHITE);

        emailLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginCard.add(emailLabel);

        loginCard.add(
                Box.createVerticalStrut(8)
        );

        // =====================================================
        // EMAIL FIELD
        // =====================================================

        emailField =
                new JTextField();

        styleTextField(emailField);

        loginCard.add(emailField);

        loginCard.add(
                Box.createVerticalStrut(25)
        );

        // =====================================================
        // PASSWORD LABEL
        // =====================================================

        JLabel passwordLabel =
                new JLabel(
                        "PASSWORD"
                );

        passwordLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        passwordLabel.setForeground(WHITE);

        passwordLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginCard.add(passwordLabel);

        loginCard.add(
                Box.createVerticalStrut(8)
        );

        // =====================================================
        // PASSWORD FIELD
        // =====================================================

        passwordField =
                new JPasswordField();

        styleTextField(passwordField);

        loginCard.add(passwordField);

        loginCard.add(
                Box.createVerticalStrut(32)
        );

        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        JButton loginButton =
                new JButton(
                        "LOGIN"
                );

        styleButton(
                loginButton,
                BLUE
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.addActionListener(
                e -> loginUser()
        );

        loginCard.add(loginButton);

        loginCard.add(
                Box.createVerticalStrut(25)
        );

        // =====================================================
        // ACCOUNT TEXT
        // =====================================================

        JLabel accountText =
                new JLabel(
                        "Don't have an account?"
                );

        accountText.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        accountText.setForeground(WHITE);

        accountText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginCard.add(accountText);

        loginCard.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // REGISTER BUTTON
        // =====================================================

        JButton registerButton =
                new JButton(
                        "CREATE NEW ACCOUNT"
                );

        registerButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        registerButton.setForeground(CYAN);

        registerButton.setBackground(
                new Color(
                        0,
                        0,
                        0,
                        0
                )
        );

        registerButton.setBorderPainted(false);

        registerButton.setFocusPainted(false);

        registerButton.setContentAreaFilled(false);

        registerButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        registerButton.addActionListener(
                e -> openRegister()
        );

        loginCard.add(registerButton);

        // =====================================================
        // ADD CARD
        // =====================================================

        rightWrapper.add(loginCard);

        mainPanel.add(rightWrapper);

        // =====================================================
        // FOOTER
        // =====================================================

        JLabel footer =
                new JLabel(
                        "© 2026 Expense Tracker • Personal Finance Management"
                );

        footer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        footer.setForeground(WHITE);

        footer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        footer.setBorder(
                new EmptyBorder(
                        8,
                        0,
                        8,
                        0
                )
        );

        background.add(
                footer,
                BorderLayout.SOUTH
        );

        // =====================================================
        // ENTER KEY
        // =====================================================

        passwordField.addActionListener(
                e -> loginUser()
        );

        emailField.addActionListener(
                e -> passwordField.requestFocus()
        );

        // =====================================================
        // SHOW
        // =====================================================

        setVisible(true);

        SwingUtilities.invokeLater(
                () -> emailField.requestFocus()
        );
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setMaximumSize(
                new Dimension(
                        430,
                        55
                )
        );

        field.setPreferredSize(
                new Dimension(
                        430,
                        55
                )
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        field.setForeground(
                Color.BLACK
        );

        field.setBackground(
                new Color(
                        250,
                        250,
                        250
                )
        );

        field.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        field.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );
    }

    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setMaximumSize(
                new Dimension(
                        430,
                        58
                )
        );

        button.setPreferredSize(
                new Dimension(
                        430,
                        58
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =========================================================
    // CREATE LOGO
    // =========================================================

    private JLabel createLogo(
            String path,
            int width,
            int height
    ) {

        JLabel label =
                new JLabel();

        File file =
                new File(path);

        if (file.exists()) {

            ImageIcon original =
                    new ImageIcon(
                            file.getAbsolutePath()
                    );

            Image image =
                    original
                            .getImage()
                            .getScaledInstance(
                                    width,
                                    height,
                                    Image.SCALE_SMOOTH
                            );

            label.setIcon(
                    new ImageIcon(image)
            );

        } else {

            label.setText("ET");

            label.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            50
                    )
            );

            label.setForeground(
                    WHITE
            );
        }

        return label;
    }

    // =========================================================
    // LOGIN USER
    // =========================================================

    private void loginUser() {

        String email =
                emailField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        // =====================================================
        // VALIDATION
        // =====================================================

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your email.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            emailField.requestFocus();

            return;
        }

        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            passwordField.requestFocus();

            return;
        }

        // =====================================================
        // DATABASE
        // =====================================================

        String sql =
                "SELECT id, name, email, phone, photo, role " +
                        "FROM users " +
                        "WHERE email = ? AND password = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    email
            );

            statement.setString(
                    2,
                    password
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                int userId =
                        result.getInt("id");

                String name =
                        result.getString("name");

                String userEmail =
                        result.getString("email");

                String phone =
                        result.getString("phone");

                String photo =
                        result.getString("photo");

                String role =
                        result.getString("role");

                // =================================================
                // ADMIN LOGIN
                // =================================================

                if (
                        role != null &&
                                role.equalsIgnoreCase("ADMIN")
                ) {

                    AdminDashboardFrame admin =
                            new AdminDashboardFrame(
                                    userId,
                                    name,
                                    userEmail,
                                    phone,
                                    photo
                            );

                    admin.setVisible(true);

                    dispose();

                }

                // =================================================
                // NORMAL USER LOGIN
                // =================================================

                else {

                    DashboardFrame dashboard =
                            new DashboardFrame(
                                    userId,
                                    name,
                                    userEmail,
                                    phone,
                                    photo
                            );

                    dashboard.setVisible(true);

                    dispose();
                }

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                passwordField.setText("");

                passwordField.requestFocus();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection error:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // =========================================================
    // OPEN REGISTER
    // =========================================================

    private void openRegister() {

        RegisterFrame register =
                new RegisterFrame();

        register.setVisible(true);

        dispose();
    }

    // =========================================================
    // BACKGROUND PANEL
    // =========================================================

    private static class BackgroundPanel
            extends JPanel {

        private Image backgroundImage;

        public BackgroundPanel(
                String imagePath
        ) {

            setOpaque(true);

            loadImage(imagePath);
        }

        private void loadImage(
                String imagePath
        ) {

            File file =
                    new File(imagePath);

            if (file.exists()) {

                backgroundImage =
                        new ImageIcon(
                                file.getAbsolutePath()
                        ).getImage();

            } else {

                backgroundImage = null;
            }
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            if (
                    backgroundImage != null &&
                            backgroundImage.getWidth(null) > 0
            ) {

                int panelWidth =
                        getWidth();

                int panelHeight =
                        getHeight();

                int imageWidth =
                        backgroundImage
                                .getWidth(null);

                int imageHeight =
                        backgroundImage
                                .getHeight(null);

                double scale =
                        Math.max(
                                (double) panelWidth
                                        / imageWidth,
                                (double) panelHeight
                                        / imageHeight
                        );

                int newWidth =
                        (int) (
                                imageWidth
                                        * scale
                        );

                int newHeight =
                        (int) (
                                imageHeight
                                        * scale
                        );

                int x =
                        (panelWidth - newWidth)
                                / 2;

                int y =
                        (panelHeight - newHeight)
                                / 2;

                g2.drawImage(
                        backgroundImage,
                        x,
                        y,
                        newWidth,
                        newHeight,
                        this
                );

            } else {

                // Fallback background
                GradientPaint gradient =
                        new GradientPaint(
                                0,
                                0,
                                new Color(
                                        30,
                                        55,
                                        90
                                ),
                                getWidth(),
                                getHeight(),
                                new Color(
                                        10,
                                        20,
                                        40
                                )
                        );

                g2.setPaint(gradient);

                g2.fillRect(
                        0,
                        0,
                        getWidth(),
                        getHeight()
                );
            }

            g2.dispose();
        }
    }

    // =========================================================
    // MAIN - OPTIONAL DIRECT TEST
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                LoginFrame::new
        );
    }
    private String getAssetPath(String fileName) {
        try {
            java.io.File jarLocation =
                    new java.io.File(
                            LoginFrame.class
                                    .getProtectionDomain()
                                    .getCodeSource()
                                    .getLocation()
                                    .toURI()
                    );

            java.io.File appFolder = jarLocation.getParentFile();

            return new java.io.File(
                    appFolder,
                    "assets/" + fileName
            ).getAbsolutePath();

        } catch (Exception e) {
            return "assets/" + fileName;
        }
    }
}