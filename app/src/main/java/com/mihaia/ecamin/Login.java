package com.mihaia.ecamin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mihaia.ecamin.localDB.UtilizatoriDB;

import static com.mihaia.ecamin.localDB.DBHelper.context;

public class Login extends AppCompatActivity {

    Button butContNou, butLogin;
    EditText eTextUser, eTextPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        butContNou = (Button) findViewById(R.id.butNewUser);
        butLogin = (Button) findViewById(R.id.butLogin);
        eTextPass = (EditText) findViewById(R.id.editTextPass);
        eTextUser = (EditText) findViewById(R.id.editTextUser);

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // login();
                Intent intent = new Intent(context, PaginaPrincipala.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        butContNou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contNou();
            }
        });
    }

    private void login(){
        LoginAsyncTask verificaDate = new LoginAsyncTask(getApplicationContext()) {
            @Override
            protected UtilizatoriDB.UtilizatorModel doInBackground(String... params) {
                return utilizatoriDB.getUtilizator(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(UtilizatoriDB.UtilizatorModel utilizatorModel) {
                super.onPostExecute(utilizatorModel);

                if(utilizatorModel != null) {
                    Intent intent = new Intent(context, PaginaPrincipala.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, R.string.error_login_failed, Toast.LENGTH_SHORT);
                }
            }
        };
        verificaDate.execute(eTextUser.getText().toString(), eTextPass.getText().toString());
    }

    private void contNou(){
      LoginAsyncTask verificaDate = new LoginAsyncTask(getApplicationContext()) {
           @Override
           protected UtilizatoriDB.UtilizatorModel doInBackground(String... params) {
               return utilizatoriDB.insertUtilizator(params[0], params[1]);
           }

           @Override
           protected void onPostExecute(UtilizatoriDB.UtilizatorModel utilizatorModel) {
               super.onPostExecute(utilizatorModel);
               Toast.makeText(context, "Cont " + utilizatorModel.getUser() + "creat cu succes!", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(context, PaginaPrincipala.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
           }
       };
       verificaDate.execute(eTextUser.getText().toString(), eTextPass.getText().toString());
    }
}
