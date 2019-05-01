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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void Register(View view) {

        String Dname=name.getText().toString();
        String Dpass=pass.getText().toString();

        database = FirebaseDatabase.getInstance();
       myRef  = database.getReference("User Detail").push();
       myRef.child("Paasword").setValue(Dpass);
       myRef.child("Name").setValue(Dname);
        startActivity(new Intent(getApplicationContext(),UserListActivity.class));

        Log.i(TAG, "Register: Working");
    }
}
