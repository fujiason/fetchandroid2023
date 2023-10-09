package com.cs407.fetch2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("hiring.json") // fetches hiring.json from API call
    Call<List<HiringItem>> fetchItems();
}
