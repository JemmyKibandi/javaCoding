import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Person {
    // Instance variables
    String name;
    int age;

    // Method to print person's details
    void speak() {
        System.out.println("Hello, my name is " + name + " and my age is " + age);
    }
}

public class methodAPI {
    public static void main(String[] args) {
        // Fetch data from the API and create Person objects
        String urlString = "https://jsonplaceholder.typicode.com/users"; // Fake API URL
        try {
            // Fetch data from the API
            String jsonResponse = fetchData(urlString);

            // Parse the JSON response to create Person objects
            parseAndDisplayPersons(jsonResponse);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to fetch data from the API
    private static String fetchData(String urlString) throws IOException {
        StringBuilder response = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // Set the request method to GET
        conn.setRequestProperty("Accept", "application/json"); // Set the accept header

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("GET request failed: " + responseCode);
        }
        return response.toString();
    }

    // Method to parse JSON response and create Person objects
    private static void parseAndDisplayPersons(String jsonResponse) {
        // Assuming the JSON is an array of users
        String[] users = jsonResponse.split("},"); // Simple split for demonstration purposes

        for (String user : users) {
            user = user.replaceAll("[\\[\\]{}]", ""); // Clean up the JSON format
            String[] attributes = user.split(",");

            String name = "";
            int age = 0;

            // Extract name and age (for simplicity, assume specific attributes)
            for (String attribute : attributes) {
                if (attribute.contains("name")) {
                    name = attribute.split(":")[1].replaceAll("\"", "").trim();
                } else if (attribute.contains("age")) {
                    age = Integer.parseInt(attribute.split(":")[1].trim());
                }
            }

            // Create a new Person object
            Person person = new Person();
            person.name = name;
            person.age = age;
            person.speak(); // Call the speak method to display details
        }
    }
}