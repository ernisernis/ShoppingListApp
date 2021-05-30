package uk.ac.le.co2103.hw4;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private ArrayList<Product> mProduct = new ArrayList<>();
    private OnNoteListener mOnNoteListener;
    private OnLongClickListener mOnLongClickListener;

    public ProductListAdapter(ArrayList<Product> mProduct, OnNoteListener onNoteListener, OnLongClickListener onLongClickListener) {
        this.mProduct = mProduct;
        this.mOnNoteListener = onNoteListener;
        this.mOnLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item,parent,false);
        return new ProductViewHolder(view, mOnNoteListener,mOnLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product current = mProduct.get(position);
        holder.bind(current.getProduct(),current.getProducturi());
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView productTextView;
        private final ImageView productImageView;
        OnNoteListener onNoteListener;
        OnLongClickListener onLongClickListener;

        private ProductViewHolder(View productView, OnNoteListener onNoteListener, OnLongClickListener mOnLongClickListener) {
            super(productView);
            productTextView = productView.findViewById(R.id.textView);
            productImageView = productView.findViewById(R.id.image);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

            this.onLongClickListener = mOnLongClickListener;
            itemView.setOnLongClickListener(this);

        }
        public void bind(String text,String uri) {
            if (uri != null) {
                Uri muri = Uri.parse(uri);
                productImageView.setImageURI(muri);
            }
            productTextView.setText(text);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onLongClickListener.onDeleteClick(getAdapterPosition());
            return true;
        }

    }
    public interface OnNoteListener{
        void onNoteClick (int position);
    }
    public interface OnLongClickListener{
        void onDeleteClick (int position);
    }

}