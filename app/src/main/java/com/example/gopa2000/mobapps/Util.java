package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/4/16.
 */

public class Util {
    public static String encodeTobase64(Bitmap image) {
        if(image!=null)
        {
            Bitmap immagex=image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.JPEG,10, baos);
            byte[] b = baos.toByteArray();
            String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

            Log.e("LOOK", imageEncoded);
            return imageEncoded;
        }
        return "";
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        try
        {
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String listToString(ArrayList<String> listContent){
        return "";
    }

    public static ArrayList<String> stringToList(String listcontent){
        return new ArrayList<>();
    }
}
