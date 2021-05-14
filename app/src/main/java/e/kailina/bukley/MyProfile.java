package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfile extends AppCompatActivity {
    TextView name,college,mail,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        name = findViewById(R.id.Pname);
        college = findViewById(R.id.Pcollege);
        mail = findViewById(R.id.Pmail);
        phone = findViewById(R.id.Pphone);
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("User").child(userId).child("User_Data").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    String S_name = task.getResult().child("User_name").getValue().toString();
                    String S_mail = task.getResult().child("User_mail").getValue().toString();
                    String S_phone = task.getResult().child("User_phone").getValue().toString();
                    String College = task.getResult().child("User_college").getValue().toString();
                    name.setText(S_name);
                    college.setText(College);
                    phone.setText(S_phone);
                    mail.setText(S_mail);
                }
            }


        });
    }
}
