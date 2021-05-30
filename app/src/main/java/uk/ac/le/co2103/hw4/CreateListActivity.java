package uk.ac.le.co2103.hw4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CreateListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CreateListActivity.class.getSimpleName();
    private static final int RESULT_LOAD_IMAGE=20;
    public static final String EXTRA_REPLY = "uk.ac.le.co2103.hw4.REPLY";
    public static final String EXTRA_IMAGE = "uk.ac.le.co2103.hw4.IMAGE";
    private static int XY = 10;
    private Uri uri;

    ImageView imageToUpload;
    Button bUploadImage;
    EditText uploadImageName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_list_activity);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        bUploadImage = findViewById(R.id.bUploadImage);
        uploadImageName = findViewById(R.id.etUploadName);

        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                XY = 15;
                break;
            case R.id.bUploadImage:
                if (XY == 15) {
                    //converting imageview to bitmap
                    imageToUpload.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable) imageToUpload.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    System.out.println(bitmap + " bitmap ");
                    //converting bitmap to uri
                    uri = getImageUri(this,bitmap);
                }
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(uploadImageName.getText())) {
                    Log.d(TAG, "onClick: Result is empty");
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String item = uploadImageName.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, item);
                    if (XY == 10) {
                        replyIntent.putExtra(EXTRA_IMAGE,"null");
                    } else{
                        if (uri != null) {
                            replyIntent.putExtra(EXTRA_IMAGE,uri.toString());
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: Clicked");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
