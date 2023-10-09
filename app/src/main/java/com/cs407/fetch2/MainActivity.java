package com.cs407.fetch2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Main Activity program is the brain of the app itself, fetching
 * the API and filters/sorts it for the Fetch Coding Exercise
 * @author  Jason Fu
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HiringItemAdapter adapter;
    private List<HiringItem> hiringItems = new ArrayList<>();

    /**
     * This method is used to set the content view of the app itself
     * and call fetchData() to process the hiring.json file
     *
     * @param savedInstanceState First Bundle object required
     *                           in all Android Apps
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    /**
     * This method is used to call the fetch hiring URL and convert
     * it into a Java retrofit object by using GSON to parse it
     * Also handles the Response and Failure when the API call is
     * successful/unsuccessful
     */
    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fetch-hiring.s3.amazonaws.com/").addConverterFactory(GsonConverterFactory.create()).build();

        ApiService api = retrofit.create(ApiService.class);
        // Uses Retrofit's Callback on the HiringItem and enqueues it
        api.fetchItems().enqueue(new Callback<List<HiringItem>>() {

            /**
             * This method is used to display the data of the json and
             * filter out names which are NULL or "" and sort by listID
             * and then name
             * @param call receives the API call
             * @param response receives the API response
             */
            @Override
            public void onResponse(Call<List<HiringItem>> call, Response<List<HiringItem>> response) {
                // checks if the API responded and if the API contained information
                if (response.isSuccessful() && response.body() != null) {
                    List<HiringItem> items = response.body();

                    // Filter out names that are NULL or "".
                    List<HiringItem> filter = items.stream().filter(item -> item.getName() != null && !item.getName().trim().isEmpty()).collect(Collectors.toList());

                    // Sort by listId then name
                    Collections.sort(filter, (first, second) -> {
                        if (first.getListId() == second.getListId()) {
                            return first.getName().compareTo(second.getName());
                        }
                        return first.getListId() - second.getListId();
                    });

                    // set the adapter for the recyclerview for the API and create a new
                    // HiringItemAdapter to send the filtered list to
                    adapter = new HiringItemAdapter(filter);
                    recyclerView.setAdapter(adapter);
                }
            }

            /**
             * This method is used to catch if the API call failed.
             * @param call receives the API call
             * @param t a throwable for debugging by sending out a Toast
             */
            @Override
            public void onFailure(Call<List<HiringItem>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: Data not fetched", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
