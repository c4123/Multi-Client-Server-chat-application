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
	
		
	
		String EmployeeRes = doc.select("tr").eq(2).text(); // �������Ĵ�
		String SangrokRes = doc.select("tr").eq(16).text(); // ��Ͽ��л��Ĵ�
		String SolnoodleRes = doc.select("tr").eq(44).text(); // �־ش���
		String GruturgiRes = doc.select("tr").eq(72).text(); // �׷��ͱ��л��Ĵ�
		String PanNoodleRes = doc.select("tr").eq(88).text(); // �Ҿش���
		String GardenCookRes = doc.select("tr").eq(97).text(); // ������
		String GisooksaRes = doc.select("tr").eq(126).text(); //�����л�(�����)�Ĵ�
		
		
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
		
		

		String EmployeeCorner = doc.select("tr").eq(3).text(); // �������Ĵ� �ڳ�
		String rEmployeeCorner = EmployeeCorner.substring(3,13);
		String SangrokCorner = doc.select("tr").eq(17).text(); // ��Ͽ��л��Ĵ� �ڳ�
		String SolnoodleCorner = doc.select("tr").eq(45).text(); // �־ش��� �ڳ�
		String GruturgiCorner = doc.select("tr").eq(73).text(); // �׷��ͱ��л��Ĵ� �ڳ�
		String PanNoodleCorner = doc.select("tr").eq(89).text(); // �Ҿش��� �ڳ�
		String GardenCookCorner = doc.select("tr").eq(98).text(); // ������ �ڳ�
		String GisooksaCorner = doc.select("tr").eq(127).text(); //�����л�(�����)�Ĵ� �ڳ�
		
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
		String name = doc.select("tr").eq(2).text(); // �������Ĵ�
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
		
		corners.get(0).insertFood(EResJ); //�������Ĵ� -> ���� �ڳ��� �޴���
		corners.get(1).insertFood(EresH); //�������Ĵ� -> �ѱ׸� �ڳ��� �޴���
		corners.get(2).insertFood(EresC); //�������Ĵ� -> ä�Ĵ� �ڳ��� �޴���
		
		
		for(int j=0;j<corners.size();j++)
		{
			System.out.println(corners.get(j).getFoods()); 
		}
		
		
		
		return employeeRes;
	}
}