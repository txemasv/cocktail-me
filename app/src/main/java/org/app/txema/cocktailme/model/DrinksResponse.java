package org.app.txema.cocktailme.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Txema on 18/08/2017.
 */

public class DrinksResponse {
    @SerializedName("drinks")
    private List<Drink> drinks;

    public List<Drink> getDrinks() {
        return drinks;
    }
}
