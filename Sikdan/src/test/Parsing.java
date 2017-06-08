package test;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class Parsing{
	private final static String address = "https://dgucoop.dongguk.edu/store/store.php?w=4&l=1";
	
	public static void main(String[] args) throws Exception {
		
		Document doc = Jsoup.connect(address).header("User-Agent", "Mozilla/5.0").get();
		ArrayList CornerList = new ArrayList();
		Elements table = doc.select("table");
		Elements tr = doc.select("tr");
	
		
	
		String EmployeeRes = doc.select("tr").eq(2).text(); // 교직원식당
		String SangrokRes = doc.select("tr").eq(16).text(); // 상록원학생식당
		String SolnoodleRes = doc.select("tr").eq(44).text(); // 솔앤누들
		String GruturgiRes = doc.select("tr").eq(72).text(); // 그루터기학생식당
		String PanNoodleRes = doc.select("tr").eq(88).text(); // 팬앤누들
		String GardenCookRes = doc.select("tr").eq(97).text(); // 가든쿡
		String GisooksaRes = doc.select("tr").eq(126).text(); //남산학사(기숙사)식당
		
		
		int resSize = 7;
		Res restaurants[] = new Res[7];
		//restaurants[0]=getEmployeeRes();
		for(int i = 0; i < resSize; i++) {
			restaurants[i] = new Res();
		}
		
		restaurants[0].setResName(EmployeeRes);
		restaurants[1].setResName(SangrokRes);
		restaurants[2].setResName(SolnoodleRes);
		restaurants[3].setResName(GruturgiRes);
		restaurants[4].setResName(PanNoodleRes);
		restaurants[5].setResName(GardenCookRes);
		restaurants[6].setResName(GisooksaRes);
		
		

		String EmployeeCorner = doc.select("tr").eq(3).text(); // 교직원식당 코너
		String rEmployeeCorner = EmployeeCorner.substring(3,13);
		String SangrokCorner = doc.select("tr").eq(17).text(); // 상록원학생식당 코너
		String SolnoodleCorner = doc.select("tr").eq(45).text(); // 솔앤누들 코너
		String GruturgiCorner = doc.select("tr").eq(73).text(); // 그루터기학생식당 코너
		String PanNoodleCorner = doc.select("tr").eq(89).text(); // 팬앤누들 코너
		String GardenCookCorner = doc.select("tr").eq(98).text(); // 가든쿡 코너
		String GisooksaCorner = doc.select("tr").eq(127).text(); //남산학사(기숙사)식당 코너
		
		String EResJ = doc.select("tr").eq(5).text();
		String EresH = doc.select("tr").eq(7).text();
		String EresC = doc.select("tr").eq(9).text(); 
		
		Res res =getEmployeeData(doc);
		/*
		
		Corner Cname[] = new Corner[conerSize];
		for(int i = 0; i < conerSize; i++) {
			Cname[i] = new Corner();
		}
		Cname[0].setCornerName(EmployeeCorner);
		Cname[1].setCornerName(SangrokCorner);
		Cname[2].setCornerName(SolnoodleCorner);
		Cname[3].setCornerName(GruturgiCorner);
		Cname[4].setCornerName(PanNoodleCorner);
		Cname[5].setCornerName(GardenCookCorner);
		Cname[6].setCornerName(GisooksaCorner);
		
		System.out.println(EmployeeRes);
		System.out.println(SangrokRes);
		System.out.println(SolnoodleRes);
		System.out.println(GruturgiRes);
		System.out.println(PanNoodleRes);
		System.out.println(GardenCookRes);
		System.out.println(GisooksaRes);
		
		System.out.println(EmployeeCorner);
		System.out.println(SangrokCorner);
		System.out.println(SolnoodleCorner);
		System.out.println(GruturgiCorner);
		System.out.println(PanNoodleCorner);
		System.out.println(GardenCookCorner);
		System.out.println(GisooksaCorner);
		
		System.out.println(data);
		*/
				
	}
	public static Res getEmployeeData(Document doc){
		Res employeeRes = new Res();
		String name = doc.select("tr").eq(2).text(); // 교직원식당
		employeeRes.setResName(name);
		String EmployeeCorner = doc.select("tr").eq(3).text();
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

		for(int j=0;j<corners.size();j++)
		{
			System.out.println(corners.get(j).getCornerName());
		}
		
		corners.get(0).insertFood(EResJ); //교직원식당 -> 집밥 코너의 메뉴들
		corners.get(1).insertFood(EresH); //교직원식당 -> 한그릇 코너의 메뉴들
		corners.get(2).insertFood(EresC); //교직원식당 -> 채식당 코너의 메뉴들
		
		
		for(int j=0;j<corners.size();j++)
		{
			System.out.println(corners.get(j).getFoods()); 
		}
		
		
		
		return employeeRes;
	}
}