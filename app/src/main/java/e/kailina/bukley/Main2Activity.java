package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {
    Boolean value=false;
    Button uploadImage;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();

    RecyclerView recyclerView;
    private ArrayList<Book> bookDetails;
    private recycleViewAdapter recycleViewAdapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView=findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Main2Activity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(false);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        bookDetails=new ArrayList<>();
        //ClearAll();
        //GetDataFromFireBase();

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
    protected void onResume() {
        super.onResume();
        ClearAll();
        GetDataFromFireBase();
    }
    // INTERACTION WITH DATABASE FOR RECYCLE VIEW

    public void GetDataFromFireBase(){

        final ProgressDialog pd = new ProgressDialog(Main2Activity.this);
        pd.setMessage("loading");
        pd.show();
        FirebaseFirestore db =FirebaseFirestore.getInstance();
        db.collection("Books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    for(QueryDocumentSnapshot snapshot1: task.getResult()){
                        Book details=(snapshot1.toObject(Book.class));
                        bookDetails.add(details);
                    }
                    recycleViewAdapter=new recycleViewAdapter(getApplicationContext(),bookDetails);
                recyclerView.setAdapter(recycleViewAdapter);
                recycleViewAdapter.notifyDataSetChanged();
                }
                else{
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();
                }
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

// GOTO BOOK DETAILS UPLOADING ACTIVITY
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


// MENU HANDLING
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);
        MenuItem search_button=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) search_button.getActionView();
        searchView.setQueryHint("Enter book name ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Books").child(query.toLowerCase());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Intent gotoContactSeller =new Intent( getApplicationContext(), ContectSeller.class);
                            gotoContactSeller.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            gotoContactSeller.putExtra("BookName",snapshot.child("b_name").getValue().toString());
                            gotoContactSeller.putExtra("BookAuthor",snapshot.child("b_author").getValue().toString());
                            gotoContactSeller.putExtra("BookEdition",snapshot.child("b_edition").getValue().toString());
                            gotoContactSeller.putExtra("BookPrice",snapshot.child("b_price").getValue().toString());
                            gotoContactSeller.putExtra("ImageUrl",snapshot.child("image_path").getValue().toString());
                            gotoContactSeller.putExtra("S_name",snapshot.child("s_name").getValue().toString());
                            gotoContactSeller.putExtra("S_mail",snapshot.child("s_mail").getValue().toString());
                            gotoContactSeller.putExtra("S_phone",snapshot.child("s_phone").getValue().toString());
                            getApplicationContext().startActivity(gotoContactSeller);
                        }
                        else{
                            AlertDialog.Builder dialog=new AlertDialog.Builder(Main2Activity.this);
                            dialog.setMessage("Sorry, this book is not available at present ");
                            dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog=dialog.create();
                            alertDialog.show();
                            //Toast.makeText(getApplicationContext(),"ok fine! lets see whats wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent gotoProfile=new Intent(getApplicationContext(), MyProfile.class);
                startActivity(gotoProfile);
                return true;
            case R.id.mycollege:
                Intent gotoMyCollege=new Intent(getApplicationContext(), MyCollege.class);
                startActivity(gotoMyCollege);
                return true;
            case R.id.mybooks:
                Intent gotoMyBooks= new Intent(getApplicationContext(),MyBooks.class);
                startActivity(gotoMyBooks);
                return true;
            case R.id.Login:
                Intent loginActivity1 = new Intent(Main2Activity.this, Login.class);
                startActivity(loginActivity1);
                return true;
            case R.id.getInTouch:
                Intent contactUs=new Intent(Intent.ACTION_SEND);
                contactUs.putExtra(Intent.EXTRA_EMAIL,new String[]{"Bukley.info@gmail.com"});
                contactUs.setType("message/rfc822");
                startActivity(Intent.createChooser(contactUs, "Choose an Email client :"));
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
            MenuItem hideMyBooks=menu.findItem(R.id.mybooks);
            hideMyBooks.setVisible(true);
            MenuItem hideCollege=menu.findItem(R.id.mycollege);
            hideCollege.setVisible(true);
            MenuItem hideProfile=menu.findItem(R.id.profile);
            hideProfile.setVisible(true);
        }
        else{
            MenuItem showLogin=menu.findItem(R.id.Login);
            showLogin.setVisible(true);
            MenuItem hideLogout=menu.findItem(R.id.Logout);
            hideLogout.setVisible(false);
            MenuItem hideMyBooks=menu.findItem(R.id.mybooks);
            hideMyBooks.setVisible(false);
            MenuItem hideCollege=menu.findItem(R.id.mycollege);
            hideCollege.setVisible(false);
            MenuItem hideProfile=menu.findItem(R.id.profile);
            hideProfile.setVisible(false);
        }
        return true;
    }
}
