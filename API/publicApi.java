import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class publicApi {
    public static void main(String[] args) {
        String urlString = "https://jsonplaceholder.typicode.com/todos/1";
        // Replace with your API URL

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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("GET request failed: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while fetching the data: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}
