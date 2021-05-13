package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCollege extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<downloadBooks> bookDetails2;
    private recycleViewAdapter3 recycleViewAdapter_3;
    String college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_college);
        recyclerView=findViewById(R.id.recyclerView3);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(MyCollege.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        bookDetails2=new ArrayList<>();
        String Uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(Uid);
        databaseReference.child("User_Data").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                 if(!task.isSuccessful()) {
                     Toast.makeText(getApplicationContext(),"im fassu im mad and i eat gu",Toast.LENGTH_LONG).show();
                }
                 else{
                     college=task.getResult().child("User_college").getValue().toString();
                 }
            }
        });
        ClearAll();
        GetDataFromFireBase();
    }
    public void GetDataFromFireBase(){
        Query query =databaseReference.child("Books");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    downloadBooks details=new downloadBooks();
                    details.setBookname(snapshot1.child("b_name").getValue().toString());
                    details.setPrice("Rs "+snapshot1.child("b_price").getValue().toString());
                    details.setImageUrl(snapshot1.child("image_path").getValue().toString());
                    details.setAuthor(snapshot1.child("b_author").getValue().toString());
                    details.setEdition(snapshot1.child("b_edition").getValue().toString());
                    details.setS_name(snapshot1.child("s_name").getValue().toString());
                    details.setS_mail(snapshot1.child("s_mail").getValue().toString());
                    details.setS_phone(snapshot1.child("s_phone").getValue().toString());
                    bookDetails2.add(details);
                }
                recycleViewAdapter_3=new recycleViewAdapter3(getApplicationContext(),bookDetails2);
                recyclerView.setAdapter(recycleViewAdapter_3);
                recycleViewAdapter_3.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(bookDetails2!=null){
            bookDetails2.clear();
        }
        if(recycleViewAdapter_3!=null){
            recycleViewAdapter_3.notifyDataSetChanged();
        }
        bookDetails2=new ArrayList<>();
    }
}
