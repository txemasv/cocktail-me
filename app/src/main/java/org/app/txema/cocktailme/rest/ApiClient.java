package org.app.txema.cocktailme.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Txema on 18/08/2017.
 */

public class ApiClient {
    private static final String API_KEY = "1";
    private static final String BASE_URL = "http://www.thecocktaildb.com/api/json/v1/" + API_KEY + "/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
