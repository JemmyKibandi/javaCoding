import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class variousApis {

    public static void main(String[] args) {
        // URL for GET request
        String getUrlString = "https://jsonplaceholder.typicode.com/todos/4";
        String postUrlString = "https://jsonplaceholder.typicode.com/todos"; // Replace with your API URL

        // First, we will make a GET request
        String getResponse = fetchData(getUrlString);

        // Then, we will make a POST request to create a new todo item
        String jsonInputString = "{\"userId\": 1, \"title\": \"New Todo Item\", \"completed\": false}"; // JSON payload
        String postResponse = sendData(postUrlString, jsonInputString);

        // Modify the JSON input string for a second POST request (update the title)
        String jsonUpdateString = "{\"userId\": 1, \"title\": \"Updated Todo Item\", \"completed\": true}"; // Updated
                                                                                                            // JSON
                                                                                                            // payload
        String postUpdateResponse = sendData(postUrlString, jsonUpdateString);

        // Display the final data from all three operations
        System.out.println("\nFinal Data Displayed:");
        System.out.println("GET Response: " + getResponse);
        System.out.println("First POST Response: " + postResponse);
        System.out.println("Second POST Response: " + postUpdateResponse);
    }

    // Method to fetch data from API
    private static String fetchData(String urlString) {
        StringBuilder response = new StringBuilder();
        try {
            // Create a URL object
            URL url = new URL(urlString);
            // Open a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // Set the request method to GET
            conn.setRequestProperty("Accept", "application/json"); // Set the accept header

            // Check the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("GET request failed: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while fetching the data: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
        return response.toString();
    }

    // Method to send data to API
    private static String sendData(String urlString, String jsonInputString) {
        StringBuilder response = new StringBuilder();
        try {
            // Create a URL object
            URL url = new URL(urlString);
            // Open a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); // Set the request method to POST
            conn.setRequestProperty("Content-Type", "application/json; utf-8"); // Set the content type
            conn.setRequestProperty("Accept", "application/json"); // Set the accept header
            conn.setDoOutput(true); // Enable output for the connection

            // Write JSON input string to the output stream
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            try {
                wr.writeBytes(jsonInputString);
                wr.flush();
            } catch (IOException e) {
                // Handle the exception appropriately
            } finally {
                try {
                    if (wr != null) {
                        wr.close();
                    }
                } catch (IOException e) {
                    // Handle the exception during closing
                }
            }

            // Check the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) { // 201 Created
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("POST request failed: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while sending the data: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
        return response.toString();
    }
}