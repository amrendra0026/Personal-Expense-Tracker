import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionFrame extends JFrame {

    private final int userId;

    private JTable table;

    public TransactionFrame(
            int userId
    ) {

        this.userId = userId;

        setTitle(
                "Expense Tracker - Transactions"
        );

        setSize(
                1000,
                650
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );

        // =================================================
        // TITLE
        // =================================================

        JLabel title =
                new JLabel(
                        "TRANSACTIONS"
                );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        10,
                        25
                )
        );

        title.setForeground(
                new Color(
                        45,
                        100,
                        210
                )
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        add(
                title,
                BorderLayout.NORTH
        );

        // =================================================
        // TABLE
        // =================================================

        String[] columns = {
                "ID",
                "TYPE",
                "CATEGORY",
                "AMOUNT",
                "DESCRIPTION",
                "DATE"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };

        table =
                new JTable(model);

        table.setRowHeight(32);

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

        add(
                new JScrollPane(
                        table
                ),
                BorderLayout.CENTER
        );

        // =================================================
        // BOTTOM
        // =================================================

        JPanel bottom =
                new JPanel();

        JButton refresh =
                new JButton(
                        "REFRESH"
                );

        JButton close =
                new JButton(
                        "CLOSE"
                );

        bottom.add(refresh);
        bottom.add(close);

        add(
                bottom,
                BorderLayout.SOUTH
        );

        refresh.addActionListener(
                e -> loadTransactions()
        );

        close.addActionListener(
                e -> dispose()
        );

        loadTransactions();

        setVisible(true);
    }

    private void loadTransactions() {

        DefaultTableModel model =
                (DefaultTableModel)
                        table.getModel();

        model.setRowCount(0);

        String sql =
                "SELECT id,type,category,amount,"
                        +
                        "description,transaction_date "
                        +
                        "FROM transactions "
                        +
                        "WHERE user_id=? "
                        +
                        "ORDER BY id DESC";

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

            while (
                    rs.next()
            ) {

                model.addRow(
                        new Object[]{
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("category"),
                                "₹ "
                                        + rs.getDouble(
                                        "amount"
                                ),
                                rs.getString(
                                        "description"
                                ),
                                rs.getDate(
                                        "transaction_date"
                                )
                        }
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