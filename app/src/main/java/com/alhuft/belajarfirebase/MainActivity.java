package com.alhuft.belajarfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button logout, add;
    private EditText edit;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listView);
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
                   // FirebaseDatabase.getInstance().getReference().child("Languages").push().child("n5").setValue(txt_name);
                    FirebaseDatabase.getInstance().getReference().child("Languages").child("n5").setValue(txt_name);
                    Toast.makeText(MainActivity.this, "Added Success!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Informasi");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ammount = snapshot.child("email").getValue(String.class);
                    String brand = snapshot.child("name").getValue(String.class);
                    Log.d("TAG",ammount + " + " + brand);
                    Information info = snapshot.getValue(Information.class);
                    String text = info.getName() + " : " + info.getEmail();
                    list.add(text);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("capital", false);
        db.collection("cities").document("JSR").set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "merge successfull", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Tokyo");
        data2.put("capital", "Japan");
        db.collection("cities").add(data2).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(MainActivity.this, "plos", Toast.LENGTH_SHORT).show();
            }
        });
//        Map<String, Object> city = new HashMap<>();
//        city.put("name", "A");
//        city.put("state", "Jharkhan");
//        city.put("country", "London");
//
//        db.collection("cities").document("JSR").set(city).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "values added", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


}