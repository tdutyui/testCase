package database;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Database {
    public static void main(String[] args) throws IOException, SQLException {
        File file = new File("data/prices.csv");
        String url = "jdbc:postgresql://localhost:5432/OilPrice";
        Connection connection = DriverManager.getConnection(url, "postgres", "dekebikil");

        String line = "";
        int count = 0;
        double price = 0;
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null) {
            if (count == 0) {
                count++;
                continue;
            }
            String parsedLine = line.replaceAll("\"", "");
            String[] month = parsedLine.split(",");
            LocalDate startDate = formatDate(month[0]);
            LocalDate endDate = formatDate(month[1]);

            if (month.length>3) {
                price = Double.parseDouble(month[2]+"."+month[3]);
            }
            else {
                price = Double.parseDouble(month[2]);
            }

            connection.createStatement();
            String sql = "insert into oilprice (start_date, end_date, [price) values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, startDate);
            statement.setObject(2, endDate);
            statement.setObject(3, price);
            statement.executeUpdate();
        }
    }

    public static LocalDate formatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MMM.yy", Locale.getDefault());
        LocalDate formatted = null;

        if (!date.contains("май")) {
            formatted = LocalDate.parse(date, formatter);
        }
        else {
            String fixed = date.replace("май", "мая");
            formatted = LocalDate.parse(fixed, formatter);
        }
        return formatted;
    }
}
