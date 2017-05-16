package aboullaite;
import java.sql.*;

public class DBHelper {
	   private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   private final String DB_URL = "jdbc:mysql://52.78.111.144:3306/unichat";

	   private final String USER = "jbkim";
	   private final String PASS = "1q2w3e4r!!";
	   
	   private Connection conn = null;
	   private Statement stmt = null;
	   
	   public DBHelper() {
		   try {
			Class.forName(JDBC_DRIVER);
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
		   //String insertStmt = "INSERT INTO user(id, passwd) VALUES('niutn@naver.com', '1q2w3e4r!!')";
		   String selectStmt = "Select * from user";
		   
		   //dbHelper.executeUpdate(insertStmt);
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
