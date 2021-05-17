package e.kailina.bukley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ContectSeller extends AppCompatActivity {
    ImageView bookImage;
    TextView bookName,Author,Edition,Price,Seller_phone,Seller_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contect_seller);

        Intent getBookDetails=getIntent();
        String bName=getBookDetails.getStringExtra("BookName").toUpperCase();
        String bAuthor= getString(R.string.author)+ getBookDetails.getStringExtra("BookAuthor");
        String bEdition=getString(R.string.edition)+ getBookDetails.getStringExtra("BookEdition");
        String bPrice=getString(R.string.price)+ getBookDetails.getStringExtra("BookPrice");
        String Url= getBookDetails.getStringExtra("ImageUrl");
        Uri uri=Uri.parse(Url);
        String S_name= getBookDetails.getStringExtra("S_name");
        String S_mail= getBookDetails.getStringExtra("S_mail");
        String S_phone="phno: "+ getBookDetails.getStringExtra("S_phone");

        bookImage=findViewById(R.id.CS_Bimage);
        Glide.with(ContectSeller.this).load(Url).into(bookImage);
        bookName=findViewById(R.id.bookname);
        Author=findViewById(R.id.Author);
        Edition=findViewById(R.id.Edition);
        Price=findViewById(R.id.bookPrice);
        Seller_phone=findViewById(R.id.seller_Phone);
        Seller_mail=findViewById(R.id.seller_mail);

        bookName.setText(bName);
        Author.setText(bAuthor);
        Edition.setText(bEdition);
        Price.setText(bPrice);
        Seller_phone.setText(S_phone);
        Seller_mail.setText(S_mail);



//        bookImage.setImageURI(uri);

    }
}
