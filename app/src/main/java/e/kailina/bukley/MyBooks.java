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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBooks extends AppCompatActivity {

    RecyclerView recyclerView;
    recycleViewAdapter2 recycleViewAdapter2;
    ArrayList<downloadBooks> mybooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        recyclerView=findViewById(R.id.recyclerView2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mybooks=new ArrayList<>();

        ClearAll();
        GetDataFromFireBase();

    }

    public void GetDataFromFireBase(){
        String Uid= FirebaseAuth.getInstance().getUid();
        Query query = FirebaseDatabase.getInstance().getReference().child("User").child(Uid).child("Books");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                if(!snapshot.exists()&& !isFinishing()){
                    final AlertDialog.Builder alert=new AlertDialog.Builder(MyBooks.this);
                    alert.setMessage("Your books list is empty,to add some books click on 'Add' ");
                    alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent gotoUpload=new Intent(getApplicationContext(),uploadBookDetails.class);
                            startActivity(gotoUpload);
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.create();
                    alert.show();
                }
                else{
                    for(DataSnapshot snapshot1:snapshot.getChildren()) {
                        downloadBooks details=new downloadBooks();
                        details.setBookname(snapshot1.child("b_name").getValue().toString());
                        details.setPrice("Rs "+snapshot1.child("b_price").getValue().toString());
                        details.setImageUrl(snapshot1.child("image_path").getValue().toString());
                        details.setAuthor(snapshot1.child("b_author").getValue().toString());
                        details.setEdition(snapshot1.child("b_edition").getValue().toString());
                        mybooks.add(details);
                    }
                    recycleViewAdapter2=new recycleViewAdapter2(getApplicationContext(),mybooks);
                    recyclerView.setAdapter(recycleViewAdapter2);
                    recycleViewAdapter2.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
