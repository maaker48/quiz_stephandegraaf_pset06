package com.example.stephan.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Questions extends AppCompatActivity {

    public List<String> questionItems = new ArrayList<>();
    public List<String> correct_answer = new ArrayList<>();
    public Boolean[] checkboxes;
    public ArrayAdapter adapter;
    public ListView lv;
    public Button bt_Submit;
    public Integer score;
    public RequestQueue queue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        lv = findViewById(R.id.lv_questions);
        bt_Submit = findViewById(R.id.bt_Submit);
        bt_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postScore();
            }
        });
        Intent intent = getIntent();
        String choice = intent.getExtras().getString("questionUrlAdd");
        queue = Volley.newRequestQueue(this);
        fetchData(choice);
    }

    //Fetches data of given choice
    public void fetchData(String choice) {
        final String url = "https://opentdb.com/api.php?amount=10&category=" + choice + "&type=boolean";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray categories = response.getJSONArray("results");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject subitem = categories.getJSONObject(i);
                                questionItems.add(subitem.getString("question"));
                                correct_answer.add(subitem.getString("correct_answer"));
                            }
                            updateAdaptor();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public void updateAdaptor(){
        checkboxes = new Boolean[questionItems.size()];
        for(int i = 0; i < questionItems.size(); i++) {
            checkboxes[i] = false;
        }

        adapter = new QuizAdapter(this, R.id.tv_question, questionItems, checkboxes);
        lv.setAdapter(adapter);
    }

    public void postScore(){
        score = 0;
        for(int i = 0; i  < checkboxes.length; i++) {
            if(checkboxes[i] == Boolean.parseBoolean(correct_answer.get(i)))
                score += 1;
        }
        String toastString = "Je behaalde score is : "+ score + " We zullen dit in de ranking zetten";
        Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT);
        toast.show();

        //Database MojoJojo, Retrieves connection, sets score for displayName
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser me = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(me.getDisplayName());
        myRef.child("score").setValue(score);

        finish();
    }
}
