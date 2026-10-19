EXPENSE TRACKER OFFLINE - SEPARATE COPY

IMPORTANT:
- This is a separate offline source copy. The original MySQL project/database are not changed.
- The app uses an embedded H2 database; MySQL is not needed on the user's laptop.
- Each Windows user profile stores its own data in: %USERPROFILE%\.expense-tracker
- Database file: %USERPROFILE%\.expense-tracker\expense_tracker.mv.db
- Profile photos: %USERPROFILE%\.expense-tracker\profile_photos
- Data does NOT sync between different laptops. Each laptop has its own accounts and transactions.

BUILD IN INTELLIJ IDEA:
1. Open this folder as a Maven project.
2. Use JDK 21.
3. Allow Maven to download dependencies (internet is needed for the first build).
4. Run Main.java to test.
5. Build > Build Project, then Maven > Lifecycle > package.
6. The shaded runnable JAR will be in target/expense-tracker-offline-1.0.0.jar

This generated source package has not yet been runtime-tested on Windows. Test registration,
login, transactions, report pages and image loading before distributing it.
