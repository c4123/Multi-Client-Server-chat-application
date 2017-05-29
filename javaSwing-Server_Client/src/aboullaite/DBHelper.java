package aboullaite;
import java.sql.*;

public class DBHelper {
	   private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   private static final String DB_URL = "jdbc:mysql://52.78.111.144:3306/unichat";

	   private static final String USER = "jbkim";
	   private static final String PASS = "1q2w3e4r!!";
	   
	   private static Connection conn = null;
	   private static Statement stmt = null;
	   private static PreparedStatement ps = null;
	   private static ResultSet rs = null;
	   
	   private DBHelper() {

	   }
	   
	   public static Connection getConnection() {
		   
		   if(conn != null) {
			   return conn;
		   }
		   
		   try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		   } catch (ClassNotFoundException | SQLException e) {
			   e.printStackTrace();
		   }
		   
		   return conn;
	   }
	   
	   private static void close() {
		   try {
		   if(rs != null) rs.close();
		   if(stmt != null) stmt.close();			   
		   if(ps != null) ps.close();
		   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }
	   }
	   
	   public static boolean getUserCheck(String id, String passwd) {
		   boolean result = false;
		   
		   try {
			   ps = conn.prepareStatement("SELECT * FROM user WHERE id=? AND passwd = PASSWORD(?)");
			   ps.setString(1,  id.trim());
			   ps.setString(2,  passwd.trim());
			   
			   rs = ps.executeQuery();
			   
			   if(rs.next())
				   result = true;
		   } catch(SQLException e) {
			   e.printStackTrace();
		   } finally {
			   close();
		   }
		   return result;
	   }
	   
	   public static boolean insertUser(String id, String passwd) {
		   boolean result = false;
		   
		   try {
			   conn = getConnection();
			   ps = conn.prepareStatement("INSERT INTO user (id, passwd) values (?, PASSWORD(?))");
			   ps.setString(1, id);
			   ps.setString(2, passwd);
			   
			   int r = ps.executeUpdate();
			   
			   if(r>0) {
				   //���� ����
				   result = true;
			   } else {
				   //���� ����
			   }
		   } catch (SQLException e) {
			e.printStackTrace();
		   } finally {
			   close();
		   }
		   return result;
	   }
	   public static void main(String[] args) {
		   DBHelper.getConnection();
		   boolean rs = DBHelper.getUserCheck("root@localhost", "1234");
		   if(rs) {
			   System.out.println("����");
		   } else {
			   System.out.println("����");
		   }
	   }
}
