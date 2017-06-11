package aboullaite;
import java.sql.*;
import java.util.HashMap;

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
	   
	   public static boolean getIdCheck(String id) {
		   boolean result = false;
		   
		   try {
			   ps = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			   ps.setString(1,  id.trim());
			   
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

	   public static String[][] getQuiz(){
		   String[][] quiz =null;
		   try {
			   ps = conn.prepareStatement("SELECT question, answer FROM quiz");
			   rs = ps.executeQuery();
			   
			   rs.last();
			   int size = rs.getRow();
			   rs.beforeFirst();
			   
			   quiz = new String[size][2]; 
		
			 for(int i=0;i<size;i++){
				 rs.next();
				 quiz[i][0] = rs.getString(1);
				 quiz[i][1] = rs.getString(2);
				 
			 }
		
			   
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   } finally {
			   close();
		   }
		   return quiz;
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
				   //삽입 성공
				   result = true;
			   } else {
				   //삽입 실패
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
			   System.out.println("아이디 비번 일치");
		   } else {
			   System.out.println("아이디 비번 불일치");
		   }
		   
		   rs = DBHelper.getIdCheck("root@localhost");
		   if(rs) {
			   System.out.println("아이디 일치");
		   } else {
			   System.out.println("아이디 불일치");
		   }
	   }
}
