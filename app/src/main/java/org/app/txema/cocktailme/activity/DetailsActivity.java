package org.app.txema.cocktailme.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.app.txema.cocktailme.R;
import org.app.txema.cocktailme.activity.fragment.DetailsFragment;
import org.app.txema.cocktailme.model.Drink;

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

        //Screen orientation (one-pane: portrait, two-pane: landscape)
        setScreenOrientation();

        //Show Progress dialog
        //progressDialogShow();
        //TODO

        // Capture the detailsFragment from the activity layout
        DetailsFragment detailsFrag = (DetailsFragment)
                (DetailsActivity.this).getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
        detailsFrag.updateDetailsView(drink);
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
