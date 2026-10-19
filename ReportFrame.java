import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class ReportFrame extends JFrame {

    private final int userId;

    private JLabel incomeValue;
    private JLabel expenseValue;
    private JLabel balanceValue;

    public ReportFrame(
            int userId
    ) {

        this.userId = userId;

        setTitle(
                "Expense Tracker - Reports"
        );

        setSize(
                900,
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
                        244,
                        247,
                        252
                )
        );

        main.setBorder(
                new EmptyBorder(
                        35,
                        40,
                        35,
                        40
                )
        );

        // =================================================
        // TITLE
        // =================================================

        JLabel title =
                new JLabel(
                        "FINANCIAL REPORT"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(
                new Color(
                        120,
                        65,
                        190
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

        main.add(
                Box.createVerticalStrut(35)
        );

        // =================================================
        // CARDS
        // =================================================

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                20,
                                0
                        )
                );

        cards.setOpaque(false);

        incomeValue =
                createCard(
                        cards,
                        "TOTAL INCOME",
                        new Color(
                                32,
                                175,
                                105
                        )
                );

        expenseValue =
                createCard(
                        cards,
                        "TOTAL EXPENSE",
                        new Color(
                                225,
                                70,
                                80
                        )
                );

        balanceValue =
                createCard(
                        cards,
                        "CURRENT BALANCE",
                        new Color(
                                52,
                                115,
                                225
                        )
                );

        main.add(cards);

        main.add(
                Box.createVerticalStrut(40)
        );

        // =================================================
        // REFRESH
        // =================================================

        JButton refresh =
                new JButton(
                        "REFRESH REPORT"
                );

        refresh.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        refresh.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        JButton close =
                new JButton(
                        "CLOSE"
                );

        close.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        refresh.addActionListener(
                e -> loadReport()
        );

        close.addActionListener(
                e -> dispose()
        );

        main.add(refresh);

        main.add(
                Box.createVerticalStrut(12)
        );

        main.add(close);

        add(main);

        loadReport();

        setVisible(true);
    }

    private JLabel createCard(
            JPanel parent,
            String title,
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
                        20,
                        25,
                        20
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
                        15
                )
        );

        JLabel value =
                new JLabel(
                        "₹ 0.00"
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
                        27
                )
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(value);

        parent.add(card);

        return value;
    }

    private void loadReport() {

        String sql =
                "SELECT " +
                        "COALESCE(SUM(CASE " +
                        "WHEN type='INCOME' " +
                        "THEN amount ELSE 0 END),0) AS income, " +
                        "COALESCE(SUM(CASE " +
                        "WHEN type='EXPENSE' " +
                        "THEN amount ELSE 0 END),0) AS expense " +
                        "FROM transactions " +
                        "WHERE user_id=?";

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

            ResultSet rs =
                    statement.executeQuery();

            if (
                    rs.next()
            ) {

                double income =
                        rs.getDouble(
                                "income"
                        );

                double expense =
                        rs.getDouble(
                                "expense"
                        );

                double balance =
                        income - expense;

                DecimalFormat df =
                        new DecimalFormat(
                                "#,##0.00"
                        );

                incomeValue.setText(
                        "₹ "
                                + df.format(
                                income
                        )
                );

                expenseValue.setText(
                        "₹ "
                                + df.format(
                                expense
                        )
                );

                balanceValue.setText(
                        "₹ "
                                + df.format(
                                balance
                        )
                );
            }

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
}