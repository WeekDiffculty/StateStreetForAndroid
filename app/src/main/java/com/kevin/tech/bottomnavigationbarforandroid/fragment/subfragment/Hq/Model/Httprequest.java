package com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.Hq.Model;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.StandardConstants;

/**
 * Created by bf on 2017/3/1.
 */

public class Httprequest {


    public  Hqmodel requestListHq(String symbol) {

        String baseString = "http://119.23.130.36:777/?query=price&type=jsonret&symbol=";

        System.out.println(baseString+symbol);
        Log.i("", "requestListHq: "+baseString+symbol);
        HttpURLConnection httpsURLConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(baseString+","+symbol);
            Log.i("", "requestListHq: "+url+"HTTP");
            httpsURLConnection = (HttpURLConnection) url.openConnection();
            httpsURLConnection.setReadTimeout(3000);
            httpsURLConnection.setConnectTimeout(3000);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == 200){
                inputStream = httpsURLConnection.getInputStream();

                StringBuffer   out   =   new   StringBuffer();
                byte[]   b   =   new   byte[4096];
                for   (int   n;   (n   =   inputStream.read(b))   !=   -1;)   {
                    out.append(new   String(b,   0,   n));
                }
                String string = out.toString();
                return  getHqmodel(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (httpsURLConnection != null){
                httpsURLConnection.disconnect();
            }
            if (inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private Hqmodel getHqmodel(String s){
        if (s ==null||s.length()<25){
            return null;
        }
        s = s.replace("jsonret({'list':[{","");//去头
        s = s.replace("}]})","");//去尾
        s = s.replace("'","");//去 '
        String[] stringarray = s.split(",");
        Hqmodel hqmodel = new Hqmodel();
        hqmodel.setPrince(stringarray[1].split(":")[1]);
        hqmodel.setTime(timesOne(stringarray[2].split(":")[1]));
        hqmodel.setSymbol(stringarray[0].split(":")[1]);
        System.out.println(hqmodel);
        Log.i("", "getHqmodel: "+hqmodel.toString());
        return hqmodel;
    }
    //时间戳转换
    public String timesOne(String time) {
         SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH：mm：ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
}
