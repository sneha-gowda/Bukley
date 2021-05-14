package e.kailina.bukley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences1;
    final static int timer=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isOnline()){
            Toast.makeText(MainActivity.this,"Please enable Internet",Toast.LENGTH_SHORT).show();
        }

        sharedPreferences1 = getSharedPreferences("onInstalling", MODE_PRIVATE);
        boolean checkFor1stTime = sharedPreferences1.getBoolean("onInstallation", true);
        if (checkFor1stTime) {
            ShowIntroductionActivity();
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent onInstalling = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(onInstalling);
                    finish();

                }
            }, timer);
        }
    }
    void ShowIntroductionActivity(){
        sharedPreferences1=getSharedPreferences("onInstalling",MODE_PRIVATE);
        SharedPreferences.Editor edit1=sharedPreferences1.edit();
        edit1.putBoolean("onInstallation",false).commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent onInstalling=new Intent(MainActivity.this,onInstalling.class);
                startActivity(onInstalling);
                finish();
            }
        },timer);
    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
