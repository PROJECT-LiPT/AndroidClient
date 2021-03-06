package com.example.licenseplatetracker;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil
{
    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String ret = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return ret.replace("\n", "").replace("\r","");
    }

    public static String CallAPI(Bitmap bitmap){
        String base64 = convert(bitmap);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://smartmekong.vn/prediction/");
        //HttpPost httppost = new HttpPost("https://webhook.site/abf00715-b6d5-40d2-8690-5573e1235018");
        try {
            // Add your data
            String json = "{\"id\":\"0000\",\"uploader\":\"AndroidApp\",\"imgUrl\":\"data:image/png;base64,"+base64+"\"}";
            httppost.setEntity(new StringEntity(json));
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            Log.i("HTTP_CODE", String.valueOf(response.getStatusLine().getStatusCode()));
            String ret = EntityUtils.toString(response.getEntity());
            JSONObject jRes = new JSONObject(ret);
            String op;
            try {
                op = jRes.getString("title");
                op = "Plate Number: " + op + "\n" + "Origin: "+findCity(op) + "\n";
                op += "Processing time: "+jRes.getString("process").substring(0,5) + "s\n";
            } catch (Exception e){
                op = "Error! License Plate Not Found!!!";
            }
            return op;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
    }

    public static String findCity(String n){
        int ii = Integer.parseInt(n.substring(0,2));
        switch (ii) {
            case 11:
                return "Cao B???ng";
            case 12:
                return "L???ng S??n";
            case 14:
                return "Qu???ng Ninh";

            case 15:
                return "H???i Ph??ng";
            case 16:
                return "H???i Ph??ng";
            case 17:
                return "Th??i B??nh";
            case 18:
                return "Nam ?????nh";
            case 19:
                return "Ph?? Th???";
            case 20:
                return "Th??i Nguy??n";
            case 21:
                return "Y??n B??i";
            case 22:
                return "Tuy??n Quang";
            case 23:
                return "H?? Giang";
            case 24:
                return "L??o Cai";
            case 25:
                return "Lai Ch??u";
            case 26:
                return "S??n La";
            case 27:
                return "??i???n Bi??n";
            case 28:
                return "H??a B??nh";
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 40:
                return "H?? N???i";
            case 34:
                return "H???i D????ng";
            case 35:
                return "Ninh B??nh";
            case 36:
                return "Thanh H??a";
            case 37:
                return "Ngh??? An";
            case 38:
                return "H?? T??nh";
            case 43:
                return "???? N???ng";
            case 47:
                return "?????kLak";
            case 48:
                return "?????c N??ng";
            case 49:
                return "L??m ?????ng";
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 41:
                return "H??? Ch?? Minh";
            case 39:
            case 60:
                return "?????ng Nai";
            case 61:
                return "H?? T??nh";
            case 62:
                return "Long An";
            case 63:
                return "Ti???n Giang";
            case 64:
                return "V??nh Long";
            case 65:
                return "C???n Th??";
            case 66:
                return "?????ng Th??p";
            case 67:
                return "An Giang";
            case 68:
                return "Ki??n Giang";
            case 69:
                return "C?? Mau";
            case 70:
                return "T??y Ninh";
            case 71:
                return "B???n Tre";
            case 72:
                return "V??ng T??u";
            case 73:
                return "Qu???ng B??nh";
            case 74:
                return "Qu???ng Tr???";
            case 75:
                return "Hu???";
            case 76:
                return "Qu???ng Ng??i";
            case 77:
                return "B??nh ?????nh";
            case 78:
                return "Ph?? Y??n";
            case 79:
                return "Kh??nh H??a";
            case 80:
                return "C?? quan nh?? n?????c";
            case 81:
                return "Gia Lai";
            case 82:
                return "Kon Tum";
            case 83:
                return "S??c Tr??ng";
            case 84:
                return "Tr?? Vinh";
            case 85:
                return "Ninh Thu???n";
            case 86:
                return "B??nh Thu???n";
            case 88:
                return "V??nh Ph??c";
            case 89:
                return "H??ng Y??n";
            case 90:
                return "H?? Nam";
            case 92:
                return "Qu???ng Nam";
            case 93:
                return "B??nh Ph?????c";
            case 94:
                return "B???c Li??u";
            case 95:
                return "H???u Giang";
            case 97:
                return "B???c C???n";
            case 98:
                return "B???c Giang";
            case 99:
                return "B???c Ninh";
            default:
                return "Invalid Plate Number";
        }
    }
}