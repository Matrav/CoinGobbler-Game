package com.example.coingobbler;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public String username = "admin";
    public String password = "admin";
    private FirebaseAuth mAuth;
    public static float sumTotal;
    private TextView jokeTextView;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        jokeTextView = findViewById(R.id.jokeTextViewOnScreen);
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api.chucknorris.io/jokes/random";

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful())
                {
                    final String myResponse = response.body().string();


                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(jokeTextView==null)
                            {
                                try {
                                    JSONObject ask = new JSONObject(myResponse);
                                    String value = ask.get("value").toString();
                                    Toast.makeText(LoginActivity.this, value,
                                            Toast.LENGTH_LONG).show();
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Log.e("response",myResponse);


                            }else{

                            jokeTextView.setText(myResponse);
                            }
                        }
                    });

                }
            }
        });

        Log.d("oncreate:",mAuth.toString());
        setContentView(R.layout.activity_login);
        Button SubmitButton = findViewById(R.id.logInButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked !","clicked !");
                EditText usernameView= findViewById(R.id.usernameBox);
                EditText passView= findViewById(R.id.passwordBox);
                String userpass = passView.getText().toString();
                String userid = usernameView.getText().toString();
                if(userid.isEmpty()==false && userpass.isEmpty()==false) {

                mAuth.signInWithEmailAndPassword(userid, userpass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Login:", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user!=null){
                                        openNoteActivity();
                                    }
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Login", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });}
            }
        });
        Button RegisterButton = findViewById(R.id.signUpButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameView= findViewById(R.id.usernameBox);
                EditText passView= findViewById(R.id.passwordBox);
                String userpass = passView.getText().toString();
                String userid = usernameView.getText().toString();
                if(userpass.equals("")||userid.equals("")){
                    Toast.makeText(LoginActivity.this, "Email or password missing.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(userid, userpass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Register:", "createUserWithEmail:success");
                                    Toast.makeText(LoginActivity.this, "SignUp successful !",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user!=null){
                                        openNoteActivity();
                                    }
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Register:", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(MainActivity.this, "Authentication failed.",
                                    //       Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if(currentUser!=null) {
            Log.d("onStart:", currentUser.toString());

            //openNoteActivity();
        }
    }
    public void openNoteActivity(){
        Intent intent = new Intent(this, AndroidLauncher.class);
        //Intent intent = new Intent(v.getContext(), EditNoteActivity.class);
        startActivity(intent);
    }
}
