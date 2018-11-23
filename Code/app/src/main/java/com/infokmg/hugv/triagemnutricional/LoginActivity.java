package com.infokmg.hugv.triagemnutricional;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.infokmg.hugv.triagemnutricional.DAO.FirebaseConfig;
import com.infokmg.hugv.triagemnutricional.model.Nutritionist;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    private FirebaseAuth firebaseAuth;
    private Nutritionist nutritionist;
    private TextView tvOpenRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvOpenRegister = (TextView) findViewById(R.id.tvOpenRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtEmail.getText().toString().equals("") && !edtPass.getText().toString().equals("")) {
                    nutritionist = new Nutritionist();
                    nutritionist.setEmail(edtEmail.getText().toString());
                    nutritionist.setPassword(edtPass.getText().toString());
                    validateLogin();
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha os campos Email e Senha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvOpenRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

    }

    public void validateLogin(){
        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(nutritionist.getEmail(), nutritionist.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    openSplashScreen();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Email ou Senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openMainActivity(){
        Intent intentOpenMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentOpenMainActivity);
    }

    public void openSplashScreen(){
        Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
        intent.putExtra("id", nutritionist.getEmail().replace('.','_'));
        startActivity(intent);
        finish();
    }

    public void openRegisterActivity(){
        Intent intentOpenRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentOpenRegisterActivity);
    }

}
