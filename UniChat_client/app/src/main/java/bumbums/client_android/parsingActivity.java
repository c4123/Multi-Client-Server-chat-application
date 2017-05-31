package bumbums.client_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class parsingActivity extends AppCompatActivity {


    private static Thread thread = null;
    String parsing_url; //파싱해올 학식 페이지 URL
    String get_data; // 파싱해서 가져온 데이터 저장하는 변수
    String get_cornerdata;
    String get_menudata;
    ArrayList<String> array;
    ArrayList<String> arraycorner;
    ArrayList<String> arraymenu;
    private TextView txt_resname;
    private TextView txt_cornername;
    //private TextView txt_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parsing);

        parsing_url = "https://dgucoop.dongguk.edu/store/store.php?w=4&l=1";

        txt_resname = (TextView)findViewById(R.id.txt_resname);  //학식을 보여줄 텍스트뷰
        txt_cornername = (TextView)findViewById(R.id.txt_cornername);
        //txt_menu = (TextView)findViewById(R.id.txt_menu);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                getData(parsing_url);
            }
        };

        thread = new Thread(task);
        thread.start(); // 반드시 쓰레드를 해줘야한다.

        try{
            thread.join(); // 쓰레드 작업 끝날때까지 다른 작업들은 대기
        } catch (Exception e){

        }
        /*for (int i=0;i<array.size();i++){
            txt_resname.append(array.get(i) + "\n"); //쓰레드 작업 끝나면 텍스트뷰에 가져온 데이터 뿌려줌
        }
        */
        /*for (int i=0;i<arraycorner.size();i++){
            txt_cornername.append(arraycorner.get(i) + "\n"); //쓰레드 작업 끝나면 텍스트뷰에 가져온 데이터 뿌려줌
        }
        */
        for (int i=0;i<arraymenu.size();i++){
            txt_cornername.append(arraymenu.get(i) + "\n"); //쓰레드 작업 끝나면 텍스트뷰에 가져온 데이터 뿌려줌
        }
    }

    public ArrayList<String>getData(String strURL)
    {
        Source source;
        get_data = "";
        get_cornerdata = "";
        get_menudata = "";
        array = new ArrayList();
        arraycorner = new ArrayList();
        arraymenu = new ArrayList();
        try {
            URL url = new URL(strURL);
            source = new Source(url); // data넣거나 web주소 입력해서 객체를 만들면 파싱준비 끝.
            Element element = null;

            List<Element> list = source.getAllElements(HTMLElementName.TD); // 식당이름 가져오는 객체

            for (int i = 0; i < list.size(); i++)
            {
                element = list.get(i);
                String sikdang = element.getAttributeValue("class"); // TD 태그의 속성값이 class를  찾는다
                String menuname = element.getAttributeValue("align");

                if (sikdang != null )
                {
                    if (sikdang.equalsIgnoreCase("menu_st")) //||   )) // 식당이름을 가져온다.
                    {
                        TextExtractor textExtractor = element.getTextExtractor(); // 해당 문자값을 가져온다.
                        get_data = textExtractor.toString(); // 가져온 값을 스트링으로 변환후
                        array.add(get_data); // Arraylist에 추가한다.
                    }
                }

                if (sikdang != null )
                {
                    if (sikdang.equalsIgnoreCase("mft style2")) //||   )) // 식당내 코너이름을 가져온다.
                    {
                        TextExtractor textCornerName = element.getTextExtractor(); // 해당 문자값을 가져온다.
                        get_cornerdata = textCornerName.toString(); // 가져온 값을 스트링으로 변환후
                        arraycorner.add(get_cornerdata); // Arraylist에 추가한다.
                    }
                }

                if (sikdang != null )
                {
                    if (sikdang.equalsIgnoreCase("ft2")) //||   )) // 식당내 메뉴를 가져온다.
                    {
                        TextExtractor textMenu = element.getTextExtractor(); // 해당 문자값을 가져온다.
                        get_menudata = textMenu.toString(); // 가져온 값을 스트링으로 변환후
                        arraymenu.add(get_menudata); // Arraylist에 추가한다.
                    }
                }

            }
        } catch (Exception e) {

        }
        return array; //입력된 배열값 리턴
    }
}

