package com.example.android.trumpnews.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.trumpnews.entity.NewsEntry;

import java.util.List;

/**
 * Created by ammar_saaddine on 03.04.18.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsEntry>> {

    private String mUrl = null;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsEntry> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        List<NewsEntry> result = QueryUtils.fetchNewsData(mUrl);
        return result;
    }
}
