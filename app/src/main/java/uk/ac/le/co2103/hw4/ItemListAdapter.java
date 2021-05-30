package uk.ac.le.co2103.hw4;

import android.accounts.AbstractAccountAuthenticator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{

    private ArrayList<Item> mItem = new ArrayList<>();
    private OnNoteListener2 mOnNoteListener2;

    public ItemListAdapter(ArrayList<Item> mItem, OnNoteListener2 onNoteListener2) {
        this.mItem = mItem;
        this.mOnNoteListener2 = onNoteListener2;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item2,parent,false);
        return new ItemViewHolder(view, mOnNoteListener2);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mItem.get(position);
        holder.bind(item.getName(),item.getQuantity(),item.getUnit());
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView itemTextView1;
        private final TextView itemTextView2;
        private final TextView itemTextView3;
        OnNoteListener2 onNoteListener2;

        private ItemViewHolder(@NonNull View itemView, OnNoteListener2 onNoteListener2) {
            super(itemView);
            itemTextView1 = itemView.findViewById(R.id.textViewItemName);
            itemTextView2 = itemView.findViewById(R.id.textViewItemQuantity);
            itemTextView3 = itemView.findViewById(R.id.textViewItemUnit);

            this.onNoteListener2 = onNoteListener2;
            itemView.setOnClickListener(this);
        }
        public void bind(String name, int quantity, String unit) {
            itemTextView1.setText(name);
            itemTextView2.setText(String.valueOf(quantity));
            itemTextView3.setText(unit);
        }

        @Override
        public void onClick(View v) {
            onNoteListener2.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener2{
        void onNoteClick (int position);
    }
}
