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

import com.squareup.picasso.Picasso;
import com.thienpg.newyorktime.R;
import com.thienpg.newyorktime.model.ArticleResponse;
import com.thienpg.newyorktime.model.Doc;
import com.thienpg.newyorktime.model.Multimedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.thienpg.newyorktime.utility.Constant.IMG_URL;

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

    private Context getContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_Article) ImageView picture;
        @BindView(R.id.tv_Headline) TextView headline;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void refreshData(List<Doc> docs) {
        mDocs = docs;
        notifyDataSetChanged();
    }
    public void addData(List<Doc> docs, int page) {
        mDocs.addAll(docs);
        notifyItemInserted(page);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.article_card, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Doc doc = mDocs.get(position);

        viewHolder.headline.setText(doc.getHeadline().getMain());
       for (Multimedia media: doc.getMultimedia()) {
            loadImageToView(doc.getMultimedia().get(0).getUrl(), viewHolder.picture);
       }

    }

    @Override
    public int getItemCount() {
        return mDocs.size();
    }

    private void loadImageToView(String imageUrl, ImageView view){
        Picasso.with(getContext())
                .load(IMG_URL +imageUrl)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(view);
    }

}
