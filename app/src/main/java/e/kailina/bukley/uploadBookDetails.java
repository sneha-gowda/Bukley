package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;

public class uploadBookDetails extends AppCompatActivity {
    Uri imagePath;
    ImageView bookImage;
    EditText bookName,bookAuthor,bookEdition,price;
    String category;
    Button selectCategory;
    Bitmap bitmap;
    ProgressBar progressbar;
    String URL;
    Book book;
    String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
//    SharedPreferences numberOfBooks=getSharedPreferences("NumberofBooks",MODE_PRIVATE);
//    SharedPreferences.Editor edit=numberOfBooks.edit().putString("currentNoOfBooks",);
//    edit.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book_details);
        bookImage=findViewById(R.id.bookImage);
        bookName=findViewById(R.id.bookName);
        bookAuthor=findViewById(R.id.bookAuthor);
        bookEdition=findViewById(R.id.bookEdition);
        selectCategory=findViewById(R.id.selectCategory) ;
        price=findViewById(R.id.Price);
        progressbar=findViewById(R.id.progressBar3);
    }


    public void imageUploadProcess(View v){
        if(ContextCompat.checkSelfPermission(uploadBookDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            uploadImage();
        }
        else{
            ActivityCompat.requestPermissions(uploadBookDetails.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            uploadImage();
        }
        else{
            Toast.makeText(uploadBookDetails.this,"Please Grant permission",Toast.LENGTH_LONG).show();
        }
    }

    private void uploadImage() {
        Intent picImage=new Intent();
        picImage.setType("image/*");
        picImage.setAction(Intent.ACTION_GET_CONTENT );
        startActivityForResult(picImage,2);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData()!=null) {
            imagePath=data.getData();
            bookImage.setImageURI(imagePath);
            try {
                InputStream inputStream=getContentResolver().openInputStream(imagePath);
                bitmap= BitmapFactory.decodeStream(inputStream);
            }
            catch(Exception e){
                Log.d("biterror","image url error");
            }
        }
        else
            Toast.makeText(uploadBookDetails.this, "Please select image", Toast.LENGTH_SHORT).show();
    }



    public void selectCategory(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(uploadBookDetails.this);
        builder.setTitle("Choose a Option");
        final String[] selectedoption = {"Computer Science", "Electronics", "Civil", "Mechanics", "AeroSpace","Aeronautic"};
        int checkedItem = 1; // cow
        builder.setSingleChoiceItems(selectedoption, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                category=selectedoption[which];
                Toast.makeText(uploadBookDetails.this,"selected option: "+selectedoption[which],Toast.LENGTH_LONG).show();
            }
        });
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void uploadBoodDetails(View v){
        String b_name=bookName.getText().toString().trim();
        String A_name=bookAuthor.getText().toString().trim();
        String E_name=bookEdition.getText().toString().trim();
        String B_price=price.getText().toString().trim();
        if(imagePath!=null){
            if(TextUtils.isEmpty(b_name)){
                bookName.setError("Please enter book name");
            }
            else if(TextUtils.isEmpty(A_name)){
                bookAuthor.setError("Please enter Author name");
            }
            else if(TextUtils.isEmpty(E_name)){
                bookEdition.setError("Please enter book Edition");
            }
            else if(TextUtils.isEmpty(B_price)){
                price.setError("Please enter book price");
            }
            else if(TextUtils.isEmpty(category)){
                Toast.makeText(uploadBookDetails.this,"Please select book category",Toast.LENGTH_SHORT).show();
            }
            else{
                book=new Book(b_name,A_name,E_name,B_price,category);
                uploadToFireBase();
            }

        }
        else{
            Toast.makeText(uploadBookDetails.this,"Please provide book image",Toast.LENGTH_LONG).show();
        }
    }



    public void uploadToFireBase(){
        progressbar.setVisibility(View.VISIBLE);
        final FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference storageReference=storage.getReference(userId+"/"+bookName.getText().toString().trim());



        storageReference.putFile(imagePath).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressbar.setVisibility(View.INVISIBLE);
                Toast.makeText(uploadBookDetails.this,"Failed to connect try again",Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String,Object> imageuri=new HashMap<>();
                        imageuri.put("ImageLocation",uri);
                        URL=uri.toString();
                        book.setUri(URL);
                        uploadToRealTimeDatabase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(uploadBookDetails.this,"failer to get url",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void uploadToRealTimeDatabase(){
        progressbar.setVisibility(View.GONE);
        final FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference(userId+"/"+book.B_name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(book);
                Toast.makeText(uploadBookDetails.this,"Uploaded Successefully",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(uploadBookDetails.this,"Failed to Upload, Try again",Toast.LENGTH_LONG).show();

            }
        });
    }
}
