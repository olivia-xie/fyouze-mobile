package com.example.socialmediaconsolidator.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialmediaconsolidator.R;

public class UsernameActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private Button okButton;
    private String username;
    private String name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        usernameEditText = findViewById(R.id.usernameEditTextId);
        okButton = findViewById(R.id.okUsernameButtonId);

        name = getIntent().getStringExtra("userName");
        email = getIntent().getStringExtra("userEmail");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameEditText.getText().toString();
                Intent intent = new Intent(UsernameActivity.this, FeedActivity.class);
                intent.putExtra("usersName", name);
                intent.putExtra("usersEmail", email);
                intent.putExtra("loggedUsername", username);

                startActivity(intent);
                finish();

            }
        });
    }
}
