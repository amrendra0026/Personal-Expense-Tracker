import javax.swing.JFrame;

public class AppNavigation {

    // =====================================================
    // OPEN DASHBOARD
    // =====================================================

    public static void openDashboard(
            int userId,
            String name,
            String email,
            String phone,
            String photo
    ) {

        new DashboardFrame(
                userId,
                name,
                email,
                phone,
                photo
        );
    }

    // =====================================================
    // OPEN EXPENSE
    // =====================================================

    public static void openExpense(
            int userId
    ) {

        new AddExpenseFrame(userId);
    }

    // =====================================================
    // OPEN INCOME
    // =====================================================

    public static void openIncome(
            int userId
    ) {

        new AddIncomeFrame(userId);
    }

    // =====================================================
    // OPEN TRANSACTIONS
    // =====================================================

    public static void openTransactions(
            int userId
    ) {

        new TransactionFrame(userId);
    }

    // =====================================================
    // OPEN REPORT
    // =====================================================

    public static void openReports(
            int userId
    ) {

        new ReportFrame(userId);
    }

    // =====================================================
    // CLOSE CURRENT WINDOW
    // =====================================================

    public static void close(
            JFrame frame
    ) {

        if (frame != null) {

            frame.dispose();
        }
    }
}