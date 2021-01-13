package com.jqielts.through.theworld.adapter.recyclerview.custom.tool.read;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jqielts.through.theworld.R;

/**
 * Created by Administrator on 2017/5/2.
 */

public class AnswerEditHolder extends RecyclerView.ViewHolder {

    public TextView item_title;
    public EditText item_discipline;

    public TextView item_content;

    public AnswerEditHolder(View itemView) {
        super(itemView);

        item_title = (TextView) itemView.findViewById(R.id.item_title);

        item_discipline = (EditText) itemView.findViewById(R.id.item_discipline);

        item_content = (TextView) itemView.findViewById(R.id.item_content);

    }
}
