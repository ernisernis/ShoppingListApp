package uk.ac.le.co2103.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {

    //ADDING A PRODUCT WHERE WE ENTER NAME, QUANTITY, SPINNER and a BUTTON ADD

    public static final String EXTRA_REPLY = "uk.ac.le.co2103.hw4.REPLY"; //name
    public static final String EXTRA_REPLY2 = "uk.ac.le.co2103.hw4.REPLY2"; //quantity
    public static final String EXTRA_REPLY3 = "uk.ac.le.co2103.hw4.REPLY3"; //spinner

    private ArrayList<Item> mItems = new ArrayList<>();
    private Spinner unitSpinner;
    private EditText editTextName;
    private EditText editTextQuantity;
    private Button addButton;
    public int sessionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);
        sessionId = getIntent().getIntExtra("productlistid",0);

        unitSpinner = findViewById(R.id.spinnerId);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        addButton = findViewById(R.id.bUplaodProduct);

        addButton.setOnClickListener(v -> {

            Intent replyIntent = new Intent();
            if(TextUtils.isEmpty(editTextName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            }else {
                String name = editTextName.getText().toString();
                String quantity = editTextQuantity.getText().toString();
                String uspinner = unitSpinner.getSelectedItem().toString();
                System.out.println(name);
                System.out.println(quantity);
                System.out.println(uspinner);
                replyIntent.putExtra(EXTRA_REPLY, name);
                replyIntent.putExtra(EXTRA_REPLY2, quantity);
                replyIntent.putExtra(EXTRA_REPLY3, uspinner);
                setResult(RESULT_OK, replyIntent);
            }

            finish();
        });
    }
}
