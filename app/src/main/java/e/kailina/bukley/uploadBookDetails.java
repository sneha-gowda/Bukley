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
import android.os.Handler;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

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
    Button submit;
    String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    Random random;
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
        submit=findViewById(R.id.uploadDetails);
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
            bookImage.setMaxWidth(350);
            bookImage.setMaxHeight(200);
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
            }
        });
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void uploadBoodDetails(View v){
        final String b_name=bookName.getText().toString().trim();
        String A_name=bookAuthor.getText().toString().trim();
        String E_name=bookEdition.getText().toString().trim();
        final String B_price=price.getText().toString().trim();
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
                selectCategory.setError("Please select category");
            }
            else{
                book=new Book(b_name,A_name,E_name,B_price,category);
                FirebaseDatabase.getInstance().getReference().child("User").child(userId).child("User_Data").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            String S_name=task.getResult().child("User_name").getValue().toString();
                            String S_mail=task.getResult().child("User_mail").getValue().toString();
                            String S_phone=task.getResult().child("User_phone").getValue().toString();
                            String College=task.getResult().child("User_college").getValue().toString();
                            book.setS_name(S_name); book.setS_mail(S_mail);book.setS_phone(S_phone);book.setCollege(College);
                        }
                    }
                });
                uploadToFireBase();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"please provide book image",Toast.LENGTH_LONG).show();
        }
    }
    public void uploadToFireBase(){
        progressbar.setVisibility(View.VISIBLE);
        final FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference storageReference=storage.getReference(userId+"/Books"+bookName.getText().toString().toLowerCase().trim());
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
                Toast.makeText(uploadBookDetails.this,"Failed to connect, try again",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(uploadBookDetails.this,"Network error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void uploadToRealTimeDatabase(){
        final FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference("User"+"/").child(userId+"/Books/"+book.B_name.toLowerCase());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(book);
                firebaseDatabase.getReference().child("Books/"+book.B_name.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        firebaseDatabase.getReference().child("Books/"+book.B_name.toLowerCase()).setValue(book);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                uploadtofirestore1();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.INVISIBLE);
                Toast.makeText(uploadBookDetails.this,"Failed to Upload, Try again",Toast.LENGTH_LONG).show();
            }
        });}
        void uploadtofirestore1(){

            FirebaseFirestore db=FirebaseFirestore.getInstance();
            String unique=bookName.getText().toString()+String.valueOf(FirebaseAuth.getInstance().getUid());
            db.collection("Books").document(unique).set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    UploadtofireStroe2();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();
                }
            });
        }
        void UploadtofireStroe2(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
            db.collection(userId).document(bookName.getText().toString()).set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Uploaded Successfully",Toast.LENGTH_LONG).show();
                    Intent gotoMain = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(gotoMain);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

}

