package com.vkolte.cargofxtest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ImagesInterface {

    @GET("search/")
    Call<List<Images>> getImages(@Query("limit") String limit, @Query("page")String page,
                                 @Query("order")String order);

    @FormUrlEncoded
    @GET("search")
    Call<List<Images>> getImages(@FieldMap Map<String,String> map);




    @FormUrlEncoded
    @GET("/search?limit=100&page=11&order=Desc")
    Call<List<Images>> getImages();
}

