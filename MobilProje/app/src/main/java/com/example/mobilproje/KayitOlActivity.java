package com.example.mobilproje;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class KayitOlActivity extends AppCompatActivity {

    private EditText adEditText, soyadEditText, mailEditText, sifreEditText;
    private RadioGroup cinsiyetRadioGroup;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol2);

        databaseHelper = new DatabaseHelper(this);

        initializeViews();
        setupButtonClick();
    }

    private void initializeViews() {
        adEditText = findViewById(R.id.ad);
        soyadEditText = findViewById(R.id.soyad);
        mailEditText = findViewById(R.id.mailkayit);
        sifreEditText = findViewById(R.id.sifrekayit);
        cinsiyetRadioGroup = findViewById(R.id.cinsiyetRadioGroup);
    }

    private void setupButtonClick() {
        findViewById(R.id.kayitButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayitOl();
            }
        });
    }

    private void kayitOl() {
        String ad = adEditText.getText().toString().trim();
        String soyad = soyadEditText.getText().toString().trim();
        String mail = mailEditText.getText().toString().trim();
        String sifre = sifreEditText.getText().toString().trim();

        if (TextUtils.isEmpty(ad) || TextUtils.isEmpty(soyad) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(sifre)) {
            showAlertDialog("Lütfen tüm alanları doldurun");
            return;
        }

        int selectedRadioButtonId = cinsiyetRadioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId == -1) {
            showAlertDialog("Lütfen cinsiyet seçin");
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String cinsiyet = selectedRadioButton.getText().toString();

        CheckBox uyelikCheckbox = findViewById(R.id.uyelikCb);
        if (!uyelikCheckbox.isChecked()) {
            showAlertDialog("Üyelik sözleşmesini kabul etmelisiniz.");
            return;
        }

        boolean isSuccess = databaseHelper.kullaniciEkle(ad, soyad, mail, sifre, cinsiyet);

        if (isSuccess) {
            showSuccessDialog();
        } else {
            showAlertDialog("Kayıt sırasında bir hata oluştu.");
        }
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Tamam", null)
                .show();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Başarılı!")
                .setMessage("Kayıt başarıyla tamamlandı.")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navigateToMainActivity();
                    }
                })
                .show();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
