package com.thienpg.newyorktime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thienpg.newyorktime.R;
import com.thienpg.newyorktime.model.ArticleResponse;
import com.thienpg.newyorktime.model.Doc;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThienPG on 2/25/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private Context mContext;
    private List<Doc> mDocs;

    public ArticlesAdapter(Context context, List<Doc> docs) {
        mDocs = docs;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_Article) ImageView picture;
        @BindView(R.id.tv_Headline) TextView headline;
        @BindView(R.id.tv_Snippet) TextView snippet;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addToDoc(List<Doc> blah) {
        mDocs.addAll(blah);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.article, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Doc doc = mDocs.get(position);

        viewHolder.headline.setText(doc.getHeadline().getMain());
        viewHolder.snippet.setText(doc.getSnippet());

    }

    @Override
    public int getItemCount() {
        return mDocs.size();
    }



}
