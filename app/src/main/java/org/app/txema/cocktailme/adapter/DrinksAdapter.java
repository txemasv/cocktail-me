package org.app.txema.cocktailme.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.app.txema.cocktailme.MainActivity;
import org.app.txema.cocktailme.R;
import org.app.txema.cocktailme.activity.DetailsActivity;
import org.app.txema.cocktailme.activity.fragment.DetailsFragment;
import org.app.txema.cocktailme.model.Drink;
import org.app.txema.cocktailme.model.DrinksResponse;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Txema on 18/08/2017.
 */

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.MyViewHolder> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Drink> drinks;
    private Context context;

    //1. create static ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        private View cardView;
        private ImageView thumbnail;
        private TextView drink;

        MyViewHolder(View itemView) {
            super(itemView);
            //find view items from layout
            cardView = itemView.findViewById(R.id.card_view);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            drink = (TextView) itemView.findViewById(R.id.drink);
        }
    }

    //2. create constructor (context, items)
    public DrinksAdapter(DrinksResponse drinksResponse, Context context) {
        this.drinks = drinksResponse.getDrinks();
        this.context = context;

        //Delete items without image
        for (Iterator<Drink> i = drinks.listIterator(); i.hasNext(); ) {
            Drink drink = i.next();
            if (drink.getThumbnail() == null) {
                i.remove();
            }
        }

        //Set drink as default (case two-panes)
        if (!drinks.isEmpty()) {
            setDefaultDrinkInView(drinks.get(0));
        }
    }

    //3. override methods
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //load itemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        //return viewHolder
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //load item (Drink)
        final Drink drink = drinks.get(position);

        //set params on view item
        holder.drink.setText(drink.getName());

        //loading main cover
        Glide.with(context).load(drink.getThumbnail()).into(holder.thumbnail);

        //add listeners
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDrinkInView(drink);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    private void setDefaultDrinkInView(Drink drink) {
        // Only in two-pane mode
        // Capture the detail fragment from the activity layout
        DetailsFragment detailsFrag = (DetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.detailsFragment);

        // If detailsFrag is available
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(drink);
        }
    }

    private void setDrinkInView(Drink drink) {
        // Capture the detail fragment from the activity layout
        DetailsFragment detailsFrag = (DetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.detailsFragment);

        // If detailsFrag is available
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(drink);
        } else {
            //one-pane layout
            Intent nextActivity = new Intent(context, DetailsActivity.class);
            //pass drink object in the intent
            nextActivity.putExtra("drink", drink);
            context.startActivity(nextActivity);
        }
    }

}