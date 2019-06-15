package avinash.com.whatnew;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.widget.CheckBox;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class GroupActivity extends AppCompatActivity {
    ArrayList<String> user=new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView listView;
    int j=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        read();
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,user);
        listView=findViewById(R.id.group_listView);
        listView.setAdapter(arrayAdapter);

        getSupportActionBar().setTitle("Groups");
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         
           final DatabaseReference groupRef = database.getReference("Group");
           
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, final long id) {


              final String name=arrayAdapter.getItem(position).toString();
                Toast.makeText(getApplicationContext(),name, LENGTH_SHORT).show();
                    groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           if(dataSnapshot.hasChild("name")){
                                          Toast.makeText(getApplicationContext(),"Value Present", LENGTH_SHORT).show();
                            } else{
                                        groupRef.push().setValue(name);

                           }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }

        });


    }
    public void read(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User Detail");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.getChildren()   ){
                    user.add(i.child("Name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
