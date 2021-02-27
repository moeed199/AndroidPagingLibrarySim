package com.example.androidpaginglibrarysimplified;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ItemViewModel itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        final ItemAdapter itemAdapter = new ItemAdapter(this);

        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Item>>() {
            @Override
            public void onChanged(PagedList<Item> items) {
                itemAdapter.submitList(items);
            }
        });
        recyclerView.setAdapter(itemAdapter);



        /*Call<StackApiResponse> call = RetrofitClient.getmInstance()
                .getApi()
                .getAnswers(1,50,"stackoverflow");

        call.enqueue(new Callback<StackApiResponse>() {
            @Override
            public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                StackApiResponse stackApiResponse=response.body();
                Toast.makeText(MainActivity.this, String.valueOf(stackApiResponse.items.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<StackApiResponse> call, Throwable t) {


            }
        });*/
    }
}