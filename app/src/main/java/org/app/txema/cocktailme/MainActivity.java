package org.app.txema.cocktailme;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.app.txema.cocktailme.adapter.DrinksAdapter;
import org.app.txema.cocktailme.model.DrinksResponse;
import org.app.txema.cocktailme.rest.ApiClient;
import org.app.txema.cocktailme.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private DrinksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CoordinatorLayout
        setContentView(R.layout.activity_main);

        //Declare AppBarLayout
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        //Declare CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));

        //Declare ToolBar widget
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Declare main layout for use with snackBar
        //TODO layoutMain = findViewById(R.id.activity_main_layout);

        //Inflate recyclerView layout
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Use the layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);

        //Set image cover for collapsingLayout (Using Glide)
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Declare ApiInterface Service
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        //Declare Call functions
        Call<DrinksResponse> call = apiService.getNonAlcoholicDrinks();

        //Screen orientation (one-pane: portrait, two-pane: landscape)
        //TODO setScreenOrientation();

        //Show Progress dialog
        //TODO progressDialogShow();

        //Call server (onResponse, onFailure)
        call.enqueue(callNonAlcoholicDrinks());
    }

    private Callback<DrinksResponse> callNonAlcoholicDrinks() {
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
                        //1. get items from response
                        DrinksResponse drinks = response.body();
                        //2. specify adapter, with items list
                        adapter = new DrinksAdapter(drinks, MainActivity.this);
                        recyclerView.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rate_app) {
            Toast.makeText(getApplicationContext(), "Ingredients, TODO", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
