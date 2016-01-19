package com.eetc.aestec.aestec;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eetc.aestec.aestec.client.AestecClient;
import com.eetc.aestec.aestec.client.AppException;
import com.eetc.aestec.aestec.entity.User;

import java.io.IOException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private final static String TAG = LoginActivity.class.getName();
    User us = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No enseña la barra superior

        SharedPreferences prefs = getSharedPreferences("uTroll-profile",
                Context.MODE_PRIVATE); //Sólo la aplicación puede recuperar las preferences
        String username = prefs.getString("username", null); //Recupera el usuario y contraseña almacenados
        String password = prefs.getString("password", null);

        // Uncomment the next two lines to test the application without login
        // each time
        //username = "david";
        //password = "david";

        if ((username != null) && (password != null)) { //Si usuario y contraseña no son nulos, inicia la actividad
            Intent intent = new Intent(this, Aestec_activity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
    }

    public void signIn(View v) throws AppException {
        EditText etUsername = (EditText) findViewById(R.id.email); //Obtener campos de texto de usuario y contraseña
        EditText etPassword = (EditText) findViewById(R.id.password);

        final String username = etUsername.getText().toString(); //Obtener usuario y contraseña
        final String password = etPassword.getText().toString();

        //Se debería acceder a la API y comprobar que las credenciales son correctas

// Launch a background task to check if credentials are correct
// If correct, store username and password and start uTroll activity
// else, handle error

        (new checkLoginTask()).execute(username, password);

// I'll suppose that u/p are correct:
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void startAestecActivity() {
        String urlUser = us.getLinks().get("self").getTarget();

        Intent intent = new Intent(this, Aestec_activity.class);
        intent.putExtra("url", urlUser);
        startActivity(intent);
        finish(); //Si no acabamos la actividad de Login, al darle al botón "back" en el móvil volvería a ella
    }

    private void evaluateLogin(Boolean loginOK) {
        if (loginOK) {
            EditText etUsername = (EditText) findViewById(R.id.email);
            EditText etPassword = (EditText) findViewById(R.id.password);

            final String username = etUsername.getText().toString();
            final String password = etPassword.getText().toString();

            SharedPreferences prefs = getSharedPreferences("uTroll-profile",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();

            editor.putString("username", username);
            editor.putString("password", password);
            System.out.println("USer"  + username +"    pass      "+password);
            boolean done = editor.commit();
            if (done)
                Log.d(TAG, "preferences set");
            else
                Log.d(TAG, "preferences not set. THIS A SEVERE PROBLEM");

            startAestecActivity();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "El usuario o la contraseña son incorrectos";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private class checkLoginTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pd;

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean correctLogin = false;
            try {
               us = AestecClient.getInstance(LoginActivity.this).login(params[0], params[1]);
                correctLogin =us.isLoginOK();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return correctLogin ;
        }

        @Override
        protected void onPostExecute(Boolean loginOK) {
            evaluateLogin(loginOK);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Buscando...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }


    }
}
