package com.example.stephan.quiz;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends ListFragment {

    public ArrayAdapter adapter;
    public List<String> menuList= new ArrayList<>();
    private List<Item> parsedItems = new ArrayList<>();
    public Item tempItem;


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(" l.getItemAtPosition(position)", l.getItemAtPosition(position).toString());
        Item item = parsedItems.get(position);
        String questionUrlAdd = item.id(item);
        Log.d("String s", questionUrlAdd);
        Intent i = new Intent(getActivity(), Questions.class);
        i.putExtra("questionUrlAdd", questionUrlAdd);
        startActivity(i);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "https://opentdb.com/api_category.php";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray categories  = response.getJSONArray("trivia_categories");
                            Log.d("json", categories.toString());
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject subitem = categories.getJSONObject(i);
                                Log.d("subitem", subitem.toString());
                                String id = Integer.toString(subitem.getInt("id"));
                                Log.d(" id", id);
                                String category = subitem.getString("name");
                                Log.d(" name", category);
                                tempItem = new Item(id, category);
                                Log.d("tempItem", tempItem.category(tempItem));
                                Log.d("tempItem", tempItem.id(tempItem));
                                parsedItems.add(tempItem);
                                String menuElement = category;
                                menuList.add(menuElement);

                            }
                            updateAdaptor();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        Log.d("jsonurl", getRequest.toString());

        // add it to the RequestQueue
        queue.add(getRequest);

    }

    public void updateAdaptor(){
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, menuList);
        this.setListAdapter(adapter);

    }
}
