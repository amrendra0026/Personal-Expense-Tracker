import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AppPaths {

    public static File userDataDirectory() {
        File folder = new File(
                System.getProperty("user.home"),
                "ExpenseTrackerData"
        );

        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder;
    }

    public static File profilePhotosDirectory() {
        File folder = new File(
                userDataDirectory(),
                "profile_photos"
        );

        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder;
    }

    public static String assetPath(String fileName) {

        File assetFolder = new File(
                userDataDirectory(),
                "assets"
        );

        if (!assetFolder.exists()) {
            assetFolder.mkdirs();
        }

        File outputFile = new File(assetFolder, fileName);

        // Already extracted: reuse the existing image.
        if (outputFile.exists()) {
            return outputFile.getAbsolutePath();
        }

        // Extract image embedded inside the JAR.
        try (InputStream input =
                     AppPaths.class.getResourceAsStream(
                             "/assets/" + fileName
                     )) {

            if (input != null) {
                Files.copy(
                        input,
                        outputFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );

                return outputFile.getAbsolutePath();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.err.println(
                "Asset not found: " + fileName
        );

        return outputFile.getAbsolutePath();
    }
}
