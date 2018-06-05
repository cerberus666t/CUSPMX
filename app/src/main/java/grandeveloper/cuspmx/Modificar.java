package grandeveloper.cuspmx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


//By: Gutierrez Merida Cristhian David

public class Modificar extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog pbProgreso;
    EditText usuario,password;
    Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        usuario = (EditText) findViewById(R.id.etLUsuario);
        password = (EditText) findViewById(R.id.etLContraseña);
        enviar = (Button) findViewById(R.id.btnEnviar);

        mAuth = FirebaseAuth.getInstance();
        pbProgreso = new ProgressDialog(this);
        pbProgreso.setIndeterminate(true);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(Modificar.this, getResources().getString(R.string.isIngresoID) + firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Modificar.this, Modificar2.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    private void doLogin() {
        String emailL = usuario.getText().toString().trim();
        String passwordL = password.getText().toString().trim();

        if (!TextUtils.isEmpty(emailL) && !TextUtils.isEmpty(passwordL)) {
            pbProgreso.setMessage(getResources().getString(R.string.isEspera));
            pbProgreso.show();
            mAuth.signInWithEmailAndPassword(emailL, passwordL)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pbProgreso.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(Modificar.this, getResources().getString(R.string.isIngreso), Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(Modificar.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
