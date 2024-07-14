package com.springboot.blog.service.impl;

import static java.lang.String.format;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class TestJDBCStatements {


   private static Map<String, String> connectionDetails = Map.of("host", "localhost",
            "port", "3307",
            "database", "testdb",
            "username", "root",
            "password", "password");

    public static String connectionURL = format("jdbc:mysql://%s:%s/%s", connectionDetails.get("host"),
            connectionDetails.get("port"),
            connectionDetails.get("database"));
    @Test
    public void testJDBCConnectivity() {
        String host = "localhost";
        String port = "3307";
        String database = "testdb";
        StringBuilder url = new StringBuilder("jdbc:mysql://");
        url.append(host).append(":").append(port).append("/").append(database);
        String user = "root";
        String password = "password";
        String query = "SELECT * FROM product_test";
//        Class.forName("mysql.cj.jdbc.Driver"); // 1.(Optional) register the driver(JDBC 4.0 onwards this optional and
        // we just need to add the dependency in the classpath and then the jdbc driver manager automatically detects
        // and registers it.
        try (Connection conn = DriverManager.getConnection(url.toString(), user, password)) { // 2. Establish the connection
            Statement statement = conn.createStatement(); // 3. Create the statement
            try (ResultSet rs = statement.executeQuery(query)) { // 4. Execute the statement
                // get the metadata
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                List<String> columnNames = new ArrayList<>(); // 5. Process the results
                for (int i = 1; i <= columnCount; i++) {
                    String colName = metaData.getColumnName(i);
                    columnNames.add(colName);
                }
                printDBData(rs, columnNames);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 6. Close the connection. The connection gets closed automatically since we are using try with resources
        }
    }


    /**
     * Scrollable create statement example
     *
     */
    @Test
    public void testScrollableCreateStatement() {
        String host = "localhost";
        String port = "3307";
        String database = "testdb";
        StringBuilder url = new StringBuilder("jdbc:mysql://");
        url.append(host).append(":").append(port).append("/").append(database);
        String user = "root";
        String password = "password";
        String query = "SELECT * FROM product_test";
//        Class.forName("mysql.cj.jdbc.Driver"); // 1.(Optional) register the driver(JDBC 4.0 onwards this optional and
        // we just need to add the dependency in the classpath and then the jdbc driver manager automatically detects
        // and registers it.
        try (Connection conn = DriverManager.getConnection(url.toString(), user, password)) { // 2. Establish the connection
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); // 3. Create the statement
            try (ResultSet rs = statement.executeQuery(query)) { // 4. Execute the statement
                // get the metadata
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                List<String> columnNames = new ArrayList<>(); // 5. Process the results
                for (int i = 1; i <= columnCount; i++) {
                    String colName = metaData.getColumnName(i);
                    columnNames.add(colName);
                }
                rs.absolute(3);
                System.out.println("Name = " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 6. Close the connection. The connection gets closed automatically since we are using try with resources
        }
    }

    /** Example of insert statement
     *
     */
    @Test
    public void testInsertStatement() {
        String query = getInsertProductQuery(Map.of("id", 5,
                "name", "Product2",
                "sku", "SKU-0105",
                "effect_start", "2024-01-01",
                "effect_end", "2025-12-31",
                "product_number", "PC-0105",
                "tenant_id", "9"));
        createProductDetails(query);
    }

    public static void createProductDetails(String query) {
        try (Connection conn = DriverManager.getConnection(connectionURL, connectionDetails.get("username"), connectionDetails.get("password"))) {
            Statement statement = conn.createStatement();
            int rowsUpdated = statement.executeUpdate(query);
            if (rowsUpdated > 0) {
                System.out.println("Rows updated successfully:" + rowsUpdated);
                getProductDetails(conn);
            } else {
                System.out.println("Row not updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getProductDetails(Connection conn) throws SQLException {
        String query = "SELECT * FROM product_test";
        Statement statement = conn.createStatement(); // 3. Create the statement
        try (ResultSet rs = statement.executeQuery(query)) { // 4. Execute the statement
            // get the metadata
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<>(); // 5. Process the results
            for (int i = 1; i <= columnCount; i++) {
                String colName = metaData.getColumnName(i);
                columnNames.add(colName);
            }
            printDBData(rs, columnNames);
        }
    }


    /**
     * Generic insert product query statement
     *
     */
    public static String getInsertProductQuery(Map<String, Object> values) {
        StringBuilder insertQuery = new StringBuilder("INSERT INTO product_test (id, name, sku, effect_start, effect_end, product_number, tenant_id) VALUES");
        insertQuery.append("(");
        setIfOrDefault((key) -> values.get(key), "id", null, (v) -> insertQuery.append(v).append(","));
        setIfOrDefault((key) -> values.get(key), "name", null, (v) -> insertQuery.append("'").append(v).append("'").append(","));
        setIfOrDefault((key) -> values.get(key), "sku", null, (v) -> insertQuery.append("'").append(v).append("'").append(","));
        setIfOrDefault((key) -> values.get(key), "effect_start", null, (v) -> insertQuery.append("'").append(v).append("'").append(","));
        setIfOrDefault((key) -> values.get(key), "effect_end", null, (v) -> insertQuery.append("'").append(v).append("'").append(","));
        setIfOrDefault((key) -> values.get(key), "product_number", null, (v) -> insertQuery.append("'").append(v).append("'").append(","));
        setIfOrDefault((key) -> values.get(key), "tenant_id", null, (v) -> insertQuery.append("'").append(v).append("'"));
        insertQuery.append(")");
        return insertQuery.toString();
    }

    /**
     * Generic method to set the value if present or set the default value
     */
    public static <T,R> void setIfOrDefault(Function<T, R> extractor, T key, R defaultValue, Consumer<R> consumer) {
        if (extractor.apply(key) != null) {
            consumer.accept(extractor.apply(key));
        } else {
            consumer.accept(defaultValue);
        }
    }

    /**
     * Print the data from the result set
     *
     */
    private static void printDBData(ResultSet rs, List<String> colNames) throws SQLException {
        int row = 1;
        while (rs.next()) {
            System.out.println("Row: " + row);
            colNames.forEach(col -> {
                try {
                    System.out.println("ColName: " + col + ", Data: " + rs.getString(col));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("-------------------------");
            row++;
        }
    }

    @Test
    public void testPrepapredStatement() {
        updateProductNameById(1, "Test product1");
        updateProductNameById(2, "Test product2");
        updateProductNameById(3, "Test product3");
    }

    /**
     * Update product name by id
     *
     */
    public static void updateProductNameById(int id, String name) {
        String query = "UPDATE product_test SET name = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(connectionURL, connectionDetails.get("username"), connectionDetails.get("password"))) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Rows updated successfully:" + rowsUpdated);
                getProductDetails(conn);
            } else {
                System.out.println("Row not updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
