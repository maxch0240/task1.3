import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        String url = "jdbc:mysql://localhost:3306/world";
        String username = "root";
        String password = "1234";
        Class.forName("com.mysql.jdbc.Driver");

        File file = new File("D:\\JAVA\\task1.1\\files\\2.txt");
        int allFields = count("D:\\JAVA\\task1.1\\files\\2.txt");
         System.out.println("file: " + file.getName());


        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int counter = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrSplit = line.split("\\|\\|");
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO strings VALUES (?, ?, ?, ?, ?);");
                preparedStatement.setString(1, arrSplit[0]);
                preparedStatement.setString(2, arrSplit[1]);
                preparedStatement.setString(3, arrSplit[2]);
                preparedStatement.setString(4, String.valueOf(Integer.parseInt(arrSplit[3])));
                preparedStatement.setString(5, String.valueOf(Double.parseDouble(arrSplit[4])));
                preparedStatement.executeUpdate();
                counter++;
                System.out.println("fields left: " + (allFields - counter));

            }
            reader.close();


        }

    }


    public static int count(String filename) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if (endsWithoutNewLine) {
                ++count;
            }
            return count;
        }
    }

}

