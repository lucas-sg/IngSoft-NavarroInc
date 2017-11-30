package mercadoNavarro.db;

import java.sql.*;

public class DBPersistance {
    private static DBPersistance uniqueInstance = new DBPersistance();
    final String url = "jdbc:postgresql://localhost:9999/postgres";
    private Connection connection;

    public static DBPersistance getInstance() {
        return uniqueInstance;
    }

    private DBPersistance() {

    }

    public boolean disconnect() {
        try {
            connection.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean connect() {
        try {
            connection = DriverManager.getConnection(url, "u2017b-7", "navarro");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean insert(String insert) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("insert " + insert);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(String delete) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("delete " + delete);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean update(String update) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("update " + update);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public ResultSet query(String query) {
        if(connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(query);
                return statement.getResultSet();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
