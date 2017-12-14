package com.example.stephan.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.List;

public class QuizAdapter extends ArrayAdapter<String> {

    public Boolean[] checkboxes;
    public List<String> questions;

    public QuizAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public QuizAdapter(Context context, int resource, List<String> items, Boolean[] checkboxes) {
        super(context, resource, items);
        questions = items;
        this.checkboxes = checkboxes;
    }

    static class ViewHolder {
        protected TextView text;
        protected ToggleButton toggle;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator;
            inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.row_question, null);
            viewHolder = new ViewHolder();
            viewHolder.text = convertView.findViewById(R.id.tv_question);
            viewHolder.toggle = convertView.findViewById(R.id.tb_toggle);
            viewHolder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Here we get the position that we have set for the checkbox using setTag.
                    int getPosition = (Integer) buttonView.getTag();
                    // Set the value of checkbox to maintain its state.
                    checkboxes[getPosition] = buttonView.isChecked();
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.tv_question, viewHolder.text);
            convertView.setTag(R.id.tb_toggle, viewHolder.toggle);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // This line is important. It puts back the proper button tag
        viewHolder.toggle.setTag(position);
        viewHolder.text.setText(questions.get(position));
        viewHolder.toggle.setChecked(checkboxes[position]);

        return convertView;
    }

}