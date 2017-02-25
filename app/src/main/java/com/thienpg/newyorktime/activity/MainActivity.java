package com.thienpg.newyorktime.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thienpg.newyorktime.R;
import com.thienpg.newyorktime.adapter.ArticlesAdapter;
import com.thienpg.newyorktime.model.ArticleResponse;
import com.thienpg.newyorktime.model.Doc;
import com.thienpg.newyorktime.retrofit.Retrofit2Client;
import com.thienpg.newyorktime.service.NewYorkTimeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.thienpg.newyorktime.utility.Constant.API_KEY;

public class MainActivity extends AppCompatActivity {

    private List<Doc> docs = new ArrayList<>();
    RecyclerView rvArticles;
    ArticlesAdapter articlesAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Retrofit2Client retrofit = Retrofit2Client.getInstance();
        NewYorkTimeService newYorkTimeService = retrofit.getNewYorkTimeService();

        rvArticles = (RecyclerView) findViewById(R.id.rv_Articles);
        rvArticles.setLayoutManager(new LinearLayoutManager(context));
        articlesAdapter = new ArticlesAdapter(context, docs);
        rvArticles.setAdapter(articlesAdapter);

        Call<ArticleResponse> call = newYorkTimeService.getArticles(API_KEY, "sa");
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                docs = response.body().getResponse().getDocs();
                articlesAdapter.addToDoc(docs);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });
    }
}
