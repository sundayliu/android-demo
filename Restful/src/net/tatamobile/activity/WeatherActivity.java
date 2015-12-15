package net.tatamobile.activity;

import java.util.Calendar;
import java.util.Random;

import net.tatamobile.application.MarsWeather;
import net.tatamobile.request.WeatherJsonRequest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.sundayliu.demo.restful.R;
public class WeatherActivity extends Activity {
    
    
    SharedPreferences mSharedPref; 
    
    int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
     
    final static String SHARED_PREFS_IMG_KEY = "img",
    SHARED_PREFS_DAY_KEY = "day";
    
    
    
    TextView mTxtDegrees, mTxtWeather, mTxtError;
    MarsWeather helper = MarsWeather.getInstance();
    final static String RECENT_API_ENDPOINT = "http://marsweather.ingenology.com/v1/latest/";

    ImageView mImageView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        
        mTxtDegrees = (TextView) findViewById(R.id.degrees);
        mTxtWeather = (TextView) findViewById(R.id.weather);
        mTxtError = (TextView) findViewById(R.id.error);
        mImageView = (ImageView)findViewById(R.id.main_bg);
        
     // Font
        mTxtDegrees.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-light.ttf"));
        mTxtWeather.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-light.ttf"));
        
        mSharedPref = getPreferences(Context.MODE_PRIVATE);
        //searchRandomImage();
        
        //SharedPreferences.Editor editor = mSharedPref.edit();
        //editor.putInt(SHARED_PREFS_DAY_KEY, today);
        //editor.putString(SHARED_PREFS_IMG_KEY, imageUrl);
        //editor.commit();
        
        if (mSharedPref.getInt(SHARED_PREFS_DAY_KEY, 0) != today) {
            // search and load a random mars pict
            try {
                searchRandomImage();
            } catch (Exception e) {
                // please remember to set your own Flickr API!
                // otherwise I won't be able to show
                // a random Mars picture
                imageError(e);
            }
        } else {
            // we already have a pict of the day: let's load it
            loadImg(mSharedPref.getString(SHARED_PREFS_IMG_KEY, ""));
        }
        loadWeatherData();
    }
    
    protected void onResume(){
        super.onResume();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        // This will tell to Volley to cancel all the pending requests
        helper.cancel();
    }
    
    
    // Fetching Data
    private void loadWeatherData() {
        WeatherJsonRequest request = new WeatherJsonRequest
            (Request.Method.GET, RECENT_API_ENDPOINT, null, new Response.Listener<JSONObject>() {
     
                @Override
                public void onResponse(JSONObject response) {
                    try {
     
                        String minTemp, maxTemp, atmo;
                        int avgTemp;
     
                        response = response.getJSONObject("report");
     
                        minTemp = response.getString("min_temp"); minTemp = minTemp.substring(0, minTemp.indexOf("."));
                        maxTemp = response.getString("max_temp"); maxTemp = maxTemp.substring(0, maxTemp.indexOf("."));
     
                        avgTemp = (Integer.parseInt(minTemp)+Integer.parseInt(maxTemp))/2;
     
                        atmo = response.getString("atmo_opacity");
     
     
                        mTxtDegrees.setText(avgTemp+"°");
                        mTxtWeather.setText(atmo);
     
                    } catch (Exception e) {
                        txtError(e);
                    }
     
                }
            }, new Response.ErrorListener() {
     
                @Override
                public void onErrorResponse(VolleyError error) {
                    txtError(error);
                }
            });
     
        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
     
    }
    
    private void txtError(Exception e) {
        mTxtError.setVisibility(View.VISIBLE);
        e.printStackTrace();
    }
    
    // Fetching Image
    final static String
    FLICKR_API_KEY = "c1643aba137a68b1f9a12f9f4757f12b",
    IMAGES_API_ENDPOINT = "https://api.flickr.com/services/rest/?format=json&nojsoncallback=1&sort=random&method=flickr.photos.search&" +
            "tags=mars,planet,rover&tag_mode=all&api_key=";
    
    private void searchRandomImage() throws Exception {
        if (FLICKR_API_KEY.equals(""))
            throw new Exception("You didn't provide a working Flickr API!");
     
        WeatherJsonRequest request = new WeatherJsonRequest
            (Request.Method.GET, IMAGES_API_ENDPOINT+ FLICKR_API_KEY, null, new Response.Listener<JSONObject>() {
     
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray images = response.getJSONObject("photos").getJSONArray("photo");
                        int index = new Random().nextInt(images.length());
     
                        JSONObject imageItem = images.getJSONObject(index);
     
                        String imageUrl = "http://farm" + imageItem.getString("farm") +
                                ".static.flickr.com/" + imageItem.getString("server") + "/" +
                                imageItem.getString("id") + "_" + imageItem.getString("secret") + "_" + "c.jpg";
     
                        // TODO: do something with *imageUrl*
                     // store the pict of the day
                        SharedPreferences.Editor editor = mSharedPref.edit();
                        editor.putInt(SHARED_PREFS_DAY_KEY, today);
                        editor.putString(SHARED_PREFS_IMG_KEY, imageUrl);
                        editor.commit();
     
                    } catch (Exception e) { imageError(e); }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imageError(error);
                }
            });
        request.setPriority(Request.Priority.LOW);
        helper.add(request);
    }
    
    int mainColor = Color.parseColor("#FF5722");
    
    private void imageError(Exception e) {
        mImageView.setBackgroundColor(mainColor);
        e.printStackTrace();
    }
    
    private void loadImg(String imageUrl) {
        // Retrieves an image specified by the URL, and displays it in the UI
        ImageRequest request = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imageError(error);
                    }
                });
     
        // we don't need to set the priority here;
        // ImageRequest already comes in with
        // priority set to LOW, that is exactly what we need.
        helper.add(request);
    }
}
