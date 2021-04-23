package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Register extends AppCompatActivity {
    EditText name,password,email,phone;
    ProgressBar progressBar;
    Button Register,gotologin;
    FirebaseAuth fAuth;
    MenuInflater item;
    Menu menu;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        Register=findViewById(R.id.register);
        gotologin=findViewById(R.id.gotologin);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User_name=name.getText().toString().trim();
                String User_email=email.getText().toString().trim();
                String User_phone=phone.getText().toString().trim();
                String User_password=password.getText().toString().trim() ;
                if(TextUtils.isEmpty(User_name)){
                    name.setError("Please enter name");
                }
                else if(TextUtils.isEmpty(User_email)){
                    email.setError("Please enter Email");
                }
                else if(TextUtils.isEmpty(User_phone)){
                    phone.setError("Please enter phone");
                }
                else if(TextUtils.isEmpty(User_password)||User_password.length()<6){
                    password.setError("Please enter password having more than 5 characters");
                }
                else if(!isOnline()){
                    Toast.makeText(Register.this,"Please enable Internet",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(User_email,User_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent gotomainActivity=new Intent(Register.this,Main2Activity.class);
                                gotomainActivity.putExtra("finishedRegistration",true);
                                startActivity(gotomainActivity);
                                finish();
                            }
                            else{
                                Toast.makeText(Register.this,"Error:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                Log.d("mytag",task.getException().getMessage());
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }



            }
        });


    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

}
