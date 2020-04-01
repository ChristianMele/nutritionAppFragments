package edu.quinnipiac.ser210.nutritionfragments;
//imports

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
Christian Mele
April 31, 2020
HomeFragment Class
Fragment of the home screen in nutrientApp, this class is where the user can input a food name, and the
    program will parse, retrieve and send food nutrient data from the API to the second fragment
 */

public class HomeFragment extends Fragment {
    //sets urlStr to the URL of the API
    String urlStr = "https://api.edamam.com/api/food-database/parser?session=40&app_key=69d004c3e3c5b967261e625baea627fc&app_id=85b409cf&ingr=";

    //fragment inflater
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {

        super.onStart();
        Button button = getView().findViewById(R.id.button);
        //when the Enter button is clicked the user enetered food data will be retrieved from the API during the FetchInfo method
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchInfo().execute("");
            }
        });
    }

    private class FetchInfo extends AsyncTask<String, Void, String> {
        TextView input = getView().findViewById(R.id.foodInput);
        String userInput = input.getText().toString();
        String data = "";

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                //creates a url from the API url with user's food input appended for specific food selection
                URL url = new URL(urlStr + userInput);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "69d004c3e3c5b967261e625baea627fc");
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;

                }
                if (in == null) {
                    return null;
                }
                //parses through the JSON data of the API to retrieve food data
                JSONObject obj = new JSONObject(data);
                JSONArray jarray = obj.getJSONArray("hints");
                JSONObject foodObj = (JSONObject) jarray.get(0);
                JSONObject foodArray = (JSONObject) foodObj.get("food");
                JSONObject nutObj = (JSONObject) foodArray.get("nutrients");
                //after parsing to the 'nutrients' section of the JSON data each individual macronutrient is retrieved
                final double calData = (double) nutObj.get("ENERC_KCAL"); //calories
                final double fatData = (double) nutObj.get("FAT"); //fats
                final double proData = (double) nutObj.get("PROCNT"); //proteins
                final double carbData = (double) nutObj.get("CHOCDF"); //carbohydrates

                //the click listener for the 'go' button, sets the nutrient values for the food in the second fragment
                Button button2 = getView().findViewById(R.id.button2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putString("cal", String.valueOf(calData)); //sets nutrient value of calories
                        args.putString("fat", String.valueOf(fatData)); //sets nutrient value of fats
                        args.putString("pro", String.valueOf(proData)); //sets nutrient value of proteins
                        args.putString("carb", String.valueOf(carbData)); //sets nutrient value of carbohydrates

                        Fragment fr = new NutritionFragment();
                        fr.setArguments(args);
                        //sends data to the NutrientFragment fragment
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fr);
                        fragmentTransaction.commit();
                    }
                });


            } catch (Exception e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        return null;
                    }
                }
            }
            return null;
        }
    }
}