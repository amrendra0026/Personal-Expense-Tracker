import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JLabel photoLabel;

    private File selectedPhoto;

    public RegisterFrame() {

        setTitle("Expense Tracker - Create Account");

        setSize(
                1550,
                950
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        // =====================================================
        // BACKGROUND
        // =====================================================

        BackgroundPanel background =
                new BackgroundPanel(
                        AppPaths.assetPath("login-background1.png")
                );

        background.setLayout(
                new GridBagLayout()
        );

        // =====================================================
        // MAIN CARD
        // =====================================================

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setOpaque(true);

        card.setBackground(
                new Color(
                        25,
                        40,
                        65,
                        205
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        140
                                ),
                                1
                        ),
                        new EmptyBorder(
                                25,
                                45,
                                25,
                                45
                        )
                )
        );

        card.setPreferredSize(
                new Dimension(
                        720,
                        820
                )
        );

        // =====================================================
        // LOGO
        // =====================================================

        JLabel logo =
                createLogo(
                        AppPaths.assetPath("expense-logo.png"),
                        85,
                        85
                );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(logo);

        card.add(
                Box.createVerticalStrut(5)
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel title =
                new JLabel(
                        "CREATE YOUR ACCOUNT"
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

        card.add(title);

        card.add(
                Box.createVerticalStrut(18)
        );

        // =====================================================
        // PHOTO AREA
        // =====================================================

        photoLabel =
                new JLabel(
                        "NO PHOTO SELECTED",
                        SwingConstants.CENTER
                );

        photoLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        photoLabel.setPreferredSize(
                new Dimension(
                        500,
                        120
                )
        );

        photoLabel.setMaximumSize(
                new Dimension(
                        500,
                        120
                )
        );

        photoLabel.setMinimumSize(
                new Dimension(
                        500,
                        120
                )
        );

        photoLabel.setForeground(
                Color.WHITE
        );

        photoLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        photoLabel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                220,
                                230,
                                245
                        ),
                        1
                )
        );

        photoLabel.setOpaque(true);

        photoLabel.setBackground(
                new Color(
                        255,
                        255,
                        255,
                        40
                )
        );

        card.add(photoLabel);

        card.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // CHOOSE PHOTO
        // =====================================================

        JButton choosePhotoButton =
                new JButton(
                        "CHOOSE PHOTO"
                );

        styleBlueButton(
                choosePhotoButton
        );

        choosePhotoButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                choosePhotoButton
        );

        card.add(
                Box.createVerticalStrut(18)
        );

        // =====================================================
        // FULL NAME
        // =====================================================

        JPanel nameRow =
                createFieldRow(
                        "FULL NAME"
                );

        nameField =
                new JTextField();

        styleField(
                nameField
        );

        nameRow.add(
                nameField
        );

        card.add(nameRow);

        card.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // EMAIL
        // =====================================================

        JPanel emailRow =
                createFieldRow(
                        "EMAIL"
                );

        emailField =
                new JTextField();

        styleField(
                emailField
        );

        emailRow.add(
                emailField
        );

        card.add(emailRow);

        card.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // PHONE
        // =====================================================

        JPanel phoneRow =
                createFieldRow(
                        "MOBILE NUMBER"
                );

        phoneField =
                new JTextField();

        styleField(
                phoneField
        );

        phoneRow.add(
                phoneField
        );

        card.add(phoneRow);

        card.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // PASSWORD
        // =====================================================

        JPanel passwordRow =
                createFieldRow(
                        "PASSWORD"
                );

        passwordField =
                new JPasswordField();

        styleField(
                passwordField
        );

        passwordRow.add(
                passwordField
        );

        card.add(passwordRow);

        card.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // CONFIRM PASSWORD
        // =====================================================

        JPanel confirmRow =
                createFieldRow(
                        "CONFIRM PASSWORD"
                );

        confirmPasswordField =
                new JPasswordField();

        styleField(
                confirmPasswordField
        );

        confirmRow.add(
                confirmPasswordField
        );

        card.add(confirmRow);

        card.add(
                Box.createVerticalStrut(22)
        );

        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        JButton createButton =
                new JButton(
                        "CREATE ACCOUNT"
                );

        JButton clearButton =
                new JButton(
                        "CLEAR"
                );

        JButton backButton =
                new JButton(
                        "BACK TO LOGIN"
                );

        styleGreenButton(
                createButton
        );

        styleOrangeButton(
                clearButton
        );

        styleDarkButton(
                backButton
        );

        buttonPanel.add(
                createButton
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                backButton
        );

        card.add(
                buttonPanel
        );

        // =====================================================
        // CENTER CARD
        // =====================================================

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.anchor =
                GridBagConstraints.CENTER;

        background.add(
                card,
                gbc
        );

        // =====================================================
        // CHOOSE PHOTO ACTION
        // =====================================================

        choosePhotoButton.addActionListener(
                e -> choosePhoto()
        );

        // =====================================================
        // CREATE ACCOUNT ACTION
        // =====================================================

        createButton.addActionListener(
                e -> createAccount()
        );

        // =====================================================
        // CLEAR ACTION
        // =====================================================

        clearButton.addActionListener(
                e -> clearFields()
        );

        // =====================================================
        // BACK ACTION
        // =====================================================

        backButton.addActionListener(
                e -> {

                    dispose();

                    new LoginFrame();
                }
        );

        add(background);

        setVisible(true);
    }

    // =========================================================
    // FIELD ROW
    // =========================================================

    private JPanel createFieldRow(
            String labelText
    ) {

        JPanel row =
                new JPanel(
                        new GridBagLayout()
                );

        row.setOpaque(false);

        row.setMaximumSize(
                new Dimension(
                        620,
                        42
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        // Label
        JLabel label =
                new JLabel(
                        labelText
                );

        label.setForeground(
                Color.WHITE
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        15
                );

        row.add(
                label,
                gbc
        );

        return row;
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleField(
            JTextField field
    ) {

        field.setPreferredSize(
                new Dimension(
                        330,
                        38
                )
        );

        field.setMinimumSize(
                new Dimension(
                        330,
                        38
                )
        );

        field.setMaximumSize(
                new Dimension(
                        330,
                        38
                )
        );

        field.setBackground(
                new Color(
                        250,
                        252,
                        255
                )
        );

        field.setForeground(
                new Color(
                        25,
                        30,
                        40
                )
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        220,
                                        235
                                ),
                                1
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );
    }

    // =========================================================
    // CHOOSE PHOTO
    // =========================================================

    private void choosePhoto() {

        JFileChooser chooser =
                new JFileChooser();

        chooser.setDialogTitle(
                "Select Profile Photo"
        );

        int result =
                chooser.showOpenDialog(
                        this
                );

        if (
                result ==
                        JFileChooser.APPROVE_OPTION
        ) {

            selectedPhoto =
                    chooser.getSelectedFile();

            ImageIcon icon =
                    new ImageIcon(
                            selectedPhoto
                                    .getAbsolutePath()
                    );

            Image image =
                    icon.getImage()
                            .getScaledInstance(
                                    110,
                                    110,
                                    Image.SCALE_SMOOTH
                            );

            photoLabel.setIcon(
                    new ImageIcon(
                            image
                    )
            );

            photoLabel.setText("");
        }
    }

    // =========================================================
    // CREATE ACCOUNT
    // =========================================================

    private void createAccount() {

        String name =
                nameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );

        // =====================================================
        // VALIDATION
        // =====================================================

        if (
                name.isEmpty()
                        ||
                        email.isEmpty()
                        ||
                        phone.isEmpty()
                        ||
                        password.isEmpty()
                        ||
                        confirmPassword.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                selectedPhoto == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select your profile photo.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                !email.contains("@")
                        ||
                        !email.contains(".")
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                !phone.matches(
                        "\\d{10}"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Mobile number must be exactly 10 digits.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                password.length() < 6
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                !password.equals(
                        confirmPassword
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // SAVE PHOTO
        // =====================================================

        String photoPath =
                savePhoto();

        if (
                photoPath == null
        ) {

            return;
        }

        // =====================================================
        // DATABASE
        // =====================================================

        String sql =
                "INSERT INTO users "
                        +
                        "(name,email,phone,password,photo,role) "
                        +
                        "VALUES (?,?,?,?,?,'USER')";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    name
            );

            statement.setString(
                    2,
                    email
            );

            statement.setString(
                    3,
                    phone
            );

            statement.setString(
                    4,
                    password
            );

            statement.setString(
                    5,
                    photoPath
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new LoginFrame();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // =========================================================
    // SAVE PHOTO
    // =========================================================

    private String savePhoto() {

        try {

            File folder = AppPaths.profilePhotosDirectory();

            if (
                    !folder.exists()
            ) {

                folder.mkdirs();
            }

            String extension =
                    "";

            String fileName =
                    selectedPhoto.getName();

            int dot =
                    fileName.lastIndexOf(".");

            if (
                    dot >= 0
            ) {

                extension =
                        fileName.substring(
                                dot
                        );
            }

            File destination =
                    new File(
                            folder,
                            "user_"
                                    +
                                    System.currentTimeMillis()
                                    +
                                    extension
                    );

            Files.copy(
                    selectedPhoto.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            return destination.getPath();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Photo could not be saved:\n"
                            + ex.getMessage(),
                    "Photo Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return null;
        }
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        nameField.setText("");

        emailField.setText("");

        phoneField.setText("");

        passwordField.setText("");

        confirmPasswordField.setText("");

        selectedPhoto = null;

        photoLabel.setIcon(null);

        photoLabel.setText(
                "NO PHOTO SELECTED"
        );
    }

    // =========================================================
    // LOGO
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

        if (
                file.exists()
        ) {

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
                    new ImageIcon(
                            image
                    )
            );
        }

        return label;
    }

    // =========================================================
    // BUTTON STYLES
    // =========================================================

    private void styleBlueButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        45,
                        115,
                        220
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    private void styleGreenButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        30,
                        175,
                        105
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);
    }

    private void styleOrangeButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        240,
                        160,
                        35
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);
    }

    private void styleDarkButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        55,
                        70,
                        90
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);
    }

    // =========================================================
    // BACKGROUND PANEL
    // =========================================================

    private static class BackgroundPanel
            extends JPanel {

        private final Image backgroundImage;

        public BackgroundPanel(
                String imagePath
        ) {

            File file =
                    new File(
                            imagePath
                    );

            if (
                    file.exists()
            ) {

                backgroundImage =
                        new ImageIcon(
                                file.getAbsolutePath()
                        ).getImage();

            } else {

                backgroundImage =
                        null;
            }
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            if (
                    backgroundImage != null
            ) {

                g2.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );

            } else {

                GradientPaint gradient =
                        new GradientPaint(
                                0,
                                0,
                                new Color(
                                        25,
                                        45,
                                        80
                                ),
                                getWidth(),
                                getHeight(),
                                new Color(
                                        80,
                                        35,
                                        100
                                )
                        );

                g2.setPaint(
                        gradient
                );

                g2.fillRect(
                        0,
                        0,
                        getWidth(),
                        getHeight()
                );
            }

            // NO BLACK OVERLAY

            g2.dispose();
        }
    }
}