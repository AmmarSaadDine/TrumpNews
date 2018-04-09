package com.example.android.trumpnews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.trumpnews.R;
import com.example.android.trumpnews.entity.NewsEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ammar_saaddine on 04.04.18.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    // Constants
    private static final DateFormat FROM_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    private static final DateFormat TO_FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    // Variables
    private Context context;
    private List<NewsEntry> newsEntries;

    // Constructors
    public NewsAdapter(@NonNull Context context, @NonNull List<NewsEntry> newsEntries) {
        this.context = context;
        this.newsEntries = newsEntries;
    }

    // Methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.news_entry_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NewsEntry currentNewsEntry = newsEntries.get(position);

        String title = currentNewsEntry.getTitle();
        if (title != null && !title.isEmpty()) {
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.titleTextView.setText(title);
        } else {
            holder.titleTextView.setVisibility(View.GONE);
        }

        String sectionName = currentNewsEntry.getSectionName();
        if (sectionName != null && !sectionName.isEmpty()) {
            holder.sectionNameTextView.setVisibility(View.VISIBLE);
            holder.sectionNameTextView.setText(sectionName);
        } else {
            holder.sectionNameTextView.setVisibility(View.GONE);
        }

        List<String> authors = currentNewsEntry.getAuthors();
        if (authors != null && !authors.isEmpty()) {
            holder.authorsTextView.setVisibility(View.VISIBLE);
            String authorsString = context.getString(R.string.authors) + authorsStringFrom(authors);
            holder.authorsTextView.setText(authorsString);
        } else {
            holder.authorsTextView.setVisibility(View.GONE);
        }

        String publicationDate = currentNewsEntry.getPublicationDate();
        String formattedDate = dateStringFrom(publicationDate);
        if (formattedDate != null && !formattedDate.isEmpty()) {
            holder.publicationDateTextView.setVisibility(View.VISIBLE);
            holder.publicationDateTextView.setText(formattedDate);
        } else {
            holder.publicationDateTextView.setVisibility(View.GONE);
        }

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNewsEntry.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                context.startActivity(websiteIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsEntries.size();
    }

    public void clear() {
        newsEntries.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<NewsEntry> newsEntries) {
        this.newsEntries.addAll(newsEntries);
        notifyDataSetChanged();
    }

    private String dateStringFrom(String originalDateString) {
        try {
            Date dateObject = FROM_FORMAT.parse(originalDateString);
            return TO_FORMAT.format(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String authorsStringFrom(List<String> authors) {
        StringBuilder builder = new StringBuilder();
        builder.append(authors.get(0));
        for (int i = 1; i < authors.size(); i++) {
            String author = authors.get(i);
            if (!author.isEmpty()) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(author);
            }
        }
        return builder.toString();
    }

    // The view holder class of this Adapter

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView sectionNameTextView;
        private TextView authorsTextView;
        private TextView publicationDateTextView;
        private View parentView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.parentView = itemView;
            this.titleTextView = itemView.findViewById(R.id.title);
            this.sectionNameTextView = itemView.findViewById(R.id.section_name);
            this.authorsTextView = itemView.findViewById(R.id.authors);
            this.publicationDateTextView = itemView.findViewById(R.id.publication_date);
        }
    }
}
