package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SikdanParsing {
	private final static String address = "https://dgucoop.dongguk.edu/store/store.php?w=4&l=1";
	
	public static void main(String[] args) throws Exception {
		Document doc = Jsoup.connect(address).header("User-Agent", "Mozilla/5.0").get();
		
		
		Elements contents = doc.select("table"); 
		
	
		
		
		int idx = 0; 
		int idx2 = 0;
		int idx3 = 0;
		int idx4 = 0;
		int idx5 = 0;
		int idx6 = 0;
		int idx7 = 0;
 		
		for(Element element : contents){ 
			if(0<idx && idx<9){
			
				System.out.println(element.text());
			}
			idx++;
		}
		
		System.out.println("\n");
		
		for(Element element : contents){ 
			if(8<idx2 && idx2<11){
			
				System.out.println(element.text());
			}
			idx2++;
		}
		
		System.out.println("\n");
		
		for(Element element : contents){ 
			if(10<idx3 && idx3<13){
			
				System.out.println(element.text());
			}
			idx3++;
		}
		
		System.out.println("\n");
		
		for(Element element : contents){ 
			if(12<idx4 && idx4<19){
			
				System.out.println(element.text());
			}
			idx4++;
		}
		
		System.out.println("\n");
		
		for(Element element : contents){ 
			if(18<idx5 && idx5<23){
			
				System.out.println(element.text());
			}
			idx5++;
		}
		
		System.out.println("\n");
		
		for(Element element : contents){ 
			if(22<idx6 && idx6<30){
			
				System.out.println(element.text());
			}
			idx6++;
		}
		
		System.out.println("\n");
		
		for(Element element : contents){ 
			if(33<idx7 && idx7<43){
			
				System.out.println(element.text());
			}
			idx7++;
		}
	}
}