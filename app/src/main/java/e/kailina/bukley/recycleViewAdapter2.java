package e.kailina.bukley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookName.setText(mybooks.get(position).getBookname());
        holder.Author.setText("Author: "+mybooks.get(position).getAuthor());
        holder.Edition.setText("Edition: "+mybooks.get(position).getEdition());
        holder.bookPrice.setText(mybooks.get(position).getPrice());
        Glide.with(context).load(mybooks.get(position).getImageUrl()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return mybooks.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bookName, Author, Edition;
        TextView bookPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.my_bimage);
            bookName = itemView.findViewById(R.id.my_bname);
            bookPrice = itemView.findViewById(R.id.my_bprice);
            Author = itemView.findViewById(R.id.my_author);
            Edition = itemView.findViewById(R.id.my_bedition);
        }
    }
}
