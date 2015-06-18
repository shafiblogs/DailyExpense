package com.de.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.de.activity.R;
import com.de.dto.CategoryDTO;
import com.de.dto.ReportDTO;

import java.util.ArrayList;

/**
 * Created by shafi on 6/18/2015.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CategoryDTO> categoryDTOs;
    private int languageId;
    private int fragmentType = 0;

    public CategoryAdapter(Context context, ArrayList<CategoryDTO> categoryDTOs, int fragmentType) {
        this.context = context;
        this.categoryDTOs = categoryDTOs;
        this.languageId = languageId;
        this.fragmentType = fragmentType;
    }


    @Override
    public View getView(int itemPos, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_category, null);
        }
        CategoryDTO categoryDTO = categoryDTOs.get(itemPos);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_type);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_order);
        tvCategory.setText(categoryDTO.getCategoryName());
        tvDate.setText(categoryDTO.getCategoryType());
        if (fragmentType == 0)
            tvAmount.setText("" + categoryDTO.getExpenseOrder());
        else if (fragmentType == 1)
            tvAmount.setText("" + categoryDTO.getIncomeOrder());

        return convertView;
    }

    @Override
    public int getCount() {
        return categoryDTOs.size();
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