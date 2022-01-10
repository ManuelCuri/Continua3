package com.example.continua3.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.example.continua3.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductEntry {
    private static final String TAG = ProductEntry.class.getSimpleName();

    public final String title;
    public final Uri dynamiUrl;
    public final String url;
    public final String price;
    public final String description;

    public ProductEntry(String title, String dynamiUrl, String url, String price, String description){
        this.title=title;
        this.dynamiUrl=Uri.parse(dynamiUrl);
        this.url = url;
        this.price = price;
        this.description = description;
    }

    public static List<ProductEntry> initProductEntryList (Resources resources){
        InputStream inputStream = resources.openRawResource(R.raw.products);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1){
                writer.write(buffer, 0 , pointer);
            }
        }catch (IOException exception){
            Log.e(TAG, "Hubo un error fatal en el archivo Json", exception);
        }finally {
            try {
                inputStream.close();
            }catch (IOException exception){
                Log.e(TAG, "Hubo un problema", exception);
            }
        }

        String jsonProductsString = writer.toString();
        Gson gson = new Gson();
        Type productListType = new TypeToken<ArrayList<ProductEntry>>(){
        }.getType();

        return gson.fromJson(jsonProductsString, productListType);
    }
}
