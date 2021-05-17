package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {
    EditText mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        mail=findViewById(R.id.reset_mail);

    }

    public void sendResetmail(View view) {
        final String mail1=mail.getText().toString();
        if(!TextUtils.isEmpty(mail1)){
        FirebaseAuth.getInstance().sendPasswordResetEmail(mail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder alert=new AlertDialog.Builder(ForgotPass.this);
                            alert.setMessage("We have sent you a mail to reset your password!!");
                            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                                   Intent gotologin=new Intent(ForgotPass.this,Login.class);
                                   gotologin.putExtra("mail",mail1);
                                   startActivity(gotologin);
                                   finish();
                               }
                           }).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder alert=new AlertDialog.Builder(ForgotPass.this);
                alert.setMessage(e.getMessage());
                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });}
        else {
            mail.setError("Please enter your mail");
        }
    }
}
