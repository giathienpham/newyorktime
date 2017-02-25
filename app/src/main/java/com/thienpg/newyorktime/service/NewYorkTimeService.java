package com.thienpg.newyorktime.service;

import com.thienpg.newyorktime.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ThienPG on 2/25/2017.
 */

public interface NewYorkTimeService {

    @GET("articlesearch.json")
    Call<ArticleResponse> getArticles(@Query("api-key") String api_key,@Query("q") String query);

//    @GET("group/{id}/users")
//    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
//
//    @POST("users/new")
//    Call<User> createUser(@Body User user);

}
