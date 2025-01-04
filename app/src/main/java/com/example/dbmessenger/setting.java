package com.example.dbmessenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class setting extends AppCompatActivity {

    private static final int SELECT_PICTURE_REQUEST_CODE = 10;

    ImageView setprofile;
    EditText setname, setstatus;
    Button donebut;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String email, password, oldImageUri;
    Uri setImageUri;
    ActivityResultLauncher<Intent> imagePickerLauncher;

    // Declare progress dialog
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        setprofile = findViewById(R.id.settingprofile);
        setname = findViewById(R.id.settingname);
        setstatus = findViewById(R.id.settingstatus);
        donebut = findViewById(R.id.donebut);

        // Initialize progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Update your profile...");
        progressDialog.setCancelable(false);

        // Initialize image picker launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        setImageUri = result.getData().getData();
                        setprofile.setImageURI(setImageUri);
                    }
                }
        );

        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
        StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    email = snapshot.child("mail").getValue(String.class);
                    password = snapshot.child("password").getValue(String.class);
                    String name = snapshot.child("Username").getValue(String.class);
                    String profile = snapshot.child("profilepic").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    oldImageUri = profile;
                    setname.setText(name);
                    setstatus.setText(status);
                    Picasso.get().load(profile).into(setprofile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        setprofile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            imagePickerLauncher.launch(Intent.createChooser(intent, "Select Picture"));
        });

        donebut.setOnClickListener(v -> {
            String name = setname.getText().toString();
            String status = setstatus.getText().toString();

            // Show the progress dialog before starting the task
            progressDialog.show();

            if (setImageUri != null) {
                storageReference.putFile(setImageUri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                            String finalimageUri = uri.toString();
                            Users users = new Users(auth.getUid(), name, email, password, finalimageUri, status);
                            reference.setValue(users).addOnCompleteListener(task1 -> {
                                progressDialog.dismiss(); // Dismiss progress dialog
                                if (task1.isSuccessful()) {
                                    Toast.makeText(setting.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(setting.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(setting.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });
                    } else {
                        progressDialog.dismiss(); // Dismiss progress dialog on failure
                        Toast.makeText(setting.this, "Image upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Users users = new Users(auth.getUid(), name, email, password, oldImageUri, status);
                reference.setValue(users).addOnCompleteListener(task -> {
                    progressDialog.dismiss(); // Dismiss progress dialog
                    if (task.isSuccessful()) {
                        Toast.makeText(setting.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(setting.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(setting.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
