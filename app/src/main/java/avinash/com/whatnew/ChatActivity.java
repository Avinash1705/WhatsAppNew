package avinash.com.whatnew;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {


    private static final String TAG ="ActiveUSer" ;
    EditText texttosend;
    Button send;
    ListView chatlistView;
    private FirebaseDatabase fd;
    DatabaseReference dr;
    private FirebaseUser user;
    private String activeUSer,chatText;
    private StorageReference mStorageRef;
    ArrayList<String> messageSave=new ArrayList<>();
    ArrayAdapter adapter;
    private DatabaseReference messageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_chat);
            texttosend=findViewById(R.id.ChateditText);
            send=findViewById(R.id.Sendbutton);
            chatlistView=findViewById(R.id.saveEnterChat);

            mStorageRef = FirebaseStorage.getInstance().getReference();

            //getSupportActionBar().setTitle("Chats");
           // Log.i(TAG, "Current Active USer"+"  "+activeUSer);//null

            user=FirebaseAuth.getInstance().getCurrentUser();
            fd=FirebaseDatabase.getInstance();
            Toast.makeText(getApplicationContext(),"Chat Here",Toast.LENGTH_SHORT).show();
            //messageSave.add("Chat Here");
            Intent intent=getIntent();
            activeUSer=intent.getStringExtra("username");
            setTitle("Chat With"+activeUSer);
            adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,messageSave);
            //fn
            showSavedList();
            chatlistView.setAdapter(adapter);


    }
//    public  void uploadfile(){
//
//        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
//
//    }

    public void showSavedList(){

        final FirebaseDatabase database;

        database = FirebaseDatabase.getInstance();


        messageRef  = database.getReference("Messages");
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                Log.i(TAG, "datasnapshot1"+dataSnapshot1.child("Message").getValue());
                try {
                    if(!dataSnapshot1.child("Message").getValue().equals(null)){
                        messageSave.add(dataSnapshot1.child("Sender").getValue() + "=>" + dataSnapshot1.child("Message").getValue().toString());

                    }
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }




    public void SendText(View view) {

        chatText =texttosend.getText().toString();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Intent intent=getIntent();
        activeUSer=intent.getStringExtra("username");
        try {
            final FirebaseUser ChatUser=FirebaseAuth.getInstance().getCurrentUser();
            // get user name of sender
           // Log.i(TAG, "chat user detail"+"   "+ChatUser.getUid());   //display name not working

            final DatabaseReference message = FirebaseDatabase.getInstance().getReference("Messages");


            dr=fd.getReference("User Detail").child(user.getUid()).child("Name");
            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //   Log.i(TAG, "checkUniqueUser: "+dataSnapshot.getValue());
                    message.child("Sender").setValue(dataSnapshot.getValue());
                    message.child("Recipient").setValue(activeUSer);
                    message.child("Message").setValue(chatText);
                    message.child("Current UId").setValue(ChatUser.getUid());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                  //  Log.i(TAG, "onCancelled: "+databaseError);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        texttosend.setText("");

    }

    public void selectPIc(View view) {

        startActivity(new Intent(getApplicationContext(),ImageActivity.class));
    }
}
