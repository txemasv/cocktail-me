package org.app.txema.cocktailme.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.app.txema.cocktailme.R;
import org.app.txema.cocktailme.model.Drink;

/**
 * Created by Txema on 18/08/2017.
 */

public class DetailsFragment extends Fragment {

    private TextView nameView;
    private ImageView thumbnailView;
    private TextView instructionsView;
    private LinearLayout ingredientsListView;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_details, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        //Declare layout
        nameView = (TextView) view.findViewById(R.id.name);
        instructionsView = (TextView) view.findViewById(R.id.instructions);
        thumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
        ingredientsListView = (LinearLayout) view.findViewById(R.id.ingredients_list);
    }

    public void updateDetailsView(Drink drink) {
        if (drink != null) {
            //Insert drink data into views
            nameView.setText(drink.getName());
            try {
                Glide.with(this).load(drink.getThumbnail()).into(thumbnailView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            instructionsView.setText(drink.getInstructions());

            //Add items programmatically to ingredientsList
            for (String ingredient : drink.getIngredientMeasureList()) {
                TextView ingredientTextView = new TextView(getContext());
                ingredientTextView.setText(ingredient.replace("\n", "").replace("\r", ""));
                ingredientTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ingredientsListView.addView(ingredientTextView);
            }
            TextView ingredientTextView = new TextView(getContext());
            ingredientTextView.setText(getString(R.string.glass_type_title)+ " " + drink.getGlass());
            ingredientTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ingredientsListView.addView(ingredientTextView);
        }
    }

}
