package e.kailina.bukley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static e.kailina.bukley.R.drawable.on_install2;

public class onInstalling extends AppCompatActivity {
    Button next;
    TextView text;
    ConstraintLayout layout;
    protected int next_click_count=1;

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
                next_click_count=2;
                text.setText("need to set");
//                text.setTextSize(25);
//                layout.setBackgroundResource(R.drawable.on_install2);


            }
        });
    }
}
