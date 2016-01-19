package com.eetc.aestec.aestec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eetc.aestec.aestec.client.AestecClient;
import com.eetc.aestec.aestec.client.AppException;
import com.eetc.aestec.aestec.entity.*;

/**
REgister activity */
public  class RegisterActivity extends AppCompatActivity  {

    private final static String TAG = RegisterActivity.class.getName();
    private View mPasswordView;
    private View mUserView;
    private View mEmailView;
    private User usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences prefs = getSharedPreferences("Aestec-profile",
                Context.MODE_PRIVATE);
        */


        // Uncomment the next two lines to test the application without login
        // each time
        // username = "alicia";
        // password = "alicia";
       /* if ((username != null) && (password != null)) {
            Intent intent = new Intent(this, Aestec_activity.class);
            startActivity(intent);
            finish();
        }
        */
        setContentView(R.layout.activity_register);
        mPasswordView=(EditText) findViewById(R.id.password);
          }

    public void signUp(View v) throws AppException{
        EditText Username = (EditText) findViewById(R.id.name);
        EditText Password = (EditText) findViewById(R.id.password);
        EditText C_Password = (EditText) findViewById(R.id.c_password);
        EditText Email = (EditText) findViewById(R.id.email);
        EditText c_Email = (EditText) findViewById(R.id.confirm_email);

        String username = Username.getText().toString();
        String password = Password.getText().toString();
        String cpassword = C_Password.getText().toString();
        String cemail = c_Email.getText().toString();
        String email = Email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if ((username.isEmpty()==true)|| email.isEmpty() == true || password.isEmpty() == true) {
            setError("Fill the required parameters");
        }
        else {



                if (password.equals(cpassword)) {
                    if (email.equals(cemail)) {

                        reg(username, password, email);
                    } else {
                        setError(getString(R.string.mismatch_email));
                    }
                } else {
                    setError(getString(R.string.mismatch_password));
                }
            }

    }

    protected User reg(String u, String p, String e) throws AppException
    {
        System.out.println("user    "+ "  p"+p+u);


        User u1;
        AestecClient client = AestecClient.getInstance(RegisterActivity.this);
        System.out.println("user    "+ "  p"+"em");

        u1 =client.register(u, p, e);
        return u1;

    }

    protected void onPostExecute(User ul) {


        if (ul.isLoginOK()==true) {
            // startActivity(new Intent(LoginActivity.this, StingsListActivity.class));

            startE();
        }
         else if(ul.getName()==null)
        {
            setError("Username already exists");
            mUserView.requestFocus();
        }
        else {
            //TODO : notificar a usuario sobre el error
           setError("Failed to Register");
            mEmailView.requestFocus();
        }
    }
    private void startE() {
        Intent intent = new Intent(this, Aestec_activity.class);
        startActivity(intent);
        finish();
    }
    private void setError(String err){
        String mensaje=err;
        Toast.makeText(this, mensaje,
                Toast.LENGTH_LONG).show();

    }

}

