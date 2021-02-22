package com.alhuft.belajarfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button logout, add;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this,"Logout",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
        });
        ////FirebaseDatabase.getInstance().getReference().child("Belajar").child("Android").setValue("abcd");

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("Nama", "Bones");
//        map.put("Email", "justbones@gmail.com");
//
//        FirebaseDatabase.getInstance().getReference().child("Belajar").child("MultipleValues").updateChildren(map);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = edit.getText().toString();
                if (txt_name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No name entered!", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Belajar").push().child("Nama").setValue(txt_name);
                    Toast.makeText(MainActivity.this, "Added Success!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}