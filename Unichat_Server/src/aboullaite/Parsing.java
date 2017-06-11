package aboullaite;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class Parsing{
	private final static String address = "https://dgucoop.dongguk.edu/store/store.php?w=4&l=2";
	private static Document doc;
	private ArrayList CornerList;
	private Elements table;
	private Elements tr;
	
	public Parsing() throws IOException {
		doc = Jsoup.connect(address).header("User-Agent", "Mozilla/5.0").get();
		CornerList = new ArrayList();
		table = doc.select("table");
		tr = doc.select("tr");
	}
	
	public static Res getEmployeeData() {
		Res employeeRes = new Res();
		String name = doc.select("tr").eq(2).text(); // 교직원식당한거 저장
		employeeRes.setResName(name);
		String EmployeeCorner = doc.select("tr").eq(3).text(); //교직원식당 코너이름 가져온거 저장
		String[] cornerNames = EmployeeCorner.split(" ");
		String EResJ = doc.select("tr").eq(5).text();
		String EresH = doc.select("tr").eq(7).text();
		String EresC = doc.select("tr").eq(9).text();
		
		for(int i=1;i<4;i++)
		{
			Corner corner = new Corner(cornerNames[i]);
			employeeRes.insertCorner(corner);
		}
		
		ArrayList<Corner> corners = employeeRes.getCorners();
		
		corners.get(0).insertFood(EResJ); //교직원식당 -> 집밥 코너의 메뉴들
		corners.get(1).insertFood(EresH); //교직원식당 -> 한그릇 코너의 메뉴들
		corners.get(2).insertFood(EresC); //교직원식당 -> 채식당 코너의 메뉴들
		
		return employeeRes;
	}
}