package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyCollege extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<Book> bookDetails2;
    private recycleViewAdapter recycleViewAdapter_3;

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
                     Toast.makeText(getApplicationContext(),"Network error, Try later",Toast.LENGTH_LONG).show();
                }
                 else{
                     final  String college=task.getResult().child("User_college").getValue().toString();
                     ClearAll();
                     GetDataFromFireBase(college);
                 }
            }
        });

    }
    public void GetDataFromFireBase(final String college){
//        Query query =databaseReference.child("Books");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Books").whereEqualTo("college",college).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
                        for(QueryDocumentSnapshot snapshot1: task.getResult()){
                            Book details=(snapshot1.toObject(Book.class));
                            bookDetails2.add(details);
                        }
                        recycleViewAdapter_3=new recycleViewAdapter(getApplicationContext(),bookDetails2);
                        recyclerView.setAdapter(recycleViewAdapter_3);
                        recycleViewAdapter_3.notifyDataSetChanged();
                    }
                    }
                    else {
                        AlertDialog.Builder alert=new AlertDialog.Builder(getApplicationContext());
                        alert.setMessage("Sorry, we don't have anything to show\n\n Let you be the first to add books here!!!");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.create();
                        alert.show();

                    }
                }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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
