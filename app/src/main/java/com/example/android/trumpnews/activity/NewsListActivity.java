package com.example.android.trumpnews.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.trumpnews.R;
import com.example.android.trumpnews.entity.NewsEntry;
import com.example.android.trumpnews.utils.NewsAdapter;
import com.example.android.trumpnews.utils.NewsLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsEntry>> {

    // Constants
    private static final String TRUMP_SEARCH_REQUEST_URL =
            "https://content.guardianapis.com/search?q=trump&tag=politics/politics&page-size=20&api-key=test";
    private static final int NEWS_LOADER_ID = 1;
    private static final String LOG_TAG = NewsListActivity.class.getName();

    // Variables
    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    /**
     * Adapter for the list of news entries
     */
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        // Find a reference to the {@link ListView} in the layout
        ListView newsEntriesListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this, new ArrayList<NewsEntry>());
        newsEntriesListView.setAdapter(mAdapter);

        newsEntriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current news entry that was clicked on
                NewsEntry currentNewsEntry= mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNewsEntry.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsEntriesListView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingSpinner = findViewById(R.id.loading_spinner);
            loadingSpinner.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<NewsEntry>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, TRUMP_SEARCH_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsEntry>> loader, List<NewsEntry> newsEntries) {

        ProgressBar progressBar = findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        // Set empty state text to display "No news found."
        mEmptyStateTextView.setText(R.string.no_results);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link NewsEntry}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsEntries != null && !newsEntries.isEmpty()) {
            mAdapter.addAll(newsEntries);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsEntry>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
