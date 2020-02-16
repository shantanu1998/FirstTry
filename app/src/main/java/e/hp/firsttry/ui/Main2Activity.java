package e.hp.firsttry.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import e.hp.firsttry.MainActivity;
import e.hp.firsttry.R;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private Button Register;
    private EditText Email;
    private EditText pass;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference DB;
    Users users;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        Register = (Button) findViewById(R.id.Register);
        Email = (EditText) findViewById(R.id.editEmail);
        pass = (EditText) findViewById(R.id.editPass);
        Register.setOnClickListener(this);
        users=new Users();
       // DatabaseReference Ref = DB.getReference("message");
        DB= FirebaseDatabase.getInstance().getReference().child("Users");




    }
    private void RegisterUser(){
        final String email = Email.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"please enter the email",Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please enter the password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("registering User");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Main2Activity.this,"registered successfully",Toast.LENGTH_SHORT).show();





                        }
                        else {
                            Toast.makeText(Main2Activity.this,"not registered",Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {
        RegisterUser();

        Intent intent = new Intent(this, MainActivity.class);
        users.setEmail(Email.getText().toString().trim());
        users.setPassword(pass.getText().toString().trim());
        DB.push().setValue(users);

        Toast.makeText(Main2Activity.this,"Data inserted succesfully",Toast.LENGTH_SHORT).show();


        startActivity(intent);
    }
}
