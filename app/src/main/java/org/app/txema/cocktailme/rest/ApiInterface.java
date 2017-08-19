package org.app.txema.cocktailme.rest;

import org.app.txema.cocktailme.model.DrinksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Txema on 18/08/2017.
 */

public interface ApiInterface {

    @GET("filter.php?a=Non_Alcoholic")
    Call<DrinksResponse> getNonAlcoholicDrinks();

    @GET("lookup.php")
    Call<DrinksResponse> getDrinkById(@Query("i") String idDrink);
}
