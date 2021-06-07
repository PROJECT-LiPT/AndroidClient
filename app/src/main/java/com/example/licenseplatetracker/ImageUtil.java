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
                return "Cao Bằng";
            case 12:
                return "Lạng Sơn";
            case 14:
                return "Quảng Ninh";

            case 15:
                return "Hải Phòng";
            case 16:
                return "Hải Phòng";
            case 17:
                return "Thái Bình";
            case 18:
                return "Nam Định";
            case 19:
                return "Phú Thọ";
            case 20:
                return "Thái Nguyên";
            case 21:
                return "Yên Bái";
            case 22:
                return "Tuyên Quang";
            case 23:
                return "Hà Giang";
            case 24:
                return "Lào Cai";
            case 25:
                return "Lai Châu";
            case 26:
                return "Sơn La";
            case 27:
                return "Điện Biên";
            case 28:
                return "Hòa Bình";
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 40:
                return "Hà Nội";
            case 34:
                return "Hải Dương";
            case 35:
                return "Ninh Bình";
            case 36:
                return "Thanh Hóa";
            case 37:
                return "Nghệ An";
            case 38:
                return "Hà Tĩnh";
            case 43:
                return "Đà Nẵng";
            case 47:
                return "ĐắkLak";
            case 48:
                return "Đắc Nông";
            case 49:
                return "Lâm Đồng";
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
                return "Hồ Chí Minh";
            case 39:
            case 60:
                return "Đồng Nai";
            case 61:
                return "Hà Tĩnh";
            case 62:
                return "Long An";
            case 63:
                return "Tiền Giang";
            case 64:
                return "Vĩnh Long";
            case 65:
                return "Cần Thơ";
            case 66:
                return "Đồng Tháp";
            case 67:
                return "An Giang";
            case 68:
                return "Kiên Giang";
            case 69:
                return "Cà Mau";
            case 70:
                return "Tây Ninh";
            case 71:
                return "Bến Tre";
            case 72:
                return "Vũng Tàu";
            case 73:
                return "Quảng Bình";
            case 74:
                return "Quảng Trị";
            case 75:
                return "Huế";
            case 76:
                return "Quảng Ngãi";
            case 77:
                return "Bình Định";
            case 78:
                return "Phú Yên";
            case 79:
                return "Khánh Hòa";
            case 80:
                return "Cơ quan nhà nước";
            case 81:
                return "Gia Lai";
            case 82:
                return "Kon Tum";
            case 83:
                return "Sóc Trăng";
            case 84:
                return "Trà Vinh";
            case 85:
                return "Ninh Thuận";
            case 86:
                return "Bình Thuận";
            case 88:
                return "Vĩnh Phúc";
            case 89:
                return "Hưng Yên";
            case 90:
                return "Hà Nam";
            case 92:
                return "Quảng Nam";
            case 93:
                return "Bình Phước";
            case 94:
                return "Bạc Liêu";
            case 95:
                return "Hậu Giang";
            case 97:
                return "Bắc Cạn";
            case 98:
                return "Bắc Giang";
            case 99:
                return "Bắc Ninh";
            default:
                return "Invalid Plate Number";
        }
    }
}