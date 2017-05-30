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
    ArrayList<String> array;
    private TextView txt_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parsing_url = "https://dgucoop.dongguk.edu/store/store.php?w=4&l=1";

        txt_text = (TextView)findViewById(R.id.txt_text);  //학식을 보여줄 텍스트뷰

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
        for (int i=0;i<array.size();i++){
            txt_text.append(array.get(i) + " "); //쓰레드 작업 끝나면 텍스트뷰에 가져온 데이터 뿌려줌
    }
    }

    public ArrayList<String>getData(String strURL)
    {
        Source source;
        get_data = "";
        array = new ArrayList();
        try {
            URL url = new URL(strURL);
            source = new Source(url); // data넣거나 web주소 입력해서 source instance를 만들면 파싱준비 끝.
            Element element = null;

            List<Element> list = source.getAllElements(HTMLElementName.TD); //TD 태그의 엘리먼트를 가져온다.

                for (int i = 0; i < list.size(); i++) {
                element = list.get(i);
                String sikdang = element.getAttributeValue("class"); // TD 태그의 속성값이 class를  찾는다
                //String menuname = element.getAttributeValue("align");

                if (sikdang != null ) {
                    if (sikdang.equalsIgnoreCase("menu_st") ||  (sikdang.equalsIgnoreCase("mft style2") )) { // 식당이름,식당내 코너이름,식단을 가져온다.
                        TextExtractor textExtractor = element.getTextExtractor(); // 해당 문자값을 가져온다.
                        get_data = textExtractor.toString(); // 가져온 값을 스트링으로 변환후
                        array.add(get_data); // Arraylist에 추가한다.
                    }
                }
            }
        } catch (Exception e) {

        }
            return array; //입력된 배열값 리턴
        }
    }


