package com.dq.dqvaccine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dq.dqvaccine.activities.HijosActivity;
import com.dq.dqvaccine.clases.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.apache.http.client.*;
//Actividad principal donde se inicia la sesion del usuario con la cuenta de google y se confirma
// existencia en la base de datos.
public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "InicioSesion";
    private static final int RC_SIGN_IN = 9001;
    public static final String EXTRA_USUARIO_ID = "extra_usuario_id";   //Extra para HijosActivity
    private GoogleApiClient client;
    private TextView VistaEstado;
    private ImageView VistaFoto;
    private ProgressDialog mProgressDialog;
    private Usuario u;
    private String correo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vistas de texto e imagen
        VistaEstado = (TextView) findViewById(R.id.title_text);
        VistaFoto = (ImageView) findViewById(R.id.profile_pic);

        //Listeners de Botones
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.confirm_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            correo = acct.getEmail().toString();    //Correo para buscar en la base de datos
            //Muestra los datos del usuario ingresado
            VistaEstado.setText(getString(R.string.usuario, acct.getDisplayName(), acct.getEmail()));
            if (acct.getPhotoUrl() != null) {
                new DownloadImageTask(VistaFoto).execute(acct.getPhotoUrl().toString());
            } else {
                VistaFoto.setImageResource(R.drawable.ic_account_circle);
            }
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    /*Si ya esta logueado, no es necesario que se vuelva a loguear*/
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(client);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    //Cuando hace clic en uno de los botones
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:   //Iniciar sesion
                signIn();
                break;
            case R.id.sign_out_button:  //Cerrar sesion
                signOut();
                break;
            case R.id.confirm_button:   //confirmar
                confirm();
                break;
        }
    }

    private void confirm() {
        new Verificar().execute();
    }   //Confirmar verifica si esta en la bd

    //Despues de verificar, pasa a la siguiente actividad, enviando como parametro
    //el id del usuario
    private void siguiente() {
        Intent confint = new Intent(this, HijosActivity.class);
        confint.putExtra(EXTRA_USUARIO_ID, u.getId());
        startActivity(confint);
    }

    //Cierra sesion
    private void signOut() {
        Auth.GoogleSignInApi.signOut(client).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    //Inicia sesion
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(client);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Se actualiza la visibilidad de las vistas (texto, imagen, botones)
    //segun se inicie, cierre sesion.
    private void updateUI(boolean b) {
        if (b) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
            findViewById(R.id.title_text).setVisibility(View.VISIBLE);
            findViewById(R.id.profile_pic).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            findViewById(R.id.confirm_button).setVisibility(View.GONE);
            findViewById(R.id.title_text).setVisibility(View.GONE);
            findViewById(R.id.profile_pic).setVisibility(View.GONE);
        }
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    // Verifica la existencia del usuario en la base de datos mediante el servicio Rest
    private class Verificar extends AsyncTask<Void, Void, Boolean> {

        //Datos de los usuarios que se obtendran de la bd
        private int idUsu;
        private String nombUsu;
        private String mailUsu;
        AlertDialog alertDialog;

        protected void onPreExecute() {
            //Alerta en caso que no se encuentre en la bd
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        }

        protected Boolean doInBackground(Void... params) {

            boolean resul = true;

            HttpClient httpClient = new DefaultHttpClient();

            //Servicio rest
            HttpPost post =
                    new HttpPost(Utiles.Path.correoPath);


            post.setHeader("content-type", "application/json");


            //Se obtiene el JSON del usuario enviando como parametro un json con el correo
            try {
                JSONObject parm = new JSONObject();
                parm.put("correo", correo);
                StringEntity entity = new StringEntity(parm.toString());
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());


                JSONObject respJSON = new JSONObject(respStr);

                idUsu = respJSON.getInt("id");
                nombUsu = respJSON.getString("nombre");
                mailUsu = respJSON.getString("correo");

                System.out.println(nombUsu);
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                resul = false;
            }

            return resul;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                u = new Usuario(idUsu, mailUsu, nombUsu);
                siguiente();
                //Si no encontro ningun error, pasa a la siguiente actividad
            }
            else{
                //
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Correo no encontrado en la base de datos");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }

    }
}
