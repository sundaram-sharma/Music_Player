package en.sundaram.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashScreen extends AppCompatActivity {

    Handler handler = new Handler(); //object of handler class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //function will delay the execution of the code
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this, mainActivity.class); //intent from splash ac
                startActivity(intent);
                finish(); 
            }
        },3000); //delay of 3 seconds
    }
}