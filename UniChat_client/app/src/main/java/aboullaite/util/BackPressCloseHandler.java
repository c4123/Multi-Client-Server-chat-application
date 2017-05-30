package aboullaite.util;

/**
 * Created by hansb on 2017-05-29.
 */
import android.app.Activity;
import android.widget.Toast;

/**
 * Created by han sb on 2017-02-21.
 */

public class BackPressCloseHandler {
    private long backKeyPressedTime =0;
    private Toast toast;

    private Activity activity;
    public BackPressCloseHandler(Activity activity){
        this.activity = activity;
    }

    public boolean onBackPressed(){
        if(System.currentTimeMillis()>backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return false;
        }
       // else(System.currentTimeMillis() <= backKeyPressedTime+2000){
        else{
            activity.finish();
            toast.cancel();
            return true;
        }
    }
    public void showGuide(){
        toast = Toast.makeText(activity," \'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT);
        toast.show();
    }
}