package com.de.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.de.activity.R;
import com.de.dto.CategoryDTO;
import com.de.provider.DataLayer;
import com.de.utils.StringUtils;

/**
 * Created by Shafi on 6/4/2015.
 */
public class CategoryDialog extends DialogFragment {
    private EditText etCategoryName;
    private CheckBox chkExpense, chkIncome;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        initializeViews(rootView);
        getDialog().setTitle("Add new category here");
        // Do something else
        return rootView;
    }

    private void initializeViews(View rootView) {
        etCategoryName = (EditText) rootView.findViewById(R.id.et_category_name);
        chkExpense = (CheckBox) rootView.findViewById(R.id.ch_expense);
        chkIncome = (CheckBox) rootView.findViewById(R.id.ch_income);
        btnSave = (Button) rootView.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategoryDetails();
            }
        });
    }

    private void saveCategoryDetails() {
        String categoryName = etCategoryName.getText().toString();
        String categoryType = null;
        if (chkExpense.isChecked() && chkIncome.isChecked()) {
            categoryType = "B";
        } else if (chkExpense.isChecked()) {
            categoryType = "E";
        } else if (chkIncome.isChecked()) {
            categoryType = "I";
        }
        if (StringUtils.isNotBlank(categoryName) && StringUtils.isNotBlank(categoryType)) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryName(categoryName);
            categoryDTO.setCategoryType(categoryType);
            categoryDTO.setExpenseOrder(0);
            categoryDTO.setIncomeOrder(0);
            DataLayer dataLayer = new DataLayer(getActivity());
            dataLayer.saveCategory(categoryDTO);
            Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_LONG).show();
            this.dismiss();
        } else {
            Toast.makeText(getActivity(), "Please fill fields", Toast.LENGTH_LONG).show();
        }
    }
}
