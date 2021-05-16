package e.kailina.bukley;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recycleViewAdapter2 extends RecyclerView.Adapter<recycleViewAdapter2.ViewHolder> {
    private Context context;
    private ArrayList<Book> mybooks;
    public recycleViewAdapter2(Context context, ArrayList<Book> imageList) {
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
        final String Uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Book book=mybooks.get(position);
        holder.bookName.setText(mybooks.get(position).getB_name());
        holder.Author.setText("Author: "+mybooks.get(position).getB_author());
        holder.Edition.setText("Edition: "+mybooks.get(position).getB_edition());
        holder.bookPrice.setText(mybooks.get(position).getB_price());
        Glide.with(context).load(mybooks.get(position).getImage_path()).into(holder.imageView);
        final  String bookName=mybooks.get(position).getB_name();
        holder.onDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection(Uid).document(bookName).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show();
                            remove(book);
                        }
                        else
                            Toast.makeText(context,"cant delete",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"cant delete",Toast.LENGTH_LONG).show();
                    }
                });
                    }
                });
    }

    private void  remove(Book book){
        int position =mybooks.indexOf(book);
        mybooks.remove(position);
        notifyItemRemoved(position);
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
