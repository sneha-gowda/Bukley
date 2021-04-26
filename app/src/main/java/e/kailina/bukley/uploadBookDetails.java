package e.kailina.bukley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class uploadBookDetails extends AppCompatActivity {
    Uri imagePath;
    ImageView bookImage;
    EditText bookName,bookAuthor,bookEdition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book_details);
        bookImage=findViewById(R.id.bookImage);
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
        if (requestCode == 2 && requestCode == RESULT_OK && data != null && data.getData()!=null) {
            imagePath=data.getData();
            bookImage.setImageURI(imagePath);
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
        if(imagePath!=null){
            Toast.makeText(uploadBookDetails.this,"Successeful",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(uploadBookDetails.this,"Plese provide book image",Toast.LENGTH_LONG).show();
        }

    }
}
