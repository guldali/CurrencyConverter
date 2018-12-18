package com.example.gldali.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
   TextView tl,usd,gbp;

   //manifestden internete bağlanıp veri cekmek için izin verdik
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tl=findViewById(R.id.txt_tl);
        usd=findViewById(R.id.txt_usd);
        gbp=findViewById(R.id.txt_gbp);
    }

    public  void  getir(View view){
        DownloadData veriGetir = new DownloadData();
        try {
            String url="http://data.fixer.io/api/latest?access_key=e09cb1afcad403841118cc2f6852fcb5";
            veriGetir.execute(url);

        }catch (Exception ex){

        }

    }
    private  class  DownloadData extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {//sayısı belli değil istediğin kadar parametre gönder string olacak
            String sonuc = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();//HttpURLConnection a çevirdik
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inReader = new InputStreamReader(inputStream);
                int veri= inReader.read();
                while (veri>0){
                    sonuc +=(char)veri;
                    veri=inReader.read();
                }
                return sonuc;
            }
            catch (Exception ex){
                return null;

            }

        }
        @Override
        protected  void  onPostExecute(String s){
            super.onPostExecute(s);
            //System.out.println("veri"+s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String rates = jsonObject.getString("rates");//rates apıdeki json objesi

                JSONObject kurlar = new JSONObject(rates);
                tl.setText(kurlar.getString("TRY"));
                usd.setText(kurlar.getString("USD"));
                gbp.setText(kurlar.getString("GBP"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
//currency converter
