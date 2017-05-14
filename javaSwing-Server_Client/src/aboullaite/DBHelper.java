package aboullaite;
import java.sql.*;

public class DBHelper {
	   private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   private final String DB_URL = "jdbc:mysql://localhost/test";

	   private final String USER = "root";
	   private final String PASS = "1q2w3e4r!!";
	   
	   private Connection conn = null;
	   private Statement stmt = null;
	   
	   public DBHelper() {
		   try {
			Class.forName("com.mysql.jdbc.Driver");
		   } catch (ClassNotFoundException e) {
			   e.printStackTrace();
		   }
	   }
	   
	   private void open() {
		   try {
			   conn = DriverManager.getConnection(DB_URL, USER, PASS);
			   stmt = conn.createStatement();
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
	   }
	   
	   private void close() {
		   try {
			   stmt.close();
			   conn.close();
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
	   }
	   
	   public boolean executeUpdate(String sql) {
		   open();
		   try {
			   stmt.executeUpdate(sql);
		   } catch (SQLException e) {
			   e.printStackTrace();
			   return false;
		   }
		   close();
		   return true;
	   }
	   
	   public ResultSet executeQuery(String sql) {
		   open();
		   ResultSet rs = null;
		   try {
			   rs = stmt.executeQuery(sql);
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
		   return rs;
	   }
	   
	   public static void main(String[] args) {
		   DBHelper dbHelper = new DBHelper();
		   String insertStmt = "INSERT INTO USER(id) VALUES('vool@naver.com')";
		   String selectStmt = "Select * from USER";
		   
		   dbHelper.executeUpdate(insertStmt);
		   ResultSet rs = dbHelper.executeQuery(selectStmt);
		   try {
			   while(rs.next()) {
				   String id = rs.getString("id");
				   
				   System.out.println("ID: " + id);
			   }
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
	   }
	   
}
