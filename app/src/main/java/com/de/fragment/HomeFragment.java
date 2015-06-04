package com.de.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.de.activity.HomeActivity;
import com.de.activity.R;
import com.de.dto.CategoryDTO;
import com.de.provider.DataLayer;
import com.de.utils.StringUtils;

/**
 * Created by Shafi on 5/27/2015.
 */
public class HomeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText etCategoryName;
    private CheckBox chkExpense, chkIncome;
    private Button btnSave;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(rootView);
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

            etCategoryName.setText("");
            chkIncome.setChecked(false);
            chkExpense.setChecked(false);
        } else {
            Toast.makeText(getActivity(), "Please fill fields", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((HomeActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
