package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button login,register;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    EditText login_email,login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        register=findViewById(R.id.Registerpagelink);
        progressBar=findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();
        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_register=new Intent(Login.this,Register.class);
                startActivity(goto_register);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(login_password.getText().toString().trim()) && !TextUtils.isEmpty(login_password.getText().toString().trim())){
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(login_email.getText().toString().trim(),login_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                if(fAuth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(Login.this,"Login successful",Toast.LENGTH_SHORT).show();
                                    Intent gotoMainActivity=new Intent(Login.this,Main2Activity.class);
                                    gotoMainActivity.putExtra("finishedRegistration",true);
                                    startActivity(gotoMainActivity);
                                    finish();
                                }
                                else{
                                    AlertDialog.Builder dialog=new AlertDialog.Builder(Login.this);
                                    dialog.setMessage("Please verify from the link sent to your registed mail ID");
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
                            else{
                                AlertDialog.Builder dialog=new AlertDialog.Builder(Login.this);
                                dialog.setMessage("Email ID or password is invalid");
                                login_email.setText("");
                                login_password.setText("");
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
                else{
                    AlertDialog.Builder dialog=new AlertDialog.Builder(Login.this);
                    dialog.setMessage("Please provide valid email and password");
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

}
