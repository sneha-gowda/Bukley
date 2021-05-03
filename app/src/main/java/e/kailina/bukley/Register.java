package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText name,password,email;
    ProgressBar progressBar;
    Button Register,goto_login;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        Register=findViewById(R.id.register);
        goto_login=findViewById(R.id.gotologin);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User_name=name.getText().toString().trim();
                String User_email=email.getText().toString().trim();
                String User_password=password.getText().toString().trim() ;
                if(TextUtils.isEmpty(User_name)){
                    name.setError("Please enter name");
                }
                else if(TextUtils.isEmpty(User_email) || !(android.util.Patterns.EMAIL_ADDRESS.matcher(User_email).matches())){
                    email.setError("Please enter valid Email");
                }

                else if(TextUtils.isEmpty(User_password)||User_password.length()<6){
                    password.setError("Please enter password having more than 5 characters");
                }
                else if(!isOnline()){
                    Toast.makeText(Register.this,"Please enable Internet",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences userDetails= getSharedPreferences("User_details",MODE_PRIVATE);
                    SharedPreferences.Editor edit=userDetails.edit();
                    edit.putString("Name",User_name).apply();

                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(User_email,User_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Objects.requireNonNull(fAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()) {
                                            progressBar.setVisibility(View.GONE);
                                            AlertDialog.Builder dialog=new AlertDialog.Builder(Register.this);
                                            dialog.setMessage("Verification link is sent to given mail, please click on that to login successfully ");
                                            dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    Intent gotoLogin = new Intent(Register.this, Login.class);
                                                    startActivity(gotoLogin);
                                                    finish();
                                                }
                                            });
                                            AlertDialog alertDialog=dialog.create();
                                            alertDialog.show();
                                        }
                                        else {
                                            AlertDialog.Builder dialog=new AlertDialog.Builder(Register.this);
                                            dialog.setMessage("hello");
                                            dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            AlertDialog alertDialog=dialog.create();
                                            alertDialog.show();
                                            progressBar.setVisibility(View.GONE);
                                        }

                                    }
                                });
                            }
                            else{
                                Toast.makeText(Register.this,"Error:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }



            }
        });
        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLogin=new Intent(Register.this,Login.class);
                startActivity(gotoLogin);
                finish();
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
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
