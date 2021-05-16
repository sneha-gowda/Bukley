package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyBooks extends AppCompatActivity {

    RecyclerView recyclerView;
    recycleViewAdapter2 recycleViewAdapter2;
    ArrayList<Book> mybooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        recyclerView=findViewById(R.id.recyclerView2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        mybooks=new ArrayList<>();

        ClearAll();
        GetDataFromFireBase();

    }

    public void GetDataFromFireBase(){
        //Toast.makeText(getApplicationContext(),"sdjkfhkadsjf",Toast.LENGTH_LONG).show();
        String Uid= FirebaseAuth.getInstance().getUid();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection(Uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() ){
                    if( task.getResult()!=null)
                    {
                        for(QueryDocumentSnapshot snapshot1: task.getResult()){
                       Book details=(snapshot1.toObject(Book.class));
                        mybooks.add(details);
                        }
                        recycleViewAdapter2=new recycleViewAdapter2(getApplicationContext(),mybooks);
                        recyclerView.setAdapter(recycleViewAdapter2);
                        recycleViewAdapter2.notifyDataSetChanged();
                    }
                    else{
                    Toast.makeText(getApplicationContext(),"You have no books to show", Toast.LENGTH_LONG).show();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void ClearAll(){
        if(mybooks!=null){
            mybooks.clear();
        }
        if(recycleViewAdapter2!=null){
            recycleViewAdapter2.notifyDataSetChanged();
        }
        mybooks=new ArrayList<>();
    }

}
