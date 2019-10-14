package com.example.retrofitexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("{id}/{type}/{category}")
    Call<PostMovie> getPostMovies(@Path("id") Integer movieId,
                                        @Path("type") String showType,
                                        @Path("category") String showCategory,
                                        @Query("api_key") String key
                                        );
}