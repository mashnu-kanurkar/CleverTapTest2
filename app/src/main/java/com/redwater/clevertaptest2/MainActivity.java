package com.redwater.clevertaptest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.clevertap.android.sdk.CleverTapAPI;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText editName, editEmail;
    Button btnLogin, btnTestEvent;
    ImageView imgLogo;
    CleverTapAPI clevertapApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.edit_user_name);
        editEmail = findViewById(R.id.edit_email);
        btnLogin = findViewById(R.id.button_login);
        btnTestEvent = findViewById(R.id.button_test_event);
        imgLogo = findViewById(R.id.img_logo);

        clevertapApi = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        raiseProductViewed();

        loadLogo();

        btnLogin.setOnClickListener(v -> login());
        btnTestEvent.setOnClickListener(v-> raiseTestEvent());

    }

    private void raiseTestEvent() {
        clevertapApi.pushEvent("TEST");
    }

    private void raiseProductViewed() {
        clevertapApi.pushEvent("Product viewed");

    }

    private void login() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (name.equals("")){
            editName.setError("Please enter your name");

            return;
        }
        if (email.equals("")){
            editEmail.setError("Please enter your email id");
            return;
        }
        if (!email.contains("@")){
            editEmail.setError("Please Enter valid email address");
            return;
        }
        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Name", name);
        profileUpdate.put("Email", email);
        clevertapApi.onUserLogin(profileUpdate);

        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
    }

    private void loadLogo() {
        Glide.with(getApplicationContext())
                .load("https://cdn.freelogovectors.net/wp-content/uploads/2021/06/clevertap-logo-freelogovectors.net_.png")
                .into(imgLogo);
    }
}