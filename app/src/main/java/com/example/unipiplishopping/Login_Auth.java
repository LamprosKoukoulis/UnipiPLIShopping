package com.example.unipiplishopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                                showMessage("Επιτυχία","Ξεκινήστε την περιήγηση!");
                            }else {
                                showMessage("Αποτυχία","Λανθασμένα Δεδομένα");
                            }
                        }
                    });
        }else {
            showMessage("Error","Συμπληρώστε Τα Πεδία!");
        }

        //auth.signOut();
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
                                showMessage("Success","User created!");
                            }else {
                                showMessage("Error",task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }else {
            showMessage("Error","Please provide data to the fields");
        }
    }
    void showMessage(String title, String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
    }
}
