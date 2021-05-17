package e.kailina.bukley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class onInstalling extends AppCompatActivity {
    Button next;
    TextView text;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_installing);
        next=findViewById(R.id.next);
        text=findViewById(R.id.textView);
        layout=findViewById(R.id.layout);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               text.setText(R.string.welcomestring2);
                text.setTextSize(25);
                next.setText("Explore");
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent letsStart=new Intent(onInstalling.this,Main2Activity.class);
                        startActivity(letsStart);
                        finish();
                    }
                });
            }

        });}





}
