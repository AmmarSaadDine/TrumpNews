package com.example.android.trumpnews.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.trumpnews.R;
import com.example.android.trumpnews.entity.NewsEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ammar_saaddine on 04.04.18.
 */

public class NewsAdapter extends ArrayAdapter<NewsEntry> {

    private static final DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final DateFormat toFormat = new SimpleDateFormat("dd-MM-yyyy");


    public NewsAdapter(@NonNull Context context, @NonNull List<NewsEntry> newsEntries) {
        super(context, 0, newsEntries);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_entry_list_item, parent, false);
        }
        NewsEntry currentNewsEntry= getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.title);
        String title = currentNewsEntry.getTitle();
        if (title != null && !title.isEmpty()) {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        } else {
            titleTextView.setVisibility(View.GONE);
        }

        TextView sectionNameTextView = listItemView.findViewById(R.id.section_name);
        String sectionName = currentNewsEntry.getSectionName();
        if (sectionName != null && !sectionName.isEmpty()) {
            sectionNameTextView.setVisibility(View.VISIBLE);
            sectionNameTextView.setText(sectionName);
        } else {
            sectionNameTextView.setVisibility(View.GONE);
        }

        TextView authorTextView = listItemView.findViewById(R.id.author);
        String author = currentNewsEntry.getAuthor();
        if (author != null && !author.isEmpty()) {
            authorTextView.setVisibility(View.VISIBLE);
            authorTextView.setText(author);
        } else {
            authorTextView.setVisibility(View.GONE);
        }

        TextView publicationDateTextView = listItemView.findViewById(R.id.publication_date);
        String publicationDate = currentNewsEntry.getPublicationDate();
        String formattedDate = dateStringFrom(publicationDate);
        if (formattedDate != null && !formattedDate.isEmpty()) {
            publicationDateTextView.setVisibility(View.VISIBLE);
            publicationDateTextView.setText(formattedDate);
        } else {
            publicationDateTextView.setVisibility(View.GONE);
        }

        return listItemView;
    }

    private String dateStringFrom(String originalDateString) {
        try {
            Date dateObject = fromFormat.parse(originalDateString);
            return toFormat.format(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
