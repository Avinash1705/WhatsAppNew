package avinash.com.whatnew;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    String Demail,Dpass;

    private FirebaseAuth mAuth;
    private String TAG="avinash";

    @Override
    protected void onStart() {
        super.onStart();
      redirect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        email = findViewById(R.id.emailEditText);
        pass = findViewById(R.id.passEditText);

        mAuth = FirebaseAuth.getInstance();


    }

    public void Signup(View view) {


            Demail = email.getText().toString();
            Dpass = pass.getText().toString();

            mAuth.createUserWithEmailAndPassword(Demail, Dpass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                redirect();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });


    }

    public void login(View view) {

        Demail = email.getText().toString();
        Dpass = pass.getText().toString();
       // Log.i(TAG, "login: Working");
        mAuth.signInWithEmailAndPassword(Demail, Dpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithEmail:success");
                            redirect();
                           //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "signInWithEmail:failure", task.getException());
                          //  Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                  //  Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void redirect(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null){

            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        }


    }
}
