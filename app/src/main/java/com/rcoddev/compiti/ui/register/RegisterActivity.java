package com.rcoddev.compiti.ui.register;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rcoddev.compiti.R;
import com.rcoddev.compiti.databinding.ActivityAuthenticationBinding;
import com.rcoddev.compiti.databinding.ActivityRegisterBinding;
import com.rcoddev.compiti.model.User;
import com.rcoddev.compiti.ui.main.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth user;
    private DatabaseReference usersReference;

    private TextInputEditText editTextRegisterName;
    private TextInputEditText editTextRegisterAge;
    private TextInputEditText editTextRegisterEmail;
    private TextInputEditText editTextRegisterPassword;
    private TextInputEditText editTextRegisterConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextRegisterName = findViewById(R.id.editTextRegisterName);
        editTextRegisterAge = findViewById(R.id.editTextRegisterAge);
        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextRegisterConfirmPassword = findViewById(R.id.editTextRegisterConfirmPassword);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        user = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void register(View view) {
        String name = editTextRegisterName.getText().toString();
        String email = editTextRegisterEmail.getText().toString();
        String password = editTextRegisterPassword.getText().toString();
        String confirmPassword = editTextRegisterConfirmPassword.getText().toString();

        try {
            String ageText = editTextRegisterAge.getText().toString();
            int age = Integer.parseInt(ageText);

            boolean result = checkUserData(age, name, email, password, confirmPassword);

            Log.i("check", " " + result);

            if (result) {
                Toast.makeText(this.getApplicationContext(), "Fill in the fields correctly",
                        Toast.LENGTH_LONG).show();

                Log.i("Teste", "passei");

                return;
            }

            user.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User currentUser = new User(name, email, age);

                                String userId = user.getCurrentUser().getUid();

                                usersReference.child(userId)
                                        .setValue(currentUser)
                                        .addOnCompleteListener(event -> {
                                            Toast.makeText(getApplicationContext(),
                                                    "Success creating new user",
                                                    Toast.LENGTH_LONG).show();

                                            Context context = getApplicationContext();
                                            Intent intent = new Intent(context, MainActivity.class);

                                            startActivity(intent);
                                            finish();
                                        });

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Failed to create new user",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (NumberFormatException e) {
            Toast.makeText(this.getApplicationContext(), "Invalid number",
                    Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkUserData(int age, String name, String email,
                                  String password, String confirmPassword) {
        boolean condition = false;
        Log.i("condition 1", " " + condition);
        condition |= (name.isEmpty());
        Log.i("condition 2", " " + condition);
        condition |= (age <= 0);
        Log.i("condition 3", " " + condition);
        condition |= (email.isEmpty());
        Log.i("condition 4", " " + condition);
        condition |= (password.isEmpty());
        Log.i("condition 5", " " + condition);
        condition |= (confirmPassword.isEmpty());
        Log.i("condition 6", " " + condition);
        condition |= (!password.equals(confirmPassword));
        Log.i("condition 7", " " + condition);
        condition |= (password.length() < 6);
        Log.i("condition 8", " " + condition);

        return condition;
    }
}