package org.d2database.V2;
import org.d2database.V2.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class CoreDatabase {
    Connection connection;
    String connectionUrl;
    public CoreDatabase() {
        //-----------------------Load-Driver-And-Login------------------------
        this.loadDriver();
        this.setLoginData();
        //-------------------------Create-Connection--------------------------
        this.formConnectionUrl();
        this.connect();
    }
    //------------------------------Load-Driver-------------------------------
    public void loadDriver() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    //----------------------------Set-Login-Data------------------------------
    public void setLoginData() {
        String path = "C:\\Users\\olive\\OneDrive\\Documents\\" +
                "Key Folder\\CryptoTraderLogin.txt";
        File file = new File(path);
        FileDataRetriever username = new FileDataRetriever(0, path);
        FileDataRetriever password = new FileDataRetriever(1, path);
        Data.USERNAME.setData(username.getData());
        Data.PASSWORD.setData(password.getData());
    }
    //--------------------------Form-Connection-URL---------------------------
    public void formConnectionUrl() {
        String serverPrefix = "jdbc:sqlserver://";
        String server = "dota-data-hub.database.windows.net:1433;";
        String database = "database=DotaDataHubDatabase;";
        String user = "user={" + Data.USERNAME.getData() + "};password={" +
                                 Data.PASSWORD.getData() + "};";
        String connProperties = "encrypt=true;trustServerCertificate=false;" +
                "hostNameInCertificate=*.database.windows.net;" +
                "loginTimeout=30;authentication=ActiveDirectoryPassword" +
                ";autoReconnect=true";
        this.connectionUrl = serverPrefix + server + database + user +
                             connProperties;
    }
    //--------------------------------Connect---------------------------------
    public void connect() {
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
    //-------------------------------Reconnect--------------------------------
    public void reconnect() {
        try {
            this.connection = DriverManager.getConnection(this.connectionUrl);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //---------------------------Make-Query-Request---------------------------
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
