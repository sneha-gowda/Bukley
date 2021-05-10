package e.kailina.bukley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ContectSeller extends AppCompatActivity {
    ImageView bookImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contect_seller);
        Intent getBookDetails=getIntent();
        String bName=getBookDetails.getStringExtra("BookName").toString();
        String bAuthor=getBookDetails.getStringExtra("BookAuthor").toString();
        String bEdition=getBookDetails.getStringExtra("BookEdition").toString();
        String bPrice=getBookDetails.getStringExtra("BookPrice").toString();
        String Url=getBookDetails.getStringExtra("ImageUrl").toString();
        Uri uri=Uri.parse(Url);
        String S_name=getBookDetails.getStringExtra("S_name").toString();
        String S_mail=getBookDetails.getStringExtra("S_mail").toString();
        String S_phone=getBookDetails.getStringExtra("S_phone").toString();
        bookImage=findViewById(R.id.CS_Bimage);
//        bookImage.setImageURI(uri);

    }
}
