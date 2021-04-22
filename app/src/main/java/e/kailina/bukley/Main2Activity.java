package e.kailina.bukley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.login:
                Intent loginActivity=new Intent(Main2Activity.this,Login.class);
                startActivity(loginActivity);
                return true;
            case R.id.getInTouch:
                //perform any action;
                return true;
            case R.id.sellBook:
                //perform any action;
                return true;
            case R.id.Register:
                Intent registerActivity=new Intent(Main2Activity.this,Register.class);
                startActivity(registerActivity);
                return true;
            case R.id.Logout:
                //perform any action;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
