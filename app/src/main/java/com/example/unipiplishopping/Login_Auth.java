package com.example.unipiplishopping;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class Login_Auth extends AppCompatActivity {
    EditText emailText,passwordText;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setLocale();
        emailText = findViewById(R.id.editTextText);
        passwordText = findViewById(R.id.editTextTextPassword);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
    public void signin(View view){
        if (!emailText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()){
            auth.signInWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                user = auth.getCurrentUser();
                                Intent intent = new Intent(Login_Auth.this, MainActivity.class);
                                intent.putExtra("customerEmail",user.getEmail());
                                startActivity(intent);
                                showMessage(getString(R.string.successTitle),getString(R.string.successMessageStart));
                            }else {
                                showMessage(getString(R.string.failureTitle),getString(R.string.failureMessageInvalid));
                            }
                        }
                    });
        }else {
            showMessage(getString(R.string.errorTitle),getString(R.string.errorFillFields));
        }

        //auth.signOut();
    }
    public void setLocale(){
        findViewById(R.id.button4).setOnClickListener(view -> {
            Locale currentLocale=Locale.getDefault();
            if(currentLocale.getLanguage().equals("el"))
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
            else if (currentLocale.getLanguage().equals("en")) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("fr"));
            } else if (currentLocale.getLanguage().equals("fr")) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("el"));
            }else{
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
            }
        });
    }
    public void signup(View view){
        if (!emailText.getText().toString().isEmpty()
                && !passwordText.getText().toString().isEmpty()){
            auth.createUserWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                user = auth.getCurrentUser();
                                showMessage(getString(R.string.successTitle),getString(R.string.userCreated));
                            }else {
                                showMessage(getString(R.string.failureTitle),task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }else {
            showMessage(getString(R.string.errorTitle),getString(R.string.errorFillFields));
        }
    }
    void showMessage(String title, String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
    }
}
