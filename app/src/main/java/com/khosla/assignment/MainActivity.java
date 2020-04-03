package com.khosla.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.khosla.assignment.Adapter.RecyclerAdapter;
import com.khosla.assignment.Model.WeatherPoJo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    List<WeatherPoJo> list = new ArrayList<>();
    AlertDialog alertDialog;
    String weather_name;
    TextView desc,temperature;
    ImageView weather_icon;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        recyclerView = (RecyclerView)findViewById(R.id.list_weather);

        desc = (TextView)findViewById(R.id.desc);
        temperature = (TextView)findViewById(R.id.temperature);
        weather_icon = (ImageView) findViewById(R.id.temp_icon);

        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Weather Information");
        alertDialog.setMessage("Loading weather Information, Please wait");
        alertDialog.show();

        getWeather();
    }

    private void getWeather() {
        //Weather API URL
        final String weather_api = "https://samples.openweathermap.org/data/2.5/forecast?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, weather_api, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("list");
                            for (int i=0;i<jsonArray.length();i++){
                                //Getting the JSONArray
                                JSONObject object = jsonArray.getJSONObject(i);
                                //Getting DATE object
                                String dateObject = object.getString("dt_txt");
                                //Seperating Date and time from string using delimiter
                                String[] date_split = dateObject.split(" ");
                                String process_data = date_split[0];
                                String[] date_new = process_data.split("-");
                                String final_date = date_new[2];

                                //Getting weather description
                                JSONArray weather_desc = object.getJSONArray("weather");
                                for (int j=0;j<weather_desc.length();j++){
                                    JSONObject desc = weather_desc.getJSONObject(j);
                                    weather_name = desc.getString("main");
                                }

                                //Getting Temperature
                                JSONObject mainObject = object.getJSONObject("main");
                                String temp_object = mainObject.getString("temp");
                                Double temp_celsius = Double.parseDouble(temp_object);

                                //Converting Kelvin to Celsius
                                int converted_celsius = (int) (temp_celsius - 273.15);
                                String final_celsius = converted_celsius + "\u2103";

                                //Adding all the values to the list
                                list.add(new WeatherPoJo(final_celsius,final_date,weather_name));

                                //Initializing all the TextView with current Date,Icon & Temperature
                                desc.setText(list.get(0).getWeather_desc());
                                temperature.setText(list.get(0).getTemp());
                                if (list.get(0).getWeather_desc().equalsIgnoreCase("Clear")){
                                    weather_icon.setImageResource(R.drawable.clear);
                                }else if (list.get(0).getWeather_desc().equalsIgnoreCase("Cloud")){
                                    weather_icon.setImageResource(R.drawable.clouds);
                                }else if (list.get(0).getWeather_desc().equalsIgnoreCase("Rain")){
                                    weather_icon.setImageResource(R.drawable.rain);
                                }
                                adapter = new RecyclerAdapter(list,getBaseContext());
                                adapter.notifyDataSetChanged();
                                layoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                                recyclerView.scheduleLayoutAnimation();
                            }

                            //Close AlertDialog
                            alertDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}
