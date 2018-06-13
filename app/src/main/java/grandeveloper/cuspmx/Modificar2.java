package grandeveloper.cuspmx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//By: Gutierrez Merida Cristhian David


public class Modificar2 extends AppCompatActivity {

    private static final String LOGTAG="INFO";
    EditText cNombre,cApellido,cNumCuenta;
    Button btnGuardar;



    //Objeto tipo Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog pbProgreso;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar2);

        cNombre = (EditText) findViewById(R.id.etMNombre);
        cApellido = (EditText) findViewById(R.id.etMApellido);
        cNumCuenta = (EditText) findViewById(R.id.etMNumCuenta);
        btnGuardar = (Button) findViewById(R.id.btnMEnviar);


        //Llenar datos con los de la base de datos

        mAuth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    mStorage = FirebaseStorage.getInstance().getReference();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child(getResources().getString(R.string.bdUsuario));
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            cNombre.setText(String.valueOf(dataSnapshot.child(getResources().getString(R.string.bdNombre)).getValue()));
                            cApellido.setText(String.valueOf(dataSnapshot.child(getResources().getString(R.string.bdApellidos)).getValue()));
                            cNumCuenta.setText(String.valueOf(dataSnapshot.child(getResources().getString(R.string.bdCuenta)).getValue()));

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    startActivity(new Intent(Modificar2.this, Modificar.class));
                    finish();
                }
            }


        };

        pbProgreso = new ProgressDialog(this);
        pbProgreso.setIndeterminate(true);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cNumCuenta.length()!=9){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeNum),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", "El numero de cuenta no es de 9 digitos");
                }
                if (cNombre.length()==0){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeNom),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", "Nombre invalido");

                }
                if (cApellido.length()==0){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeApell),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", "Apellido invalido");

                }

                if (cNumCuenta.length()==9 && cNombre.length()==0 && cApellido.length()==0) {
                    final String nombre = cNombre.getText().toString();
                    final String apellidos = cApellido.getText().toString();
                    final String cuenta = cNumCuenta.getText().toString();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child(getResources().getString(R.string.bdUsuario));
                    DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                    pbProgreso.setMessage(getResources().getString(R.string.mfDatos));
                    pbProgreso.show();
                    currentUserDB.child(getResources().getString(R.string.bdNombre)).setValue(nombre);
                    currentUserDB.child(getResources().getString(R.string.bdApellidos)).setValue(apellidos);
                    currentUserDB.child(getResources().getString(R.string.bdCuenta)).setValue(cuenta);
                    Intent intent = new Intent(Modificar2.this, IniciarSesion.class);
                    startActivity(intent);
                    finish();
                    Log.i("INFO", "Datos modificados correctamente");
                }
                else{
                    Log.i("INFO", "Existe un problema con los datos proporcionados por el usuario");
                }

            }
        });





    }


}
