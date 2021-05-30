package uk.ac.le.co2103.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateProductActivity extends AppCompatActivity {

    //UPDATE THE ITEM.

    public static final String EXTRA_REPLY1 = "uk.ac.le.co2103.hw4.REPLY1";
    public static final String EXTRA_REPLY2 = "uk.ac.le.co2103.hw4.REPLY2";
    public static final String EXTRA_REPLY3 = "uk.ac.le.co2103.hw4.REPLY3";
    public static final String EXTRA_REPLY4 = "uk.ac.le.co2103.hw4.REPLY4";

    EditText editName;
    TextView editQuantity,spinnerText;
    Button button, inc, dec;
    int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product_activity);

        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.quantityEditText);
        spinnerText = findViewById(R.id.spinnerText);
        button = findViewById(R.id.saveButton);
        inc = findViewById(R.id.increment);
        dec = findViewById(R.id.decrement);


        String object = getIntent().getStringExtra(EXTRA_REPLY4);

        String name = getIntent().getStringExtra(EXTRA_REPLY1);
        editName.setText(name);

        String quantity = getIntent().getStringExtra(EXTRA_REPLY2);
        editQuantity.setText(quantity);

        String spinner = getIntent().getStringExtra(EXTRA_REPLY3);
        spinnerText.setText(spinner);


        count=Integer.parseInt(quantity);

        inc.setOnClickListener(v -> {
            count++;
            editQuantity.setText(count + " ");
        });
        dec.setOnClickListener(v -> {
            count--;
            editQuantity.setText(count + " ");
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY1, "" + editName.getText());
                replyIntent.putExtra(EXTRA_REPLY2, String.valueOf(count));
                replyIntent.putExtra(EXTRA_REPLY3, spinner);
                replyIntent.putExtra(EXTRA_REPLY4, object);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

    }
}
