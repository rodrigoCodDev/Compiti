package com.rcoddev.compiti.ui.authentication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
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

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    // TODO: organizar

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intentResult = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn
                            .getSignedInAccountFromIntent(intentResult);

                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        loginGoogleWithCredentials(account.getIdToken());

                    } catch (ApiException e) {
                        Toast.makeText(this, "Erro ao logar", Toast.LENGTH_LONG).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("token")
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

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

    public void loginGoogle(View view) {
        Intent signInIntent = gsc.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }

    public void register(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginGoogleWithCredentials(String token) {
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);

        user.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, MainActivity.class);

                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Falha ao logar", Toast.LENGTH_LONG).show();
            }
        });
    }
}