import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddExpenseFrame extends JFrame {

    private final int userId;

    private JTextField amountField;
    private JTextField categoryField;
    private JTextField dateField;
    private JTextField descriptionField;

    public AddExpenseFrame(
            int userId
    ) {

        this.userId = userId;

        setTitle(
                "Expense Tracker - Add Expense"
        );

        setSize(
                600,
                650
        );

        setLocationRelativeTo(null);

        // IMPORTANT
        // Only this window closes.
        // Application does NOT close.
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

        // =================================================
        // TITLE
        // =================================================

        JLabel title =
                new JLabel(
                        "ADD EXPENSE"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(
                new Color(
                        220,
                        70,
                        80
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
                        "Record your new expense"
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

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        main.add(
                Box.createVerticalStrut(8)
        );

        main.add(subtitle);

        main.add(
                Box.createVerticalStrut(30)
        );

        // =================================================
        // AMOUNT
        // =================================================

        main.add(
                label("AMOUNT")
        );

        amountField =
                field();

        main.add(amountField);

        main.add(
                Box.createVerticalStrut(18)
        );

        // =================================================
        // CATEGORY
        // =================================================

        main.add(
                label("CATEGORY")
        );

        categoryField =
                field();

        main.add(categoryField);

        main.add(
                Box.createVerticalStrut(18)
        );

        // =================================================
        // DATE
        // =================================================

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

        // =================================================
        // DESCRIPTION
        // =================================================

        main.add(
                label("DESCRIPTION")
        );

        descriptionField =
                field();

        main.add(descriptionField);

        main.add(
                Box.createVerticalStrut(30)
        );

        // =================================================
        // SAVE
        // =================================================

        JButton save =
                new JButton(
                        "SAVE EXPENSE"
                );

        save.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        save.setBackground(
                new Color(
                        225,
                        70,
                        80
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

        // =================================================
        // CLOSE
        // =================================================

        JButton close =
                new JButton(
                        "CLOSE"
                );

        close.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        close.setFocusPainted(false);

        main.add(close);

        // =================================================
        // SAVE ACTION
        // =================================================

        save.addActionListener(
                e -> saveExpense()
        );

        // =================================================
        // CLOSE ACTION
        // =================================================

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

    private void saveExpense() {

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
                    "Please fill Amount, Category and Date.",
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
                        "EXPENSE"
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
                    "Expense saved successfully!",
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