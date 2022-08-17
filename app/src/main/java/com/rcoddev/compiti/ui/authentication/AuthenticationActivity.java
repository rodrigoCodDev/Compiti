package com.rcoddev.compiti.ui.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rcoddev.compiti.R;
import com.rcoddev.compiti.databinding.ActivityAuthenticationBinding;
import com.rcoddev.compiti.databinding.ActivityMainBinding;
import com.rcoddev.compiti.ui.editor.TaskEditorActivity;
import com.rcoddev.compiti.ui.main.MainActivity;
import com.rcoddev.compiti.ui.register.RegisterActivity;

public class AuthenticationActivity extends AppCompatActivity {

    private FirebaseAuth user = FirebaseAuth.getInstance();

    private TextInputEditText editTextAuthEmail;
    private TextInputEditText editTextAuthPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        editTextAuthEmail = findViewById(R.id.editTextAuthEmail);
        editTextAuthPassword = findViewById(R.id.editTextAuthPassword);
    }

    public void login(View view) {
        String email = editTextAuthEmail.getText().toString();
        String password = editTextAuthPassword.getText().toString();

        user.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Verifica se o login deu certo
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Successful authenticating user",
                                    Toast.LENGTH_LONG).show();

                            Context context = getApplicationContext();
                            Intent intent = new Intent(context, MainActivity.class);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Failed to authenticate user",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void register(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}