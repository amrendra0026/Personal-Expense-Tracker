import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddIncomeFrame extends JFrame {

    private final int userId;

    private JTextField amountField;
    private JTextField categoryField;
    private JTextField dateField;
    private JTextField descriptionField;

    public AddIncomeFrame(
            int userId
    ) {

        this.userId = userId;

        setTitle(
                "Expense Tracker - Add Income"
        );

        setSize(
                600,
                650
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        JPanel main =
                new JPanel();

        main.setLayout(
                new BoxLayout(
                        main,
                        BoxLayout.Y_AXIS
                )
        );

        main.setBackground(
                new Color(
                        245,
                        247,
                        251
                )
        );

        main.setBorder(
                new EmptyBorder(
                        35,
                        55,
                        35,
                        55
                )
        );

        JLabel title =
                new JLabel(
                        "ADD INCOME"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(
                new Color(
                        30,
                        175,
                        105
                )
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        main.add(title);

        JLabel subtitle =
                new JLabel(
                        "Record your new income"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setForeground(
                new Color(
                        90,
                        110,
                        140
                )
        );

        main.add(
                Box.createVerticalStrut(8)
        );

        main.add(subtitle);

        main.add(
                Box.createVerticalStrut(30)
        );

        main.add(
                label("AMOUNT")
        );

        amountField =
                field();

        main.add(amountField);

        main.add(
                Box.createVerticalStrut(18)
        );

        main.add(
                label("SOURCE / CATEGORY")
        );

        categoryField =
                field();

        main.add(categoryField);

        main.add(
                Box.createVerticalStrut(18)
        );

        main.add(
                label("DATE (YYYY-MM-DD)")
        );

        dateField =
                field();

        dateField.setText(
                LocalDate.now()
                        .toString()
        );

        main.add(dateField);

        main.add(
                Box.createVerticalStrut(18)
        );

        main.add(
                label("DESCRIPTION")
        );

        descriptionField =
                field();

        main.add(descriptionField);

        main.add(
                Box.createVerticalStrut(30)
        );

        JButton save =
                new JButton(
                        "SAVE INCOME"
                );

        save.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        save.setBackground(
                new Color(
                        30,
                        175,
                        105
                )
        );

        save.setForeground(
                Color.WHITE
        );

        save.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        save.setFocusPainted(false);

        save.setPreferredSize(
                new Dimension(
                        360,
                        55
                )
        );

        main.add(save);

        main.add(
                Box.createVerticalStrut(15)
        );

        JButton close =
                new JButton(
                        "CLOSE"
                );

        close.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        main.add(close);

        save.addActionListener(
                e -> saveIncome()
        );

        close.addActionListener(
                e -> dispose()
        );

        add(main);

        setVisible(true);
    }

    private JLabel label(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        label.setForeground(
                new Color(
                        70,
                        85,
                        110
                )
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        return label;
    }

    private JTextField field() {

        JTextField field =
                new JTextField();

        field.setMaximumSize(
                new Dimension(
                        450,
                        50
                )
        );

        field.setPreferredSize(
                new Dimension(
                        450,
                        50
                )
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        return field;
    }

    private void saveIncome() {

        String amount =
                amountField
                        .getText()
                        .trim();

        String category =
                categoryField
                        .getText()
                        .trim();

        String date =
                dateField
                        .getText()
                        .trim();

        String description =
                descriptionField
                        .getText()
                        .trim();

        if (
                amount.isEmpty()
                        ||
                        category.isEmpty()
                        ||
                        date.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill Amount, Source and Date.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            double value =
                    Double.parseDouble(
                            amount
                    );

            if (
                    value <= 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String sql =
                    "INSERT INTO transactions "
                            +
                            "(user_id,type,category,amount,description,transaction_date) "
                            +
                            "VALUES (?,?,?,?,?,?)";

            try (
                    Connection connection =
                            DBConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(
                                    sql
                            )
            ) {

                statement.setInt(
                        1,
                        userId
                );

                statement.setString(
                        2,
                        "INCOME"
                );

                statement.setString(
                        3,
                        category
                );

                statement.setDouble(
                        4,
                        value
                );

                statement.setString(
                        5,
                        description
                );

                statement.setString(
                        6,
                        date
                );

                statement.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Income saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}