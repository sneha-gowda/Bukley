package e.kailina.bukley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recycleViewAdapter2 extends RecyclerView.Adapter<recycleViewAdapter2.ViewHolder> {
    private Context context;
    private ArrayList<downloadBooks> mybooks;
    public recycleViewAdapter2(Context context, ArrayList<downloadBooks> imageList) {
        this.context = context;
        this.mybooks = imageList;
    }
    @NonNull
    @Override
    public recycleViewAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mybooks,parent,false);
        return new recycleViewAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String Uid= FirebaseAuth.getInstance().getUid();
        final downloadBooks book=mybooks.get(position);
        holder.bookName.setText(mybooks.get(position).getBookname());
        holder.Author.setText("Author: "+mybooks.get(position).getAuthor());
        holder.Edition.setText("Edition: "+mybooks.get(position).getEdition());
        holder.bookPrice.setText(mybooks.get(position).getPrice());
        Glide.with(context).load(mybooks.get(position).getImageUrl()).into(holder.imageView);
        final  String bookName=mybooks.get(position).getBookname();
        holder.onDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Deleted_Books").child(bookName);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                databaseReference.setValue(book);
                                androidx.appcompat.app.AlertDialog.Builder dialog=new androidx.appcompat.app.AlertDialog.Builder(context);
//                                Toast.makeText(context,"Deleted Succefully",Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Toast.makeText(context,"Failed to Upload, Try again",Toast.LENGTH_LONG).show();

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("User").child(Uid).child("Books").child(bookName).getRef().setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("Books").child(bookName).getRef().setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context,"Deleted Successefully",Toast.LENGTH_LONG).show();
                        }
                });
                    }
                });
    }


    @Override
    public int getItemCount() {
        return mybooks.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bookName, Author, Edition;
        TextView bookPrice;
        Button onDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.my_bimage);
            bookName = itemView.findViewById(R.id.my_bname);
            bookPrice = itemView.findViewById(R.id.my_bprice);
            Author = itemView.findViewById(R.id.my_author);
            Edition = itemView.findViewById(R.id.my_bedition);
            onDelete=itemView.findViewById(R.id.delete);
        }
    }
}
