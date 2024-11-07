import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FertilizerManagement {

    public static void main(String[] args) {
        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/java_proj";
        String dbUser = "root"; // Replace with your database username
        String dbPassword = "SI41"; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            System.out.println("Database connected!");

            // Collect user input
            try (Scanner scanner = new Scanner(System.in)) {
                // Display crop options
                System.out.println("Select a crop:");
                String[] crops = {"Wheat", "Corn", "Rice", "Potato", "Tomato", "Cabbage", "Onion", "Carrot", "Lettuce", "Strawberry"};
                for (int i = 0; i < crops.length; i++) {
                    System.out.println((i + 1) + ". " + crops[i]);
                }

                int cropChoice = getValidChoice(scanner, crops.length);
                String cropName = crops[cropChoice - 1];

                // Display soil type options
                System.out.println("Select a soil type:");
                String[] soilTypes = {"Loamy", "Sandy", "Clay"};
                for (int i = 0; i < soilTypes.length; i++) {
                    System.out.println((i + 1) + ". " + soilTypes[i]);
                }

                int soilChoice = getValidChoice(scanner, soilTypes.length);
                String soilType = soilTypes[soilChoice - 1];

                // Collect moisture level and pH input
                System.out.print("Enter the moisture level (%): ");
                float moisture = scanner.nextFloat();

                System.out.print("Enter the pH level: ");
                float pH = scanner.nextFloat();

                // Query to find a matching fertilizer recommendation
                String query = "SELECT fertilizer_name, nutrients_needed FROM fertilizer_recommendations " +
                               "WHERE crop_name = ? AND soil_type = ? AND ? BETWEEN min_moisture AND max_moisture " +
                               "AND ? BETWEEN min_ph AND max_ph";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, cropName);
                    statement.setString(2, soilType);
                    statement.setFloat(3, moisture);
                    statement.setFloat(4, pH);

                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String fertilizerName = resultSet.getString("fertilizer_name");
                        String nutrientsNeeded = resultSet.getString("nutrients_needed");
                        System.out.println("Recommended Fertilizer: " + fertilizerName);
                        System.out.println("Nutrients Needed: " + nutrientsNeeded);
                    } else {
                        System.out.println("No specific fertilizer recommendation found for the provided input.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to get a valid user choice
    private static int getValidChoice(Scanner scanner, int max) {
        int choice = 0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter the number corresponding to your choice: ");
                choice = scanner.nextInt();
                if (choice < 1 || choice > max) {
                    System.out.println("Invalid choice, please try again.");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return choice;
    }
}
