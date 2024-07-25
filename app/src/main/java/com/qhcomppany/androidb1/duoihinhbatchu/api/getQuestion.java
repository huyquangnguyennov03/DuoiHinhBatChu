package com.qhcomppany.androidb1.duoihinhbatchu.api;

import android.os.AsyncTask;

import com.qhcomppany.androidb1.duoihinhbatchu.Data;
import com.qhcomppany.androidb1.duoihinhbatchu.object.cauDo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;

public class getQuestion extends AsyncTask<Void, Void, Void> {

    String data;

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.56.1/DuoiHinhBatChu/layCauHoi.php")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            data = responseBody.string();
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (data != null) {
            try {
                Data.getData().arrCauDo.clear();
                JSONArray array = new JSONArray(data);
                for (int i =0; i<array.length();i++){
                    JSONObject o = array.getJSONObject(i);
                    cauDo c = new cauDo();
                    c.picture = o.optString("picture");
                    c.name = o.optString("name");
                    c.result = o.optString("result");
                    Data.getData().arrCauDo.add(c);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
        }
    }
}


