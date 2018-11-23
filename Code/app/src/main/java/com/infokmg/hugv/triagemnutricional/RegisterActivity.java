package com.infokmg.hugv.triagemnutricional;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.infokmg.hugv.triagemnutricional.DAO.FirebaseConfig;
import com.infokmg.hugv.triagemnutricional.helper.Base64Custom;
import com.infokmg.hugv.triagemnutricional.helper.Preferences;
import com.infokmg.hugv.triagemnutricional.model.Nutritionist;

public class RegisterActivity extends AppCompatActivity {


    private EditText edtCadName;
    private EditText edtCadLastName;
    private EditText edtCadCRN;
    private EditText edtCadPasswordConfirm;
    private EditText edtCadPassword;
    private EditText edtCadEmail;
    private RadioButton rbMale;
    private RadioButton rbFamale;
    private Button btnRegister;
    private Nutritionist nutritionist;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        edtCadPasswordConfirm = (EditText) findViewById(R.id.edtCadPasswordConfirm);
        edtCadPassword = (EditText) findViewById(R.id.edtCadPassword);
        edtCadName = (EditText) findViewById(R.id.edtCadName);
        edtCadLastName = (EditText) findViewById(R.id.edtCadLastName);
        edtCadCRN = (EditText) findViewById(R.id.edtCadCRN);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFamale = (RadioButton) findViewById(R.id.rbFemale);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtCadPassword.getText().toString().equals(edtCadPasswordConfirm.getText().toString())) {
                    //Toast.makeText(RegisterActivity.this, "Entrou", Toast.LENGTH_SHORT).show();

                    nutritionist = new Nutritionist();
                    nutritionist.setEmail(edtCadEmail.getText().toString());
                    nutritionist.setPassword(edtCadPassword.getText().toString());
                    nutritionist.setFirstName(edtCadName.getText().toString());
                    nutritionist.setLastName(edtCadLastName.getText().toString());
                    nutritionist.setCRN(edtCadCRN.getText().toString());
                    if(rbFamale.isChecked()){
                        nutritionist.setGenre("Feminino");
                    }else{
                        nutritionist.setGenre("Masculino");
                    }

                    nutricionistRegister();

                }else{
                    Toast.makeText(RegisterActivity.this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void nutricionistRegister(){
        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                nutritionist.getEmail(),
                nutritionist.getPassword()
        ).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userIdentifier = (nutritionist.getEmail().replace('.','_'));//Base64Custom.encodeBase64(nutritionist.getEmail());
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    nutritionist.setId(userIdentifier);
                    //nutritionist.save();

                    Preferences userPreferences = new Preferences(RegisterActivity.this);
                    userPreferences.saveUserPreferences(userIdentifier, nutritionist.getFirstName());

                    enterToApp();

                }else{
                    String erroMessage = "";

                    try{

                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        erroMessage = "Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroMessage = "Email invalido";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroMessage = "Esse email já está cadastrado no sistema";
                    }catch(Exception e){
                        erroMessage = "Erro ao efetuar o cadastro";
                    }
                    Toast.makeText(RegisterActivity.this, "Erro: "+erroMessage, Toast.LENGTH_SHORT).show();

                }
            }



        });
    }

    public void openLoginActivity(){
        Intent intentOpenLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intentOpenLoginActivity);
        finish();
    }

    public void enterToApp(){
        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(nutritionist.getEmail(), nutritionist.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    databaseReference = FirebaseConfig.getDatabaseReference();
                    databaseReference.child("nutricionists").child(nutritionist.getId()).setValue(nutritionist);
                    openSplashScreen();
                    Toast.makeText(RegisterActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openSplashScreen(){
        Intent intent = new Intent(RegisterActivity.this, SplashScreen.class);
        intent.putExtra("id", nutritionist.getEmail().replace('.','_'));
        startActivity(intent);
        finish();
    }

}