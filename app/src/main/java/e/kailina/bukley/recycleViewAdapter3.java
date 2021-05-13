package e.kailina.bukley;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recycleViewAdapter3 extends RecyclerView.Adapter<recycleViewAdapter3.ViewHolder> {
    private Context context;
    private ArrayList<downloadBooks> imageList;

    public recycleViewAdapter3(Context context, ArrayList<downloadBooks> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public recycleViewAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_details,parent,false);
        return new recycleViewAdapter3.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull recycleViewAdapter3.ViewHolder holder, final int position) {
        holder.bookName.setText(imageList.get(position).getBookname());
        holder.bookPrice.setText(imageList.get(position).getPrice());
        Glide.with(context).load(imageList.get(position).getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bName= imageList.get(position).getBookname();
                Intent gotoContactSeller =new Intent( context, ContectSeller.class);
                gotoContactSeller.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                gotoContactSeller.putExtra("BookName",bName);
                gotoContactSeller.putExtra("BookAuthor",imageList.get(position).getAuthor());
                gotoContactSeller.putExtra("BookEdition",imageList.get(position).getEdition());
                gotoContactSeller.putExtra("BookPrice",imageList.get(position).getPrice());
                gotoContactSeller.putExtra("ImageUrl",imageList.get(position).getImageUrl());
                gotoContactSeller.putExtra("S_name",imageList.get(position).getS_name());
                gotoContactSeller.putExtra("S_mail",imageList.get(position).getS_mail());
                gotoContactSeller.putExtra("S_phone",imageList.get(position).getS_phone());
                context.startActivity(gotoContactSeller);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size() ;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView bookName;
        TextView bookPrice;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView= itemView.findViewById(R.id.bookpic);
            bookName=itemView.findViewById(R.id.bName);
            bookPrice=itemView.findViewById(R.id.bPrice);

        }
    }
}
