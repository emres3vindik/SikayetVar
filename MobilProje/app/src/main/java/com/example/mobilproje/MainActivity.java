package com.example.mobilproje;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_password);

        com.google.android.material.button.MaterialButton girisButton = findViewById(R.id.girisButon);
        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        TextView kayitText = findViewById(R.id.kayitText);
        kayitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistrationActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFields();
    }

    public void togglePasswordVisibility(View view) {
        EditText passwordEditText = (EditText) view;
        if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlertDialog("Lütfen e-posta ve şifrenizi giriniz.");
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        boolean dogrulandi = dbHelper.kullaniciDogrula(email, password);

        if (dogrulandi) {
            startLoggedInActivity();
        } else {
            showAlertDialog("E-posta veya şifre yanlış.");
        }
    }

    private void startLoggedInActivity() {
        Intent intent = new Intent(MainActivity.this, GirisYapActivity.class);
        startActivity(intent);
    }

    private void startRegistrationActivity() {
        Intent intent = new Intent(MainActivity.this, KayitOlActivity.class);
        startActivity(intent);
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message);
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetFields() {
        emailEditText.setText("");
        passwordEditText.setText("");
    }
}
