package avinash.com.whatnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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



        user.add("First Added Contact");
        ReadData();

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,user);

        lV.setAdapter(arrayAdapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.register_menu:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void ReadData(){
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("User Detail");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // tv.setText(dataSnapshot.toString());
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    tv.setText(i.child("Name").getValue().toString());
                    Log.i(TAG, "ALL Names "+i.child("Name").getValue().toString());
                    user.add(i.child("Name").getValue().toString());


                }
//                      String value=dataSnapshot.getValue(String.class);
//                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
//                     User value=dataSnapshot1.getValue(User.class);
//                    user.add(value.toString());
//                    Log.i(TAG, "Database Retrive Data "+value);
                }

            //}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Failed to read value.", databaseError.toException());

            }
        });
    }
}
