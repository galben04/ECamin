package com.mihaia.ecamin.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mihaia.ecamin.AsyncTaskuri.GetInfoAsyncTask;
import com.mihaia.ecamin.AsyncTaskuri.GetMasiniAsyncTask;
import com.mihaia.ecamin.AsyncTaskuri.GetMasiniLibereAsyncTask;
import com.mihaia.ecamin.AsyncTaskuri.GetStariPlangeriAsync;
import com.mihaia.ecamin.AsyncTaskuri.LoginAsyncTask;
import com.mihaia.ecamin.DataContracts.InfoUser;
import com.mihaia.ecamin.DataContracts.Masina_Spalat;
import com.mihaia.ecamin.DataContracts.Stare_Plangere;
import com.mihaia.ecamin.DataContracts.User;
import com.mihaia.ecamin.PaginaPrincipala;
import com.mihaia.ecamin.R;
import com.mihaia.ecamin.Utils;

import java.util.Collection;

public class Login extends AppCompatActivity {

    Button butContNou, butLogin;
    EditText eTextUser, eTextPass;
    TextView tvStatus;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        butContNou = (Button) findViewById(R.id.butNewUser);
        butLogin = (Button) findViewById(R.id.butLogin);
        eTextPass = (EditText) findViewById(R.id.editTextPass);
        eTextUser = (EditText) findViewById(R.id.editTextUser);
        tvStatus = (TextView) findViewById(R.id.tv_login_status);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompletate())
                    login();
                else {
                    tvStatus.setVisibility(View.VISIBLE);
                    tvStatus.setText(R.string.user_parola_gresit);
                    resetForm();
                }

            }
        });

        butContNou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contNou();
            }
        });
    }

    private boolean isCompletate() {
        if(eTextUser.getText().toString().length() < 3)
            return false;
        if(eTextPass.getText().toString().length() < 1)
            return false;

        return  true;
    }


    private void login(){
        progressBar.setVisibility(View.VISIBLE);
        LoginAsyncTask verificaDate = new LoginAsyncTask(this, this) {
            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                if(user != null) {

                    resetForm();

                    new GetInfoAsyncTask("InformatiiUsers"){
                        @Override
                        protected void onPostExecute(InfoUser infoUser) {
                            super.onPostExecute(infoUser);

                            if(infoUser != null) {
                                Utils.infoUserLogat = infoUser;
                            }

                            Intent intent = new Intent(context, PaginaPrincipala.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Utils.CurrentUser, getServer_response());

                            progressBar.setVisibility(View.GONE);
                            resetForm();
                            context.startActivity(intent);
                        }
                    }.execute(String.valueOf(user.Id_Info));

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
        progressBar.setVisibility(View.GONE);
    }

    private void contNou(){

    }


    private void resetForm() {
        tvStatus.setText("");
        //tvStatus.setVisibility(View.GONE);

        eTextPass.setText("");
        eTextUser.setText("");
    }
}
