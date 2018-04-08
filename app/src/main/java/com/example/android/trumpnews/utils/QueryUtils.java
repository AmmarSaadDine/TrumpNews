package com.example.android.trumpnews.utils;

import android.util.Log;

import com.example.android.trumpnews.entity.NewsEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Helper methods related to requesting and receiving news entry data from USGS.
 */
public final class QueryUtils {

    private static final String LOG_TAG = "QueryUtils";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_TITLE = "webTitle";
    private static final String KEY_SECTION_NAME = "sectionName";
    private static final String KEY_PUBLICATION_DATE = "webPublicationDate";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_URL = "webUrl";


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link NewsEntry} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<NewsEntry> extractNewsEntriesFromJson(String newsEntriesJSON) {

        // Create an empty ArrayList that we can start adding newsEntries to
        ArrayList<NewsEntry> newsEntries = new ArrayList<>();

        try {
            // build up a list of NewsEntry objects with the corresponding data.
            JSONObject root = new JSONObject(newsEntriesJSON);
            JSONObject response = root.getJSONObject(KEY_RESPONSE);
            JSONArray results = response.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < results.length(); i++) {
                JSONObject newsEntryObject = results.getJSONObject(i);
                String title = newsEntryObject.optString(KEY_TITLE);
                String sectionName = newsEntryObject.optString(KEY_SECTION_NAME);
                String publicationDate = newsEntryObject.optString(KEY_PUBLICATION_DATE);
                String author = newsEntryObject.optString(KEY_AUTHOR);
                String url = newsEntryObject.optString(KEY_URL);
                newsEntries.add(new NewsEntry(title, sectionName, author, publicationDate, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the news entry JSON results", e);
        }

        // Return the list of newsEntries
        return newsEntries;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.v(LOG_TAG, jsonResponse);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news entry JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the guardian dataset and return a list of {@link NewsEntry} objects.
     */
    public static List<NewsEntry> fetchNewsData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link NewsEntry}s
        List<NewsEntry> newsEntries = extractNewsEntriesFromJson(jsonResponse);

        // Return the list of {@link NewsEntry}s
        return newsEntries;
    }
}