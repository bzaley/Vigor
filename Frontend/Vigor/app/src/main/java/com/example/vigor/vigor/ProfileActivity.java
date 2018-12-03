package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

/**
 * This activity allows users to view their profile and delete it if necessary
 */
public class ProfileActivity extends AppCompatActivity {

    private SessionController session;
    private String TAG = ProfileActivity.class.getSimpleName();
    private static int PICK_IMAGE = 100;
    private static int REQUEST_CAMERA = 1, SELECT_FILE = 0;

    private Button DeleteUser;
    private GoogleSignInClient mGoogleSignInClient;

    private Context context = this;

    ImageView picture;
    Uri ImageURI;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize the variables necessary
        session = new SessionController(getApplicationContext());
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Set up initial display
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

        //Listen to if the user wants to delete their profile.
        DeleteUser = (Button) findViewById(R.id.ProfileBtnDeleteUser);
        DeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {AlertDialog.Builder alert = new AlertDialog.Builder(
                    ProfileActivity.this);
                alert.setTitle("Are you sure about that?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sendUrl = "http://proj309-ad-07.misc.iastate.edu:8080/user/delete/" + session.returnUserID();
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                                sendUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error:" + error.getMessage());
                            }
                        });
                        VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "json_req");
                        session.attemptLogout();
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        finish();
                        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

    }

    /**
     * Used to open a phone's image storage to attempt to set a custom profile
     */
    private void openImageGallery() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder alert = new AlertDialog.Builder(
                ProfileActivity.this);
        alert.setTitle("Set Image");
        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[which].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        alert.show();
    }

    /**
     * Used to recieve and process image data
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, 100, 100);
                picture.setImageBitmap(bitmap1);
            } else if (requestCode == SELECT_FILE) {
                Uri uri = data.getData();
//                picture.setMaxHeight(20);
//                picture.setMaxWidth(20);
                picture.setImageURI(uri);
            }
        }
    }
}
