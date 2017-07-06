package com.mihaia.ecamin.AsyncTaskuri;

import android.content.Context;
import android.os.AsyncTask;

import com.mihaia.ecamin.localDB.UtilizatoriDB;

/**
 * Created by mihaia on 3/7/2017.
 */

public abstract class LoginAsyncTask extends AsyncTask<String, Void, UtilizatoriDB.UtilizatorModel> {

    public LoginAsyncTask(Context context) {
        this.context = context.getApplicationContext();
    }

    protected UtilizatoriDB utilizatoriDB;
    protected Context context;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        utilizatoriDB = new UtilizatoriDB(context);
        utilizatoriDB.open();
    }

    /*
    @Override
    protected Void  doInBackground(String... params) {

        if(params.length < 2);

        utilizatoriDB = new UtilizatoriDB(context);
        utilizatoriDB.open();

        utilizatoriDB.getUtilizator((String) params[0],(String) params[1]);
        return null;

    }*/
}

