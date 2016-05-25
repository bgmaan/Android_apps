package com.example.bgm.geekchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class SignUp_Activity extends AppCompatActivity {

    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected Button signUpButton;
    protected Firebase ref;
    protected String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_);
        Firebase.setAndroidContext(this);
        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
        signUpButton = (Button)findViewById(R.id.signupButton);

         ref = new Firebase(MyConstants.FIREBASE_URL);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                email = emailEditText.getText().toString();

                password = password.trim();
                email = email.trim();

                if (password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_Activity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
         ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>()  {
                                 @Override
                                 public void onSuccess(Map<String, Object> result) {
                                     AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_Activity.this);
                                     String userId = result.get("uid").toString();
                                     ref.child("users").child(userId).child("email").setValue(email);
                                     builder.setMessage(R.string.signup_success)
                                             .setPositiveButton(R.string.login_button_label, new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialogInterface, int i) {
                                                     Intent intent = new Intent(SignUp_Activity.this, Login_activity.class);
                                                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                     startActivity(intent);
                                                 }
                                             });
                                     AlertDialog dialog = builder.create();
                                     dialog.show();
                                 }

             @Override
             public void onError(FirebaseError firebaseError) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_Activity.this);
                 builder.setMessage(firebaseError.getMessage())
                         .setTitle(R.string.signup_error_title)
                         .setPositiveButton(android.R.string.ok, null);
                 AlertDialog dialog = builder.create();
                 dialog.show();
             }



                             });
                         }
                     }
                 });
             }
         }



