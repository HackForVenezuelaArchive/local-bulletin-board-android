package wpi.kgcm.hackforvenezuela;

import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ListView listView;
    TextView txtTitle;
    TextView txtAuthor;
    Button btn;

    DatabaseReference myRef;
    ArrayList<Post> arrayList = new ArrayList<>();
    PostAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");

        listView = findViewById(R.id.listView);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtTitle = findViewById(R.id.txtTitle);
        btn = findViewById(R.id.btnSubmit);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot contact : dataSnapshot.getChildren()) {
                    Post p = contact.getValue(Post.class);
                    arrayList.add(p);
                }
                arrayAdapter = new PostAdapter(arrayList, MainActivity.this);
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(MainActivity.this, "This is my Toast message!",
                        Toast.LENGTH_LONG).show();

                Post post = dataSnapshot.getValue(Post.class);

                Log.d(TAG, "Value is: " + post.getTitle());

                arrayList.add(post);
                arrayAdapter = new PostAdapter(arrayList, MainActivity.this);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Post post = dataSnapshot.getValue(Post.class);
//
//                Log.d(TAG, "Title is: " + post.getTitle());
//                Log.d(TAG, "Author is: " + post.getAuthor());
//
//                arrayAdapter = new PostAdapter(arrayList, MainActivity.this);
//                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onPress(View v) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(UUID.randomUUID().toString(), new Post(txtTitle.getText().toString(), txtAuthor.getText().toString()));

        myRef.updateChildren(updates);

    }
}
