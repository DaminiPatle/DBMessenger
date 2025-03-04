//package com.example.dbmessenger;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class registration extends AppCompatActivity {
//    TextView loginbut;
//    EditText rg_username,rg_email,rg_password,rg_repassword;
//    Button rg_signup;
//    CircleImageView rg_profileImg;
//    FirebaseAuth auth;
//    Uri imageURI;
//    String imageuri;
//    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    FirebaseDatabase database;
//    FirebaseStorage storage;
//    ProgressDialog progressDialog;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//        progressDialog =new ProgressDialog(this);
//        progressDialog.setMessage("Establishing The Account");
//        progressDialog.setCancelable(false);
//
//        getSupportActionBar().hide();
//        database =FirebaseDatabase.getInstance();
//        storage =FirebaseStorage.getInstance();
//        auth = FirebaseAuth.getInstance();
//        loginbut =findViewById(R.id.loginbut);
//        rg_username =findViewById(R.id.rgusername);
//        rg_email =findViewById(R.id.rgEmail);
//        rg_password =findViewById(R.id.rgPassword);
//        rg_repassword =findViewById(R.id.rgPassword);
//        rg_profileImg =findViewById(R.id.profileimgg);
//        rg_signup =findViewById(R.id.signupbutton);
//
//        loginbut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(registration.this, login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        rg_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String namee =rg_username.getText().toString();
//                String emaill =rg_email.getText().toString();
//                String Password =rg_password.getText().toString();
//                String cPassword =rg_repassword.getText().toString();
//                String status ="Hey I'm using this Application";
//                if(TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword) ){
//                    progressDialog.dismiss();
//                    Toast.makeText(registration.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
//                }else if (!emaill.matches(emailPattern)){
//                    progressDialog.dismiss();
//                    rg_email.setError("Type A Valid Email Here");
//                }else if (Password.length()<6){
//                    progressDialog.dismiss();
//                    rg_password.setError("Password Must Be 6Characters or More");
//                }else if (!Password.equals(cPassword)){
//                    progressDialog.dismiss();
//                    rg_password.setError("The Password Doesn't Match");
//                }else {
//                    auth.createUserWithEmailAndPassword(emaill,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()){
//                                String id =task.getResult().getUser().getUid();
//                                DatabaseReference reference =database.getReference().child("user").child(id);
//                                StorageReference storageReference =storage.getReference().child("Upload").child(id);
//
//                                if (imageURI != null) {
//                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                            if (task.isSuccessful()){
//                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                    @Override
//                                                    public void onSuccess(Uri uri) {
//                                                        imageuri =uri.toString();
//                                                        Users users = new Users(id,namee,emaill,Password,cPassword,status);
//                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                if (task.isSuccessful()){
//                                                                    progressDialog.show();
//                                                                    Intent intent =new Intent(registration.this,MainActivity.class);
//                                                                    startActivity(intent);
//                                                                    finish();
//                                                                }else {
//                                                                    Toast.makeText(registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
//                                                    }
//                                                });
//                                            }
//
//                                        }
//                                    });
//                                }else {
//                                    String status ="Hey I'm using this Application";
//                                    imageuri ="https://firebasestorage.googleapis.com/v0/b/dbmessenger-a6923.appspot.com/o/man.png?alt=media&token=bcebeb80-dc4f-4332-b226-c7647388784e";
//                                    Users users =new Users(id,namee,emaill,Password,imageuri,status);
//                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                progressDialog.show();
//                                                Intent intent =new Intent(registration.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            }else {
//                                                Toast.makeText(registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                }
//                            }else {
//                                Toast.makeText(registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//
//
//            }
//        });
//
//        rg_profileImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10)   ;
//
//            }
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==10){
//            if (data != null) {
//                imageURI =data.getData();
//                rg_profileImg.setImageURI(imageURI);
//            }
//        }
//    }
//}



package com.example.dbmessenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity {
    TextView loginbut;
    EditText rg_username, rg_email, rg_password, rg_repassword;
    Button rg_signup;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;
    Uri imageURI;
    String imageuri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Your Account...");
        progressDialog.setCancelable(false);

        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        loginbut = findViewById(R.id.loginbut);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgEmail);
        rg_password = findViewById(R.id.rgPassword);
        rg_repassword = findViewById(R.id.rgPassword2); // Corrected reference for re-enter password field
        rg_profileImg = findViewById(R.id.profileimgg);
        rg_signup = findViewById(R.id.signupbutton);

        loginbut.setOnClickListener(v -> {
            Intent intent = new Intent(registration.this, login.class);
            startActivity(intent);
            finish();
        });

        rg_signup.setOnClickListener(v -> {
            String namee = rg_username.getText().toString();
            String emaill = rg_email.getText().toString();
            String Password = rg_password.getText().toString();
            String cPassword = rg_repassword.getText().toString();
            String status = "Hey I'm using this Application";

            if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword)) {
                Toast.makeText(registration.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
            } else if (!emaill.matches(emailPattern)) {
                rg_email.setError("Type A Valid Email Here");
            } else if (Password.length() < 6) {
                rg_password.setError("Password Must Be 6 Characters or More");
            } else if (!Password.equals(cPassword)) {
                rg_password.setError("The Password Doesn't Match");
            } else {
                progressDialog.show(); // Show progress dialog before starting registration process

                // Check if the email is already registered
                auth.fetchSignInMethodsForEmail(emaill).addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().getSignInMethods().isEmpty()) {
                        // If email is already registered
                        progressDialog.dismiss();
                        Toast.makeText(registration.this, "This Email is already registered!", Toast.LENGTH_SHORT).show();
                    } else {
                        // If email is available, proceed with signup
                        auth.createUserWithEmailAndPassword(emaill, Password).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                String id = task1.getResult().getUser().getUid();
                                DatabaseReference reference = database.getReference().child("user").child(id);
                                StorageReference storageReference = storage.getReference().child("Uploads").child(id);

                                if (imageURI != null) {
                                    // Upload image if selected
                                    storageReference.putFile(imageURI).addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                                imageuri = uri.toString();
                                                saveUserToDatabase(id, namee, emaill, Password, cPassword, status, imageuri, reference);
                                            });
                                        }
                                    });
                                } else {
                                    // Use default image if no image is selected
                                    imageuri = "https://firebasestorage.googleapis.com/v0/b/dbmessenger-a6923.appspot.com/o/man.png?alt=media&token=bcebeb80-dc4f-4332-b226-c7647388784e";
                                    saveUserToDatabase(id, namee, emaill, Password, cPassword, status, imageuri, reference);
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(registration.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        rg_profileImg.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
        });
    }

    private void saveUserToDatabase(String id, String namee, String emaill, String password, String cPassword, String status, String imageuri, DatabaseReference reference) {
        Users users = new Users(id, namee, emaill, password, imageuri, status);
        reference.setValue(users).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Intent intent = new Intent(registration.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            imageURI = data.getData();
            rg_profileImg.setImageURI(imageURI);
        }
    }
}


