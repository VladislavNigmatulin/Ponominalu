package com.ponominalu.nvv.ponominalutest.Interfaces;

import com.ponominalu.nvv.ponominalutest.Entity.Description;
import com.ponominalu.nvv.ponominalutest.Entity.CategoryResponse;
import com.ponominalu.nvv.ponominalutest.Entity.DescriptionResponse;
import com.ponominalu.nvv.ponominalutest.Entity.EventResponse;
import com.ponominalu.nvv.ponominalutest.Entity.TestData;
import com.ponominalu.nvv.ponominalutest.dao.Event;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;


public interface IPonominalu {

    @GET("v4/categories/list?session=sesson_android_2015_ponominalu_msk")
    Call<CategoryResponse> listCategories();

    @GET("v4/events/list?&session=sesson_android_2015_ponominalu_msk")
    Call<EventResponse> listEvents(@Query("category_id") Long category_id);

    @GET("/i/300x300/{image}")
    Call<ResponseBody> eventImage(@Path("image") String image);


    @GET("v4/subevents/description/get?session=sesson_android_2015_ponominalu_msk")
    Call<Description> eventDescription(@Query("id") Long subevent_id);

}
