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

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.ViewHolder> {
    private static final String tag="Recycleriew";
    private Context context;
    private ArrayList<downloadBooks> imageList;

    public recycleViewAdapter(Context context, ArrayList<downloadBooks> imageList) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookName.setText(imageList.get(position).getBookname());
        Glide.with(context).load(imageList.get(position).getImageUrl()).into(holder.imageView);
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
            bookPrice=itemView.findViewById(R.id.bookprice);

        }
    }
}
