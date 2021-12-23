package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd, buttonViewAll;
    TextView id;
    EditText editName, editAge;
    Switch switchIsActive;
    RecyclerView recyclerViewStudent;
    LinearLayoutManager linearLayoutManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "End", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_laptop:
                        Toast.makeText(getApplicationContext(), "Laptop is clicked", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_voice:
                        Toast.makeText(getApplicationContext(), "Voice is clicked", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_chrome_reader:
                        Toast.makeText(getApplicationContext(), "Chrome Reader is clicked", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonViewAll = findViewById(R.id.buttonViewAll);
        id = findViewById(R.id.idMain);
        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        switchIsActive = findViewById(R.id.switchStudent);
        recyclerViewStudent = findViewById(R.id.recyclerViewStudent);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            StudentModel studentModel;

            @Override
            public void onClick(View v) {
                if (id.getText().toString() == "") {
                    try {
                        studentModel = new StudentModel(0, editName.getText().toString(), Integer.parseInt(editAge.getText().toString()), switchIsActive.isChecked());
                        Toast.makeText(MainActivity.this, studentModel.toString(), Toast.LENGTH_SHORT).show();
                        editName.setText("");
                        editAge.setText("");
                        switchIsActive.setChecked(false);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    dbHelper.addStudent(studentModel);
                } else {
                    try {
                        studentModel = new StudentModel(Integer.valueOf(id.getText().toString()), editName.getText().toString(), Integer.parseInt(editAge.getText().toString()), switchIsActive.isChecked());
                        Toast.makeText(MainActivity.this, studentModel.toString(), Toast.LENGTH_SHORT).show();
                        editName.setText("");
                        editAge.setText("");
                        switchIsActive.setChecked(false);
                        id.setText("");
                        buttonAdd.setText("Add");
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    if (dbHelper.updateStudent(studentModel)) {
                        seeAllRecords();
                    }
                }
            }
        });

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeAllRecords();
            }
        });


    }

    public void seeAllRecords() {
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        List<StudentModel> list = dbHelper.getAllStudents();
        Adapter adapter = new Adapter(MainActivity.this, list);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewStudent.setLayoutManager(linearLayoutManager);
        recyclerViewStudent.setAdapter(adapter);
    }
}