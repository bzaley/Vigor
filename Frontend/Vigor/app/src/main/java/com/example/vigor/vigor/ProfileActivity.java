package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;

public class ProfileActivity extends AppCompatActivity {

    private SessionController session;
    private static int PICK_IMAGE = 100;
    private static int REQUEST_CAMERA = 1, SELECT_FILE = 0;

    private Context context = this;

    ImageView picture;
    Uri ImageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionController(getApplicationContext());

        TextView name = (TextView) findViewById(R.id.ProfileTvUserName);
        name.setText(session.returnFirstName() + " " + session.returnLastName());

        TextView email = (TextView) findViewById(R.id.ProfileTvEmail);
        email.setText(session.returnEmail());

        picture = (ImageView) findViewById(R.id.ProfileIvUserImage);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageGallery();
            }
        });


    }

    private void openImageGallery(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder alert = new AlertDialog.Builder(
                ProfileActivity.this);
        alert.setTitle("Set Image");
        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[which].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        alert.show();

//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, 100, 100);
                picture.setImageBitmap(bitmap1);
            } else if (requestCode == SELECT_FILE){
                Uri uri = data.getData();
//                picture.setMaxHeight(20);
//                picture.setMaxWidth(20);
                picture.setImageURI(uri);
            }
        }
    }
}
