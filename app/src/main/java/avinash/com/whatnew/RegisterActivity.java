package avinash.com.whatnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    String TAG="register";
    EditText name,pass;
    FirebaseDatabase database;
    DatabaseReference myRef;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.NameeditText);
        pass=findViewById(R.id.PasseditText);
        getSupportActionBar().setTitle("Register Activity");


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
            case R.id.userList_menu:
                startActivity(new Intent(getApplicationContext(),UserListActivity.class));
                return  true;
            case R.id.register_menu:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public void Register(View view) {

        final String Dname=name.getText().toString();
        final String Dpass=pass.getText().toString();


        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Log.i(TAG, "Auth "+user.getUid());

        final FirebaseDatabase fd;
        DatabaseReference dr;
        fd=FirebaseDatabase.getInstance();
        dr=fd.getReference("User Detail").child(user.getUid());
        Log.i(TAG, "checkUniqueUser: "+dr.getKey());


        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("User Detail");


        if(dr.getKey().equals(user.getUid())){
            dr  = database.getReference("User Detail").child(user.getUid().toString());
            dr.child("Paasword").setValue(Dpass);
            dr.child("Name").setValue(Dname);
            Toast.makeText(getApplicationContext(),"User Updated",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),UserListActivity.class));

        }else{
            database = FirebaseDatabase.getInstance();
            myRef  = database.getReference("User Detail").child(user.getUid().toString()).push();
            myRef.child("Paasword").setValue(Dpass);
            myRef.child("Name").setValue(Dname);
            Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),UserListActivity.class));
            }
            }
}

