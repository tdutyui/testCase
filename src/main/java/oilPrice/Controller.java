package oilPrice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@RestController
public class Controller {

    private String url = "jdbc:postgresql://localhost:5432/OilPrice";
    private Connection connection = DriverManager.getConnection(url, "postgres", "dekebikil");

    public Controller() throws SQLException {
    }

    @RequestMapping(path = "/data")
    @GetMapping
    public List<Map<String, Object>> allData() throws SQLException {
        List<Map<String, Object>> data = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from oilprice");
        ResultSet rs = statement.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
                row.put(rsmd.getColumnLabel(i), rs.getObject(i));
                data.add(row);
            }
        return data;
    }

    @RequestMapping(path = "/data/{date}")
    @GetMapping
    public double priceOnDate(@PathVariable Date date) throws SQLException {
        double price = 0;
        PreparedStatement statement = connection.prepareStatement("select price from oilprice where end_date ='"+date+"'");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            price = rs.getDouble(1);
        }
        return price;
    }

    @RequestMapping(path = "/data/{start}/{end}")
    @GetMapping
    public double priceOnTimeInterval(@PathVariable Date start, @PathVariable Date end) throws SQLException {
        double avgPrice = 0;
        PreparedStatement statement = connection.prepareStatement("select avg(price) from oilprice where end_date between '"+start+"' and '"+end+"'");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            avgPrice = rs.getDouble(1);
        }
        return avgPrice;
    }

    @RequestMapping(path = "/data/price/{start}/{end}")
    @GetMapping
    public HashMap<String, Double> minAndMaxPrice(@PathVariable Date start, @PathVariable Date end) throws SQLException {
        double minPrice = 0;
        double maxPrice = 0;
        HashMap<String, Double> minAndMax = new HashMap<String, Double>();

        PreparedStatement statement = connection.prepareStatement("select min(price), max(price) from oilprice where end_date between '"+start+"' and '"+end+"'");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            minPrice = rs.getDouble(1);
            maxPrice = rs.getDouble(2);
        }
        minAndMax.put("min",minPrice);
        minAndMax.put("max",maxPrice);
        return minAndMax;
    }
}
