package com.example.sql_r2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etName, etCell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etCell = findViewById(R.id.etCell);
    }

    public void btnCreate(View v)
    {

        String name = etName.getText().toString().trim();
        String cell = etCell.getText().toString().trim();

        try {
            ContactDB db = new ContactDB(this);
            db.open();

            db.insert(name, cell);
            Toast.makeText(this, "Data inserted successfully.", Toast.LENGTH_SHORT).show();
            db.close();

            etName.setText("");
            etCell.setText("");

        }catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void btnDelete(View v)
    {
        try {
            ContactDB db = new ContactDB(this);
            db.open();
            db.deleteContact("1");
            Toast.makeText(this, "Data Deleted successfully.", Toast.LENGTH_SHORT).show();
            db.close();
        }catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void btnEdit(View v)
    {
        try {
            ContactDB db = new ContactDB(this);
            db.open();
            db.updateContact("2", "Waqas","03123345679");
            Toast.makeText(this, "Data updated successfully.", Toast.LENGTH_SHORT).show();
            db.close();
        }catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void btnView(View v)
    {
        Intent intent = new Intent(MainActivity.this, ViewData.class);
        startActivity(intent);
    }
}