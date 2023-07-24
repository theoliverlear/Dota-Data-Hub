package org.d2database.V2.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CoreDatabase {
    Connection connection;
    String connectionUrl;
    public CoreDatabase() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String path = "C:\\Users\\olive\\OneDrive\\Documents\\" +
                "Key Folder\\CryptoTraderLogin.txt";
        File file = new File(path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        String username = "";
        String password = "";
        if (scanner != null) {
            while (scanner.hasNext()) {
                username = scanner.next();
                password = scanner.next();
            }
        }
        String serverPrefix = "jdbc:sqlserver://";
        String server = "dota-data-hub.database.windows.net:1433;";
        String database = "database=DotaDataHubDatabase;";
        String user = "user={" + username + "};password={" + password + "};";
        String ending = "encrypt=true;trustServerCertificate=false;" +
                "hostNameInCertificate=*.database.windows.net;" +
                "loginTimeout=30;authentication=ActiveDirectoryPassword" +
                ";autoReconnect=true";
        this.connectionUrl = serverPrefix + server + database + user + ending;
        try {
            this.connection = DriverManager.getConnection(this.connectionUrl);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (this.connection != null) {
            System.out.println("Connected to database!");
        }
        else {
            System.out.println("Failed to connect to database!");
        }
    }
    public void reconnect() {
        try {
            this.connection = DriverManager.getConnection(this.connectionUrl);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void commandQuery(String command) {
        try {
            try (PreparedStatement statement = this.getConnection().prepareStatement(command)) {
                statement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.reconnect();
        }
    }
    //--------------------------Getters-And-Setters---------------------------
    public Connection getConnection() {
        return this.connection;
    }
}
