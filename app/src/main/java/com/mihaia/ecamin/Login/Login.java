package com.mihaia.ecamin.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.LoginAsyncTask;
import com.mihaia.ecamin.DataContracts.User;
import com.mihaia.ecamin.PaginaPrincipala;
import com.mihaia.ecamin.R;
import com.mihaia.ecamin.Utils;
import com.mihaia.ecamin.localDB.UtilizatoriDB;

import static com.mihaia.ecamin.localDB.DBHelper.context;

public class Login extends AppCompatActivity {

    Button butContNou, butLogin;
    EditText eTextUser, eTextPass;
    TextView tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        butContNou = (Button) findViewById(R.id.butNewUser);
        butLogin = (Button) findViewById(R.id.butLogin);
        eTextPass = (EditText) findViewById(R.id.editTextPass);
        eTextUser = (EditText) findViewById(R.id.editTextUser);
        tvStatus = (TextView) findViewById(R.id.tv_login_status);

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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
        LoginAsyncTask verificaDate = new LoginAsyncTask(this, this) {
            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                if(user != null) {
                    Intent intent = new Intent(context, PaginaPrincipala.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Utils.CurrentUser, getServer_response());
                    resetForm();
                    context.startActivity(intent);
                }
                else
                {
                    tvStatus.setVisibility(View.VISIBLE);
                    tvStatus.setText(R.string.user_parola_gresit);
                    resetForm();
                }
            }
        };
        verificaDate.execute(eTextUser.getText().toString(), eTextPass.getText().toString());
    }

    private void contNou(){

    }

    private void resetForm() {
        tvStatus.setText("");
        tvStatus.setVisibility(View.GONE);

        eTextPass.setText("");
        eTextUser.setText("");
    }
}
