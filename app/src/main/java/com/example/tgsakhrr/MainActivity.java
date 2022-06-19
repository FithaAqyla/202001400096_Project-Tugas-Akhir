package com.example.tgsakhrr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.adapter.AlertDialogManager;
import com.example.database.DatabaseHelper;
import com.example.session.SessionManager;
import com.example.tgsakhrr.R;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    TextView btnRegister;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        txtUsername = findViewById(R.id.loginusername);
        txtPassword = findViewById(R.id.LoginPassword);

        btnLogin = findViewById(R.id.buttonLGN);
        btnRegister = findViewById(R.id.txRegistrasi);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                try {
                    // Check if username, password is filled
                    if (username.trim().length() > 0 && password.trim().length() > 0) {
                        dbHelper.open();

                        if (dbHelper.Login(username, password)) {
                            session.createLoginSession(username);

                            finish();
                            Intent i = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(i);

                        } else {
                            alert.showAlertDialog(MainActivity.this, "Login gagal..", "Email atau Password salah!", false);

                        }
                    } else {
                        alert.showAlertDialog(MainActivity.this, "Login gagal..", "Form tidak boleh kosong!", false);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Regis.class);
                startActivity(i);
            }
        });
    }
    public static void setWindowFlag(MainActivity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Apakah Anda ingin keluar dari aplikasi?");
        builder.setCancelable(true);
        builder.setNegativeButton(getString(R.string.batal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(getString(R.string.keluar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}