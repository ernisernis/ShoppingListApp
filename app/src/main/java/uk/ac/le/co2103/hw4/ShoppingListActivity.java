package uk.ac.le.co2103.hw4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity implements ItemListAdapter.OnNoteListener2 {

    //SHOWS ALL THE SHOPPLING LIST ITEMS WITH SALMON COLOR.

    private static final String TAG = "ShoppingListActivity";
    public static final String EXTRA_REPLY1 = "uk.ac.le.co2103.hw4.REPLY1";
    public static final String EXTRA_REPLY2 = "uk.ac.le.co2103.hw4.REPLY2";
    public static final String EXTRA_REPLY3 = "uk.ac.le.co2103.hw4.REPLY3";
    public static final String EXTRA_REPLY4 = "uk.ac.le.co2103.hw4.REPLY4";

    public static final int ADD_ITEM_ACTIVITY_REQUEST_CODE = 5;
    public static final int ADD_ITEM_ACTIVITY_REQUEST_CODE2 = 10;

    private ProductViewModel productViewModel;
    private ArrayList<Item> mItems = new ArrayList<>();
    private ArrayList<Item> mItems2 = new ArrayList<>();
    public ItemListAdapter mItemListAdapter;
    public int sessionId;
    public int x;
    public String name;
    public String unit;
    public int quantity;
    public String object;
    public Item item2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_activity);

        sessionId = getIntent().getIntExtra("productlistid",0);

        RecyclerView recyclerView = findViewById(R.id.recyclerview2);
        mItemListAdapter = new ItemListAdapter(mItems,this);
        recyclerView.setAdapter(mItemListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllItems().observe(this, items -> {

            mItems.clear();
            mItems2.clear();
           mItems2.addAll(items);
            for (int i = 0; i < mItems2.size(); i++) {
                int a = mItems2.get(i).getItemId();
                if (a == sessionId) {
                    mItems.add(mItems2.get(i));

                }
            }
            mItemListAdapter.notifyDataSetChanged();

        });


        final FloatingActionButton button = findViewById(R.id.fabAddProduct);
        button.setOnClickListener(v -> {
            Intent switchActivityIntent = new Intent(this, AddProductActivity.class);
            switchActivityIntent.putExtra("productlistid",sessionId);
            startActivityForResult(switchActivityIntent,ADD_ITEM_ACTIVITY_REQUEST_CODE);
        });



    }
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            
            String name = data.getStringExtra(AddProductActivity.EXTRA_REPLY);
            for (int i = 0; i < mItems2.size(); i++) {
                int b = mItems2.get(i).getItemId();
                String a = mItems2.get(i).getName();
                if (name.equals(a) && b==sessionId) {
                    x = 5;
                }
            }
            if (x==5) {
                Toast.makeText(this, "Product already exists", Toast.LENGTH_SHORT).show();
            } else {
                int i=Integer.parseInt(data.getStringExtra(AddProductActivity.EXTRA_REPLY2));
                Item item = new Item(sessionId,data.getStringExtra(AddProductActivity.EXTRA_REPLY),i,data.getStringExtra(AddProductActivity.EXTRA_REPLY3));
                productViewModel.insertItem(item);
            }
        }
        if (requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE2 && resultCode == RESULT_OK) {
            //name
            String name = data.getStringExtra(UpdateProductActivity.EXTRA_REPLY1);

            //count number
            String i = data.getStringExtra(UpdateProductActivity.EXTRA_REPLY2);
            int j=Integer.parseInt(i);

            //spiner data
            String spinner = data.getStringExtra(UpdateProductActivity.EXTRA_REPLY3);

            //sessionid
            int id = sessionId;

            //object
            String object2 = data.getStringExtra(UpdateProductActivity.EXTRA_REPLY4);

            item2 = new Item(sessionId,name,j,spinner);

            for (int x = 0; x<mItems.size();x++) {
                String y = mItems.get(x).toString();
                if (y.equals(object2)) {
                    productViewModel.deleteItem(mItems.get(x));
                    mItems.remove(x);
                    mItems.add(item2);
                    productViewModel.insertItem(item2);
                    mItemListAdapter.notifyDataSetChanged();
                }
            }

        }
    }

    @Override
    public void onNoteClick(int position) {

        name = mItems.get(position).getName();
        quantity = mItems.get(position).getQuantity();
        unit = mItems.get(position).getUnit();
        object = mItems.get(position).toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this);
        builder.setCancelable(true);
        builder.setTitle("What do you want to do with this item?");


        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int WhichButton)  {

                switchActivity();
            }
        });
        builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                productViewModel.deleteItem(mItems.get(position));
                mItems.remove(position);
                mItemListAdapter.notifyDataSetChanged();

            }
        });
        builder.show();

    }
    void switchActivity() {
        Intent switchActivityIntent = new Intent(this, UpdateProductActivity.class);
        switchActivityIntent.putExtra(EXTRA_REPLY1,name);
        String s=String.valueOf(quantity);
        switchActivityIntent.putExtra(EXTRA_REPLY2,s);
        switchActivityIntent.putExtra(EXTRA_REPLY3,unit);
        switchActivityIntent.putExtra(EXTRA_REPLY4,object);
        setResult(RESULT_OK, switchActivityIntent);
        startActivityForResult(switchActivityIntent,ADD_ITEM_ACTIVITY_REQUEST_CODE2);
    }
}
