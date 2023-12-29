package com.example.sql_r2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewData extends AppCompatActivity {

    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        tvResult = findViewById(R.id.tvResult);

        try {
            ContactDB db = new ContactDB(this);
            db.open();
            tvResult.setText(db.readAllContacts());
            db.close();
        }catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}