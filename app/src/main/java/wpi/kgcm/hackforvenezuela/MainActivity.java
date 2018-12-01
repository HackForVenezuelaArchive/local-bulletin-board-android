package wpi.kgcm.hackforvenezuela;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ListView listView;
    TextView txtTitle;
    TextView txtBody;
    TextView txtAuthor;
    Button btn;
    LocationManager lm;

    DatabaseReference myRef;
    ArrayList<Post> arrayList = new ArrayList<>();
    PostAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0001);
        } else {
            // not granted
        }


        listView = findViewById(R.id.listView);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        btn = findViewById(R.id.btnSubmit);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        refresh();

//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                arrayList.clear();
//
//                for (DataSnapshot contact : dataSnapshot.getChildren()) {
//                    Post p = contact.getValue(Post.class);
//                    arrayList.add(p);
//                }
//                arrayAdapter = new PostAdapter(arrayList, MainActivity.this);
//                listView.setAdapter(arrayAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Post post = dataSnapshot.getValue(Post.class);
//
//                Log.d(TAG, "Value is: " + post.getTitle());
//
//                arrayList.add(post);
//                arrayAdapter = new PostAdapter(arrayList, MainActivity.this);
//                listView.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////                Post post = dataSnapshot.getValue(Post.class);
////
////                Log.d(TAG, "Title is: " + post.getTitle());
////                Log.d(TAG, "Author is: " + post.getAuthor());
////
////                arrayAdapter = new PostAdapter(arrayList, MainActivity.this);
////                listView.setAdapter(arrayAdapter);
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // if reset
                refresh();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    void refresh(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot contact : snapshot.getChildren()) {
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

    }

    public void onPress(View v) {
        Map<String, Object> updates = new HashMap<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            Toast.makeText(MainActivity.this, "Can't access location! Post denied.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        updates.put(UUID.randomUUID().toString(), new Post(txtTitle.getText().toString(),
                txtAuthor.getText().toString(), txtBody.getText().toString(), new Date().getTime(), latitude, longitude));

        myRef.updateChildren(updates);
        refresh();

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.menu_item, menu );
        return true;
    }
}
