import java.io.IOException;
import java.net.*;
import java.sql.*;

public class Practical_01_Server extends Practical_01_DataBase {

    private static final int PORT = 6666; // the port number the server listens on
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server listening on port " + PORT);

            // listen for incoming client connections
            while (true) {
                // accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // create new thread to handle search request
                Thread thread = new Thread(() -> {
                    try {
                        // handle search request
                        Connection connection = getConnection();
                        ResultSet resultSet = searchData(connection.createStatement(), "java");
                        String searchResult = getSearchResult(resultSet);
                        clientSocket.getOutputStream().write(searchResult.getBytes());
                        System.out.println("Search result sent to client.");
                    } catch (Exception e) {
                        System.out.println("Error handling search request: " + e.getMessage());
                    } finally {
                        // close client connection
                        try {
                            clientSocket.close();
                            System.out.println("Client disconnected.");
                        } catch (IOException e) {
                            System.out.println("Error closing client connection: " + e.getMessage());
                        }
                    }
                });
                thread.start();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
