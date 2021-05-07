package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {
    Boolean value=false;
    Button uploadImage;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<downloadBooks> bookDetails;
    private recycleViewAdapter recycleViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView=findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Main2Activity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        bookDetails=new ArrayList<>();
        ClearAll();
        GetDataFromFireBase();



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
                Intent loginActivity1 = new Intent(Main2Activity.this, Login.class);
                startActivity(loginActivity1);
                return true;
            case R.id.getInTouch:
                return true;
            case R.id.sellBook:
                gotoUploadActivity();
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


    public void GetDataFromFireBase(){

        Query query =databaseReference.child("Images");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
//                progressBar.setVisibility(View.VISIBLE);
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    downloadBooks details=new downloadBooks();
                    details.setBookname(snapshot1.child("b_name").getValue().toString());
                    details.setPrice("Rs "+snapshot1.child("b_price").getValue().toString());
                    details.setImageUrl(snapshot1.child("image_path").getValue().toString());
                    bookDetails.add(details);
                }
                recycleViewAdapter=new recycleViewAdapter(getApplicationContext(),bookDetails);
                recyclerView.setAdapter(recycleViewAdapter);
                recycleViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(bookDetails!=null){
            bookDetails.clear();
        }
        if(recycleViewAdapter!=null){
            recycleViewAdapter.notifyDataSetChanged();
        }
        bookDetails=new ArrayList<>();
    }

    public void gotoUploadActivity(){
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

}
