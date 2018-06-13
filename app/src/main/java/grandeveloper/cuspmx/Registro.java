package grandeveloper.cuspmx;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//By: Gutierrez Merida Cristhian David

public class Registro extends AppCompatActivity {
    private static final String LOGTAG="INFO";
    EditText cNombre,cApellido,cNumCuenta,cCorreo,cContraseña;
    CheckBox cGenero1,cGenero2,cFacultad1,cFacultad2,cFacultad3,cFacultad4,cFacultad5;
    Button btnEnviar;
    int valGenero,valFacultad;
    Alumno alumno;
    String genero,facultad;
    Context contexto=this;
    VerificarEmail verifica;


    //Objeto tipo Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog pbProgreso;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        cNombre = (EditText) findViewById(R.id.etNombre);
        cApellido = (EditText) findViewById(R.id.etApellido);
        cNumCuenta = (EditText) findViewById(R.id.etNumCuenta);
        cCorreo = (EditText) findViewById(R.id.etCorreo);
        cContraseña = (EditText) findViewById(R.id.etContraseña);
        cGenero1 = (CheckBox) findViewById(R.id.cbGenero1);
        cGenero2 = (CheckBox) findViewById(R.id.cbGenero2);
        cFacultad1 = (CheckBox) findViewById(R.id.cbFacultad1);
        cFacultad2 = (CheckBox) findViewById(R.id.cbFacultad2);
        cFacultad3 = (CheckBox) findViewById(R.id.cbFacultad3);
        cFacultad4 = (CheckBox) findViewById(R.id.cbFacultad4);
        cFacultad5 = (CheckBox) findViewById(R.id.cbFacultad5);
        btnEnviar = (Button) findViewById(R.id.bnEnviar);

        //Inicializamos el FirebvaseAuth
        mAuth = FirebaseAuth.getInstance();
        pbProgreso = new ProgressDialog(this);
        pbProgreso.setIndeterminate(true);


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valGenero=0;
                valFacultad=0;

                //Valida Nombre
                if(cNombre.length()==0){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeNom),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", getResources().getString(R.string.mensajeNom));

                }

                //Valida Apellido
                if(cApellido.length()==0){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeApell),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", getResources().getString(R.string.mensajeApell));

                }

                //Valida Numero de cuenta
                if(cNumCuenta.length()!=9){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeNum),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", getResources().getString(R.string.mensajeNum));

                }

                //Valida Correo
                verifica = new VerificarEmail(cCorreo.getText().toString());
                if(verifica.verifica()!=true){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeCorreo),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", getResources().getString(R.string.mensajeCorreo));

                }

                //Valida Contraseña (minimo 6 caracteres)
                if(cContraseña.length()<6){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeContraseña),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", getResources().getString(R.string.mensajeContraseña));
                }

                //Valida Genero
                if(cGenero1.isChecked()==true){
                    valGenero++;
                    genero=cGenero1.getText().toString();
                }
                if(cGenero2.isChecked()==true){
                    valGenero++;
                    genero=cGenero2.getText().toString();
                }
                if(valGenero!=1){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeGenero),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", getResources().getString(R.string.mensajeGenero));

                }


                //Valida Facultad
                if(cFacultad1.isChecked()==true){
                    valFacultad++;
                    facultad=cFacultad1.getText().toString();
                }
                if(cFacultad2.isChecked()==true){
                    valFacultad++;
                    facultad=cFacultad2.getText().toString();
                }
                if(cFacultad3.isChecked()==true){
                    valFacultad++;
                    facultad=cFacultad3.getText().toString();
                }
                if(cFacultad4.isChecked()==true){
                    valFacultad++;
                    facultad=cFacultad4.getText().toString();
                }
                if(cFacultad5.isChecked()==true){
                    valFacultad++;
                    facultad=cFacultad5.getText().toString();
                }

                if(valFacultad!=1){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.mensajeFac),Toast.LENGTH_SHORT).show();
                    Log.i("INFO", getResources().getString(R.string.mensajeFac));

                }


                //Toast Alumno y LOGTAG
                if(valGenero==1 && valFacultad==1 && cNumCuenta.length()==9 && cContraseña.length()>=6 && cNombre.length()!=0 && cApellido.length()!=0 && verifica.verifica()==true){
                    alumno = new Alumno(cNombre.getText().toString(),cApellido.getText().toString(),
                            Long.parseLong(cNumCuenta.getText().toString()),cCorreo.getText().toString(),
                            cContraseña.getText().toString(),genero,facultad,contexto);

                    Toast.makeText(getApplicationContext(),alumno.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),alumno.toString(),Toast.LENGTH_LONG).show();
                    Log.i("INFO", alumno.toString());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("INFO", "Usuario registrado");
                        }
                    },5000); // will trigger your code after 5 seconds


                    //Registrando Usuario
                    final String nombre = cNombre.getText().toString();
                    final String apellidos = cApellido.getText().toString() ;
                    final String cuenta = cNumCuenta.getText().toString();
                    final String email = cCorreo.getText().toString();
                    final String contraseña = cContraseña.getText().toString();
                    final String generoR = genero;
                    final String facultadR = facultad;
                    pbProgreso.setMessage(getResources().getString(R.string.mRegistro));
                    pbProgreso.show();
                    mAuth.createUserWithEmailAndPassword(email,contraseña)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pbProgreso.dismiss();
                                    if (task.isSuccessful()){
                                        mAuth.signInWithEmailAndPassword(email,contraseña);
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(getResources().getString(R.string.bdUsuario));
                                        DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                                        currentUserDB.child(getResources().getString(R.string.bdNombre)).setValue(nombre);
                                        currentUserDB.child(getResources().getString(R.string.bdApellidos)).setValue(apellidos);
                                        currentUserDB.child(getResources().getString(R.string.bdEmail)).setValue(email);
                                        currentUserDB.child(getResources().getString(R.string.bdGenero)).setValue(generoR);
                                        currentUserDB.child(getResources().getString(R.string.bdFacultad)).setValue(facultadR);
                                        currentUserDB.child(getResources().getString(R.string.bdCuenta)).setValue(cuenta);
                                    }

                                    else{
                                        Toast.makeText(Registro.this,R.string.eRegistro,Toast.LENGTH_LONG).show();
                                        Log.i("INFO", getResources().getString(R.string.eRegistro));

                                    }

                                }
                            });
                }
                else{
                    Log.i(LOGTAG, "Usuario no registrado, por datos incorrectos");
                }
            }
        });

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Intent intent = new Intent(Registro.this, IniciarSesion.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
    }

}
