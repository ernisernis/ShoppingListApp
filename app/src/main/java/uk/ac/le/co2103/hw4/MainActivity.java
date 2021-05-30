package uk.ac.le.co2103.hw4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Parcelable;
import android.util.Log;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ProductListAdapter.OnNoteListener, ProductListAdapter.OnLongClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProductViewModel productViewModel;
    public static final int ADD_ITEM_ACTIVITY_REQUEST_CODE = 1;
    private ArrayList<Product> mProducts = new ArrayList<>();
    private ArrayList<Product> mProducts2 = new ArrayList<>();
    public ProductListAdapter mProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mProductListAdapter = new ProductListAdapter(mProducts,this,this);
        recyclerView.setAdapter(mProductListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this,products -> {
            mProducts.clear();
            mProducts.addAll(products);
            mProductListAdapter.notifyDataSetChanged();
        });



        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            Log.d(TAG, "Floating action button clicked.");
            Intent switchActivityIntent = new Intent(this, CreateListActivity.class);
            startActivityForResult(switchActivityIntent,ADD_ITEM_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean b = false;
            for (int i = 0; i < mProducts.size(); i++) {
                String a = mProducts.get(i).getProductName();
                if (a.equals(data.getStringExtra(CreateListActivity.EXTRA_REPLY))) {
                    Toast.makeText(getApplicationContext(), "Name must be unique!", Toast.LENGTH_SHORT).show();
                    b = true;
                }
            }
            if (b == false) {
                Product product = new Product(data.getStringExtra(CreateListActivity.EXTRA_REPLY),data.getStringExtra(CreateListActivity.EXTRA_IMAGE));
                productViewModel.insert(product);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Item cannot be empty", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onNoteClick(int position) {

        Product current = mProducts.get(position);
        Intent intent = new Intent(this, ShoppingListActivity.class);
        intent.putExtra("productlistid", current.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);

        builder.setTitle("Are you sure you want to delete this item?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int WhichButton)  {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                productViewModel.deleteProduct(mProducts.get(position));
                mProducts.remove(position);
                mProductListAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }
}