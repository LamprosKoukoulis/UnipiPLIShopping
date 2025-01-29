package com.example.unipiplishopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class Settings extends AppCompatActivity {
    private EditText editTextName, editTextSurname, editTextNumber;
    private Button buttonChangeColor,buttonSave;
    private FirebaseUser currentUser;
    private String userId;
    public boolean isDarkMode;
    SharedPreferences preferences;
    String name, surname= "empty";
    int fontSize=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); //use activity_settings.xml
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        //Log.d("FirebaseUser",currentUser.toString());
        if(currentUser==null){
            Toast.makeText(this,"Δεν είστε συνδεδεμένος",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        applyFontSize(findViewById(android.R.id.content));

        editTextName=findViewById(R.id.editTextName);
        editTextSurname=findViewById(R.id.editTextSurname);
        editTextNumber=findViewById(R.id.editTextNumber);
        buttonSave=findViewById(R.id.buttonSave);
        buttonChangeColor=findViewById(R.id.buttonChangeColor);
        ////Get unique preferences for each user
        preferences=getSharedPreferences("user_preferences"+userId,MODE_PRIVATE);
        isDarkMode =preferences.getBoolean("darkMode",false);
        applyDarkMode();
        loadPreferences();
        buttonSave.setOnClickListener(view -> {
            String name =editTextName.getText().toString();
            String surname =editTextSurname.getText().toString();
            String fontSize=editTextNumber.getText().toString();

            if( name.isEmpty()||surname.isEmpty()||fontSize.isEmpty()){
                Toast.makeText(this,"Παρακαλώ συμπληρώστε όλα τα πεδία",Toast.LENGTH_SHORT).show();
                return;
            }
            int fontSizeInt=Integer.parseInt(fontSize);
            savePreferences(name,surname,fontSizeInt);
            loadPreferences();
            applyFontSize(findViewById(android.R.id.content));

        });
        buttonChangeColor.setOnClickListener(view -> darkMode());
    }

    private void loadPreferences(){
        name =preferences.getString("name","");
        surname =preferences.getString("surname","");
        fontSize = preferences.getInt("fontSize",20);

        editTextName.setText(name);
        editTextSurname.setText(surname);
        editTextNumber.setText(String.valueOf(fontSize));
        Log.d("SettingsActivity", "Loaded preferences: name=" + name + ", surname=" + surname + ", fontSize=" + fontSize);
    }

    private void savePreferences(String name,String surname,int fontSize){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("name",name);
        editor.putString("surname",surname);
        editor.putInt("fontSize",fontSize);
        editor.apply();
    }

    private void darkMode(){
        isDarkMode=!isDarkMode;
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("darkMode",isDarkMode);
        editor.apply();

        if(isDarkMode){
            Toast.makeText(this,"Ενεργοποιήθηκε η Νυχτερινή Λειτουργεία",Toast.LENGTH_SHORT).show();
            applyDarkMode();
        }else{
            Toast.makeText(this,"Απενεργοποιήθηκε η Νυχτερινή Λειτουργεία",Toast.LENGTH_SHORT).show();
            applyDarkMode();
        }
    }
    public void applyDarkMode(){
        preferences=getSharedPreferences("user_preferences"+userId,MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean("darkMode", false);
        if(isDarkMode){
            getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        }else{
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }
    }
    public void applyFontSize(View view) {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SharedPreferences preferences = getSharedPreferences("user_preferences" + userId, MODE_PRIVATE);
        int fontSizeInt = preferences.getInt("fontSize", 20);

        if (view instanceof TextView) {
            ((TextView) view).setTextSize(fontSizeInt);
            //I dont have to check if it's button/EditText as all widgets extend TextView
        }
        if (view instanceof ViewGroup) {
            //All items are in ContrainedLayout extends ViewGroup
            ViewGroup layout = (ViewGroup) view;
            for (int i = 0; i < layout.getChildCount(); i++) {
                View item = layout.getChildAt(i);
                applyFontSize(item);
            }
        }
    }
}
