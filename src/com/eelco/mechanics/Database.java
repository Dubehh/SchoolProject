package com.eelco.mechanics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	
	/**
	 *  @author Eelco - 2015
	 *  (Maakt gebruik van SQL driver lib, zie google drive)
	 */
	public static final String driver = "com.mysql.jdbc.Driver";
	private Statement statement;
	private Connection connection;
	private String username, password, host, database;
	private int port;
	private static Database _instance = null;
	
	
	/**
	 * @param username Inlognaam
	 * @param password Wachtwoord
	 * @param database Database naam
	 * @param port Port
	 * @param host URL van de database
	 */
	private Database(String username, String password, String database, int port, String host) throws SQLException, ClassNotFoundException{
		this.password = password;
		this.username = username;
		this.database = database;
		this.port = port;
		this.host = host;
		/* Benaderen van de driver class*/
		Class.forName(driver);
		connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, username, password);
		statement = connection.createStatement();
	}
	
	/**
	 * Vraagt database object; zodat je niet constant een nieuwe hoeft te maken. (scheelt cache geheugen)
	 * @return NullPointerException als het object nog niet aangemaakt is, anders geeft hij het object terug.
	 */
	public static Database getInstance(){
		if(_instance == null)
			try {
				_instance = new Database("root", "usbw", "kbs_school", 3307, "localhost");
			} catch (Exception e) {}
		return _instance;
	}
	/**
	 * @return Geeft een stabiele connectie terug
	 */
	public Connection getConnection(){
		return connection;
	}
	
	/**
	 * @return Geeft connectie statement terug.
	 */
	public Statement getStatement(){
		return statement;
	}
	
	/**
	 * @param option DatabaseOption die je terug wilt hebben.
	 * @return Waarde in Object type; je moet (veilig) casten :)
	 */
	public Object getDatabaseOption(DatabaseOption option){
		switch(option){
		case DATABASE_NAME: return database;
		case HOST: return host;
		case PASSWORD: return password;
		case PORT: return port;
		case USERNAME: return username;
		default: return null;
		}
	}
	/* Dit scheelt een hoop getters.*/
	public enum DatabaseOption{ USERNAME, PASSWORD, HOST, DATABASE_NAME, PORT}
	
	/**
	 * vraag data uit de database
	 * @param value Select Statement
	 * @param path From Statement
	 * @param where Where Statement OF 'NULL'
	 * @return ResultSet object
	 */
	public ResultSet getData(String value, String path, String where){
		String sql = "SELECT "+value+" FROM "+path+(where == null ? ";": "WHERE "+where+";");
		try{
			return getStatement().executeQuery(addSlashes(sql));
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Object getResult(String value, String path, String where){
		ResultSet set = getData(value, path, where);
		try {
			if(set.next()) return set.getString(value);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Addslashes voor SQL injecties
	 * @param s String dat je invoert
	 * @return Zelfde string met beveiliging
	 */
	private String addSlashes(String s) {
        s = s.replaceAll("\\\\", "\\\\\\\\");
        s = s.replaceAll("\\n", "\\\\n");
        s = s.replaceAll("\\r", "\\\\r");
        s = s.replaceAll("\\00", "\\\\0");
        s = s.replaceAll("'", "\\\\'");
        return s;
    }
	
}
