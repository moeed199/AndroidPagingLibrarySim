package com.example.androidpaginglibrarysimplified;

import android.util.Log;

import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {

    //the size of a page that we want
    public static final int PAGE_SIZE = 50;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    //we need to fetch from stackoverflow
    private static final String SITE_NAME = "stackoverflow";


    //this will be called once to load the initial data
    @Override
    public void loadInitial(LoadInitialParams<Integer> params, final LoadInitialCallback<Integer, Item> callback) {
        RetrofitClient.getmInstance()
                .getApi().getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().items, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }

    //this will load the previous page
    @Override
    public void loadBefore(final LoadParams<Integer> params,  final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getmInstance()
                .getApi().getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {

                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page

                        if (response.body() != null) {
                            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                            //passing the loaded data
                            //and the previous page key
                            callback.onResult(response.body().items, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }

    //this will load the next page
    @Override
    public void loadAfter(final LoadParams<Integer> params,final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getmInstance()
                .getApi()
                .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {

                        if (response.body() != null) {
                            //if the response has next page
                            //incrementing the next page number
                            Integer key = response.body().has_more ? params.key + 1 : null;

                            //passing the loaded data and next page value
                            callback.onResult(response.body().items, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }
}
