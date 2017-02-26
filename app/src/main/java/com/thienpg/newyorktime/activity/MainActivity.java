package com.thienpg.newyorktime.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thienpg.newyorktime.R;
import com.thienpg.newyorktime.adapter.ArticlesAdapter;
import com.thienpg.newyorktime.adapter.EndlessRecyclerViewScrollListener;
import com.thienpg.newyorktime.fragment.FilterFragment;
import com.thienpg.newyorktime.model.ArticleResponse;
import com.thienpg.newyorktime.model.Doc;
import com.thienpg.newyorktime.model.Filter;
import com.thienpg.newyorktime.retrofit.Retrofit2Client;
import com.thienpg.newyorktime.service.NewYorkTimeService;
import com.thienpg.newyorktime.utility.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thienpg.newyorktime.utility.Constant.API_KEY;

public class MainActivity extends AppCompatActivity implements FilterFragment.FilterFragmentListener {

    private List<Doc> docs = new ArrayList<>();
    RecyclerView rvArticles;
    ArticlesAdapter articlesAdapter;
    Context context;
    String currentQuery = null ;
    String currentSort = null;
    String currentDate = null;
    String currentDesk = null;
    int currentPage = 0;
    private EndlessRecyclerViewScrollListener scrollListener;

    Retrofit2Client retrofit = Retrofit2Client.getInstance();
    NewYorkTimeService newYorkTimeService = retrofit.getNewYorkTimeService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

//        Retrofit2Client retrofit = Retrofit2Client.getInstance();
//        NewYorkTimeService newYorkTimeService = retrofit.getNewYorkTimeService();

        rvArticles = (RecyclerView) findViewById(R.id.rv_Articles);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rvArticles.setLayoutManager(gridLayoutManager);
        articlesAdapter = new ArticlesAdapter(context, docs);
        rvArticles.setAdapter(articlesAdapter);

        Call<ArticleResponse> call = newYorkTimeService.getArticles(API_KEY, currentQuery, currentDate,currentDesk, currentSort,currentPage);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.body() == null){
                    Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                docs = response.body().getResponse().getDocs();
                articlesAdapter.refreshData(docs);

            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

        ItemClickSupport.addTo(rvArticles).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String url = docs.get(position).getWebUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                }
        );


        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        rvArticles.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        MenuItem filterItem = menu.findItem(R.id.action_filter);


        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(Color.WHITE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                currentQuery = query;
                currentPage = 0;
                Call<ArticleResponse> call = newYorkTimeService.getArticles(API_KEY, currentQuery, currentDate,currentDesk, currentSort,currentPage);
                call.enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                        if (response.body() == null){
                            Toast.makeText(MainActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                        }
                        docs = response.body().getResponse().getDocs();
                        articlesAdapter.refreshData(docs);

                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showEditDialog();
                Toast.makeText(context, "aaa", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance("Some Title");
        filterFragment.show(fm, "fragment_filter");
    }


    @Override
    public void onFinishFilterFragment(Filter filter) {

        currentDate = filter.getBeginDate();
        currentDesk = filter.getDesk();
        currentSort = filter.getSort();
        currentPage = 0;
        Call<ArticleResponse> call = newYorkTimeService.getArticles(API_KEY, currentQuery, currentDate,currentDesk, currentSort,currentPage);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.body() == null){
                    Toast.makeText(MainActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                }
                docs = response.body().getResponse().getDocs();
                articlesAdapter.refreshData(docs);

            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        currentPage = offset;
        Call<ArticleResponse> call = newYorkTimeService.getArticles(API_KEY, currentQuery, currentDate,currentDesk, currentSort,currentPage);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.body() == null){
                    Toast.makeText(MainActivity.this, "Don't scroll too fast! Or donate me to load faster", Toast.LENGTH_SHORT).show();
                    return;
                }
                docs = response.body().getResponse().getDocs();
                articlesAdapter.addData(docs);

            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }


}
