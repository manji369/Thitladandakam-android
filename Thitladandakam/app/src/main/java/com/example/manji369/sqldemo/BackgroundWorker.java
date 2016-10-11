package com.example.manji369.sqldemo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Dell on 7/23/2016.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    Handler handler;
    String type_global;
    BackgroundWorker(Context ctx,Handler hndlr){
        context = ctx;
        handler = hndlr;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        type_global = type;
        String login_url = "http://manji369.comxa.com/login.php"; //http://31.170.160.209
        String load_url = "http://manji369.comxa.com/random_word.php";
        if(type.equals("login")){
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1")); //,"iso-8859-1"
                String result = "";
                String line;
                while((line = bufferedReader.readLine())!= null){
                    result += line;
                }
                int length = result.length();
                result = (String) result.subSequence(0,length-146);
                result = result.substring(3);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String res_obt = null;
                //JSONArray jArray = new JSONArray();
                try{

                    JSONObject json_data= new JSONObject(result);
                    //JSONObject name = json_data.getJSONObject("name");
                    res_obt = json_data.getString("res");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return res_obt;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("load")){
            try {
                URL url = new URL(load_url);
                String send_data = "refresh";
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("refresh","UTF-8")+"="+URLEncoder.encode(send_data,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1")); //,"iso-8859-1"
                String result = "";
                String line;
                while((line = bufferedReader.readLine())!= null){
                    result += line;
                }
                int length = result.length();
                result = (String) result.subSequence(0,length-146);
                result = result.substring(3);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String res_obt = null;
                /*try{

                    JSONObject json_data= new JSONObject(result);
                    res_obt = json_data.getString("res");
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        //alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String res_obt) {
        //alertDialog.setMessage(res_obt);
        //alertDialog.show();
        Message message = new Message();
        Bundle bundle = new Bundle();
        if(type_global.equals("login")) {
            bundle.putString("notify", res_obt);
        }else if(type_global.equals("load")){
            bundle.putString("word", res_obt);
        }
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}