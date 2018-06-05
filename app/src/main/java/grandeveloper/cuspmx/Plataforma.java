package grandeveloper.cuspmx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

//By: Gutierrez Merida Cristhian David


public class Plataforma extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog pbProgreso;
    Button btnOut;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plataforma);
        btnOut = (Button) findViewById(R.id.btnSalir);
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null)
                    mAuth.signOut();
                startActivity(new Intent(Plataforma.this, IniciarSesion.class));
                finish();
            }
        });

        pbProgreso = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };


    }
}
