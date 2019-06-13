package avinash.com.whatnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    private static final String TAG ="userList" ;
    ListView lV;
    ArrayAdapter arrayAdapter;
    ArrayList<String>user=new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        lV=findViewById(R.id.listView);
        tv=findViewById(R.id.testingtextView);

        getSupportActionBar().setTitle("User List");
        Log.i(TAG, "onCreate: USerList");
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ChatActivity.class);

                intent.putExtra("username",user.get(position));
                startActivity(intent);
            }
        });

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i(TAG, "Checking For USER  "+ currentuser );

        user.add("First Added Contact");
        ReadData();

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,user);

        lV.setAdapter(arrayAdapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_menu:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            case R.id.register_menu:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                return true;
            case R.id.group_menu:
                startActivity(new Intent(getApplicationContext(),GroupActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void ReadData(){
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("User Detail");

        final FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv.setText(dataSnapshot.toString());
                Log.i(TAG, "onDataChange: uSER UID");
               //ser=F
                Log.i(TAG, "Current USer"+database);
                Log.i(TAG, "Data snapshot"+userAuth.getUid());//current user
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    Log.i(TAG, "i Value"+" "+i.getKey());
                    if (!dataSnapshot.child(userAuth.getUid()).getKey().equals(i.getKey()) ) {
                        // User is signed in
                        user.add(i.child("Name").getValue().toString());
                    } else {
                        // No user is signed in
                        Toast.makeText(getApplicationContext(),"Will Create Problem",Toast.LENGTH_SHORT).show();
                    }
                    tv.setText(i.child("Name").getValue().toString());
                    Log.i(TAG, "ALL Names "+i.child("Name").getValue().toString());

                    }

                }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Failed to read value.", databaseError.toException());
                Toast.makeText(getApplicationContext(),"Failed to read value." ,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
