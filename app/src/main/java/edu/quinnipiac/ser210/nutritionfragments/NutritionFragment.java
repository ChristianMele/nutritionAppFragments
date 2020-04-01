package edu.quinnipiac.ser210.nutritionfragments;
//imports

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*
Christian Mele
April 31, 2020
NutritionFragment Class
This class retrieves data from the HomeFragment bundle and sets them to the textviews in the NutritionFragment fragment
    for viewing by the user
 */

public class NutritionFragment extends Fragment {
    //instance variables
    String calInfo;
    String fatInfo;
    String proInfo;
    String carbInfo;

    @Override
    public void onStart() {
        super.onStart();
        //retrieves the arguments bundle from HomeFragment so data is available on this fragment
        Bundle args = getArguments();
        if (args != null) {

            calInfo = (String) args.get("cal"); //retrieves calories
            fatInfo = (String) args.get("fat"); //retrieves fats
            proInfo = (String) args.get("pro"); //retrieves proteins
            carbInfo = (String) args.get("carb"); //retrieves carbohydrates

            TextView calView = getView().findViewById(R.id.caloriesUnit); //sets calories
            TextView fatView = getView().findViewById(R.id.proteinUnit); //sets proteins
            TextView proView = getView().findViewById(R.id.carbsUnit); //sets carbohydrates
            TextView carbView = getView().findViewById(R.id.caloriesUnit); //sets fats
            //sets the textviews data statistics to those retrived from the API
            calView.setText(calInfo);
            fatView.setText(fatInfo);
            proView.setText(proInfo);
            carbView.setText(carbInfo);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }
}