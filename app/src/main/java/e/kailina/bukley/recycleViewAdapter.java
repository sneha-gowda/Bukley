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

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> imageList;

    public recycleViewAdapter(Context context, ArrayList<Book> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public recycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bookName.setText(imageList.get(position).getB_name());
        holder.bookPrice.setText(imageList.get(position).getB_price());
        Glide.with(context).load(imageList.get(position).getImage_path()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bName= imageList.get(position).getB_name();
                Intent gotoContactSeller =new Intent( context, ContectSeller.class);
                gotoContactSeller.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                gotoContactSeller.putExtra("BookName",bName);
                gotoContactSeller.putExtra("BookAuthor",imageList.get(position).getB_author());
                gotoContactSeller.putExtra("BookEdition",imageList.get(position).getB_edition());
                gotoContactSeller.putExtra("BookPrice",imageList.get(position).getB_price());
                gotoContactSeller.putExtra("ImageUrl",imageList.get(position).getImage_path());
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
