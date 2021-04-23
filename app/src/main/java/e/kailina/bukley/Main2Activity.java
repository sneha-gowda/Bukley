package e.kailina.bukley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;


public class Main2Activity extends AppCompatActivity {
    Boolean value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
         value=intent.getBooleanExtra("finishedRegistration",false);
         if(value){
             System.out.println("came");
             invalidateOptionsMenu();
         }

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
            case R.id.Login:
                Intent loginActivity1=new Intent(Main2Activity.this,Login.class);
                startActivity(loginActivity1);
                return true;
            case R.id.getInTouch:
                return true;
            case R.id.sellBook:
                //perform any action;
                return true;
            case R.id.Register:
                Intent registerActivity=new Intent(Main2Activity.this,Register.class);
                startActivity(registerActivity);
                return true;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(value) {
            MenuItem hideRegisterr=menu.findItem(R.id.Register);
            hideRegisterr.setVisible(false);
            MenuItem hideLogin=menu.findItem(R.id.Login);
            hideLogin.setVisible(false);
            MenuItem showLogout=menu.findItem(R.id.Logout);
            showLogout.setVisible(true);
            value=false;
        }
        else if(value){
            MenuItem showRegisterr=menu.findItem(R.id.Register);
            showRegisterr.setVisible(true);
            MenuItem showLogin=menu.findItem(R.id.Login);
            showLogin.setVisible(true);
            MenuItem hideLogout=menu.findItem(R.id.Logout);
            hideLogout.setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
        return true;
    }

}
