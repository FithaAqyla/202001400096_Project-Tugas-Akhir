package com.example.tgsakhrr;

import static com.example.tgsakhrr.MainActivity.setWindowFlag;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DatabaseHelper;

public class Regis extends AppCompatActivity {

    EditText txtUserame, txtEmail, txtPassword;
    Button btnDaftar;
    TextView Login;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String Username, Email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 31) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        txtUserame = findViewById(R.id.rgsUsername);
        txtEmail = findViewById(R.id.regisemail);
        txtPassword = findViewById(R.id.regisPassword);

        btnDaftar = findViewById(R.id.buttonR);
        Login = findViewById(R.id.txLOGIN);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = txtUserame.getText().toString();
                Email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                try {
                    if (Email.trim().length() > 0 && password.trim().length() > 0 && Username.trim().length() > 0) {
                        dbHelper.open();
                        dbHelper.Register(Email, password, Username);
                        Toast.makeText(Regis.this, "Daftar berhasil", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(Regis.this, "Daftar gagal, lengkapi form!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Regis.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static void setWindowFlag(Regis activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}