package com.de.fragment;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.de.activity.R;
import com.de.dto.CategoryDTO;
import com.de.dto.ReportDTO;
import com.de.provider.DataLayer;
import com.de.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Shafi on 5/28/2015.
 */
public class ExpenseFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText etAmount, etDescription;
    private Spinner spCategory;
    private DataLayer dataLayer;
    private int fragmentType = 0;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ExpenseFragment newInstance(int sectionNumber) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ExpenseFragment() {
    }

    @Override
    public void onPause() {
        super.onPause();

        ArrayList<CategoryDTO> categoryList = null;
        if (fragmentType == 0)
            categoryList = dataLayer.getCategory("E");
        else if (fragmentType == 1)
            categoryList = dataLayer.getCategory("I");


        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        for (CategoryDTO categoryDTO : categoryList) {
            list.add(categoryDTO.getCategoryName());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);
        dataLayer = new DataLayer(getActivity());
        fragmentType = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        initializeViews(rootView);

        return rootView;
    }

    private void initializeViews(View rootView) {
        etAmount = (EditText) rootView.findViewById(R.id.et_amount);
        etDescription = (EditText) rootView.findViewById(R.id.et_description);
        spCategory = (Spinner) rootView.findViewById(R.id.sp_category);

        ArrayList<CategoryDTO> categoryList = null;
        if (fragmentType == 0)
            categoryList = dataLayer.getCategory("E");
        else if (fragmentType == 1)
            categoryList = dataLayer.getCategory("I");


        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        for (CategoryDTO categoryDTO : categoryList) {
            list.add(categoryDTO.getCategoryName());
        }

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        final String asGmt = df.format(c.getTime());

        ((Button) rootView.findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString().trim();
                String category = spCategory.getSelectedItem().toString().trim();
                String description = etDescription.getText().toString().trim();
                if (StringUtils.isNotBlank(amount) && StringUtils.isNotBlank(description)) {
                    ReportDTO expenseDTO = new ReportDTO(category, asGmt, Integer.parseInt(amount), description);
                    if (fragmentType == 0)
                        dataLayer.saveExpense(expenseDTO);
                    else if (fragmentType == 1)
                        dataLayer.saveIncome(expenseDTO);

                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();

                    etAmount.setText("");
                    etDescription.setText("");
                    spCategory.setSelection(0);
                } else {
                    Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        ((Button) rootView.findViewById(R.id.btn_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialog categoryDialog = new CategoryDialog();
                categoryDialog.show(getActivity().getFragmentManager(), "category");
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((HomeActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
