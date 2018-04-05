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

import java.util.List;

/**
 * Created by ammar_saaddine on 04.04.18.
 */

public class NewsAdapter extends ArrayAdapter<NewsEntry> {

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
        titleTextView.setText(currentNewsEntry.getTitle());

        TextView sectionNameTextView = listItemView.findViewById(R.id.section_name);
        sectionNameTextView.setText(currentNewsEntry.getSectionName());

        TextView authorTextView = listItemView.findViewById(R.id.author);
        authorTextView.setText(currentNewsEntry.getAuthor());

        TextView publicationDateTextView = listItemView.findViewById(R.id.publication_date);
        publicationDateTextView.setText(currentNewsEntry.getPublicationDate());

        return listItemView;
    }
}
