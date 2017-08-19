package org.app.txema.cocktailme.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.app.txema.cocktailme.R;
import org.app.txema.cocktailme.activity.fragment.DetailsFragment;
import org.app.txema.cocktailme.model.Drink;
import org.app.txema.cocktailme.model.DrinksResponse;
import org.app.txema.cocktailme.rest.ApiClient;
import org.app.txema.cocktailme.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Txema on 18/08/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //activity entry animation
        slideToLeft();

        //Get Drink from extras
        Drink drink = getIntent().getParcelableExtra("drink");

        //Declare ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String toolbarTitle = "";
        if(drink != null) {
            toolbarTitle = drink.getName();
        }
        //Must setTitle in toolBar
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);

        //Declare ActionBar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Declare ApiInterface Service
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        //Declare Call function
        Call<DrinksResponse> call = apiService.getDrinkById(drink.getIdDrink());

        //Screen orientation (one-pane: portrait, two-pane: landscape)
        setScreenOrientation();

        //Show Progress dialog
        //progressDialogShow();
        //TODO

        //Call server (onResponse, onFailure)
        call.enqueue(mCall());
    }

    private Callback<DrinksResponse> mCall() {
        return new Callback<DrinksResponse>() {
            @Override
            public void onResponse(Call<DrinksResponse> call, Response<DrinksResponse> response) {
                //wait for debugger (NOT IN RUN MODE!)
                android.os.Debug.waitForDebugger();
                Log.d(TAG, "wait for debugger ");

                //get status_code
                int statusCode = response.code();

                //verify response http (code)
                //200(OK), 400(BAD_REQUEST), 404(NOT_FOUND)
                switch (statusCode) {
                    case 200:
                        //1. Get items from response
                        DrinksResponse drinksResponse = response.body();
                        //2. Capture detailsFragment from the activity layout
                        DetailsFragment detailsFrag = (DetailsFragment)
                                (DetailsActivity.this).getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
                        //3. Set drink information in detailsFragment
                        if(!drinksResponse.getDrinks().isEmpty()) {
                            detailsFrag.updateDetailsView(drinksResponse.getDrinks().get(0));
                        }
                        break;
                    default:
                        Log.e(TAG, "HTTP CODE " + statusCode);
                        break;
                }
            }

            @Override
            public void onFailure(Call<DrinksResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        };
    }

    private void setScreenOrientation() {
        //Screen orientation (one-pane: portrait)
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        slideToRight();
    }

    private void slideToLeft() {
        //slide animation
        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
    }

    private void slideToRight() {
        //slide animation
        overridePendingTransition(R.anim.right_slide_out, R.anim.right_slide_in);
    }

}
