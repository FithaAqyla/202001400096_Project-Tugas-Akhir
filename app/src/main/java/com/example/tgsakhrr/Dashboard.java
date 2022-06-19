package com.example.tgsakhrr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adapter.AlertDialogManager;
import com.example.session.SessionManager;

public class Dashboard extends AppCompatActivity {

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(Dashboard.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }
    public void profileMenu(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void historyMenu(View v) {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void booking(View v) {
        Intent i = new Intent(this, BookingActivity.class);
        startActivity(i);
    }

    public void bookHotel(View v) {
        Toast.makeText(getApplicationContext(), "Selamat Datang Di Aplikasi Booking Tiket.", Toast.LENGTH_LONG).show();
    }
}