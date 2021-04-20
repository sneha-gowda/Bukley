package e.kailina.bukley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences1=getSharedPreferences("onInstalling",MODE_PRIVATE);
        boolean checkFor1stTime=sharedPreferences1.getBoolean("onInstallation",true);
        if(checkFor1stTime){
            ShowIntroductionActivity();
        }

    }
    void ShowIntroductionActivity(){
        Intent onInstalling=new Intent(MainActivity.this,onInstalling.class);
        startActivity(onInstalling);
        finish();
    }
}
