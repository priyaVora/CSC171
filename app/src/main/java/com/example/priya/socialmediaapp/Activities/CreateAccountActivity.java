package com.example.priya.socialmediaapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.priya.socialmediaapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText firstNameView;
    private EditText lastNameView;
    private EditText emailView;
    private EditText password;
    private Button createAccountButton;
    private Button goBackButton;
    private ImageButton profileImageButton;

    private DatabaseReference mDatabaseReferences;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mCreateAccountDialog;
    private StorageReference mFirebaseStorage;

    private final static int GALLERYCODE = 1;
    private Uri resultUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firstNameView = findViewById(R.id.firstNameViewLogin);
        lastNameView = findViewById(R.id.lastNameViewLogin);
        emailView = findViewById(R.id.email);
        password = findViewById(R.id.passwordViewLogin);
        profileImageButton = findViewById(R.id.profileImageLoginScreen);
        createAccountButton = findViewById(R.id.createAccountButton);
        goBackButton = findViewById(R.id.goBackButton);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                finish();
            }
        });


        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReferences = mDatabase.getReference().child("MUser");
        mFirebaseStorage = FirebaseStorage.getInstance().getReference().child("MBlog_Profile_Pics");
        mAuth = FirebaseAuth.getInstance();
        mCreateAccountDialog = new ProgressDialog(this);




        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountBtn();
            }
        });

        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                startActivityForResult(galleryIntent, GALLERYCODE);
            }
        });

    }

        public void createAccountBtn() {
            final String fName = firstNameView.getText().toString().trim();
            final String lName = lastNameView.getText().toString().trim();
            final String em = emailView.getText().toString().trim();
            final String pwd = password.getText().toString().trim();

            if(!TextUtils.isEmpty(fName) && !TextUtils.isEmpty(lName) &&
                    !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pwd)) {
                mCreateAccountDialog.setMessage("Creating Account...");
                mCreateAccountDialog.show();
                Log.d("AuthMessage","Before Creating account...");

                if(mAuth != null) {
                    mAuth.createUserWithEmailAndPassword(em, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(authResult != null) {

                                Log.d("profileImageMessage", "Auth Result is not null");
                                StorageReference imagePath = mFirebaseStorage.child("MBlog_Profile_Pics")
                                        .child(resultUri.getLastPathSegment());
                                Log.d("profileImageMessage", "Image Path storage");
                                imagePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        String userid = mAuth.getCurrentUser().getUid();

                                        DatabaseReference currentUserDb = mDatabaseReferences.child(userid);
                                        currentUserDb.child("image").setValue(resultUri.toString());
                                        Log.d("profileImageMessage", "Image is set.");
                                        currentUserDb.child("firstname").setValue(fName);
                                        Log.d("profileImageMessage", "First name is set.");
                                        currentUserDb.child("lastname").setValue(lName);
                                        Log.d("profileImageMessage", "Last name is set.");
                                        mCreateAccountDialog.dismiss();



                                        //Send users to postlist
//                                        Intent intent = new Intent(CreateAccountActivity.this, PostListActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //we usually use finish()
//
//                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(CreateAccountActivity.this, "The mAUth is null", Toast.LENGTH_LONG).show();
                                Log.d("profileImageMessage", "mAuth is null.");
                            }
                        }
                    });
                } else {
                        Log.d("AuthMessage", "Auth is null: Firebase.Auth not found");
                }










                Log.d("AuthMessage","After");

            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AuthMessage","Activity Result Called...");
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERYCODE && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();
            CropImage.activity(mImageUri)
                    .setAspectRatio(1,1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(CreateAccountActivity.this);
        }


        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {
                resultUri = result.getUri();
                profileImageButton.setImageURI(resultUri);
            } else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
