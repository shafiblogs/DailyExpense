package com.de.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.de.activity.R;
import com.de.dto.ReportDTO;

import java.util.ArrayList;

/**
 * Created by Shafi on 5/28/2015.
 */
public class ReportAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ReportDTO> expenseDTOs;
    private int languageId;

    public ReportAdapter(Context context, ArrayList<ReportDTO> expenseDTOs) {
        this.context = context;
        this.expenseDTOs = expenseDTOs;
        this.languageId = languageId;
    }


    @Override
    public View getView(int itemPos, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_expense, null);
        }
        ReportDTO expenseDTO = expenseDTOs.get(itemPos);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tv_category);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
        tvCategory.setText(expenseDTO.getCategory());
        tvDate.setText(expenseDTO.getDate());
        tvAmount.setText("" + expenseDTO.getAmount());

        return convertView;
    }

    @Override
    public int getCount() {
        return expenseDTOs.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }
}