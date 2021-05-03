package e.kailina.bukley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;



public class Main2Activity extends AppCompatActivity {
    Boolean value=false;
    Button uploadImage;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        uploadImage=findViewById(R.id.uploadImage);
        if(fAuth.getCurrentUser()!=null && fAuth.getCurrentUser().isEmailVerified()){
            value=true;
            invalidateOptionsMenu();
        }
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fAuth.getCurrentUser()!=null && fAuth.getCurrentUser().isEmailVerified()){
                    Intent gotoupload=new Intent(Main2Activity.this,uploadBookDetails.class);
                    startActivity(gotoupload);
                }
                else{
                    AlertDialog.Builder dialog=new AlertDialog.Builder(Main2Activity.this);
                    dialog.setMessage("Please Login to upload file");
                    dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog=dialog.create();
                    alertDialog.show();
                }
            }
        });
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
                value=false;
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(value) {
            MenuItem hideLogin=menu.findItem(R.id.Login);
            hideLogin.setVisible(false);
            MenuItem showLogout=menu.findItem(R.id.Logout);
            showLogout.setVisible(true);
        }
        else{

            MenuItem showLogin=menu.findItem(R.id.Login);
            showLogin.setVisible(true);
            MenuItem hideLogout=menu.findItem(R.id.Logout);
            hideLogout.setVisible(false);
        }

        return true;
    }

}
