package com.de.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.de.activity.R;
import com.de.controller.OnFragmentResult;
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
public class EntryFragment extends Fragment implements OnFragmentResult {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText etAmount, etDescription;
    private Spinner spCategory;
    private DataLayer dataLayer;
    private int fragmentType = 0;
    private OnFragmentResult fragmentResult;
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private String todayString;
    private static ViewPager mViewPager;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EntryFragment newInstance(int sectionNumber, ViewPager viewPager) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        mViewPager = viewPager;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entry, container, false);
        initializeViews(rootView);
        updateAdapterList();
        return rootView;
    }

    private void initializeViews(View rootView) {
        etAmount = (EditText) rootView.findViewById(R.id.et_amount);
        etDescription = (EditText) rootView.findViewById(R.id.et_description);
        spCategory = (Spinner) rootView.findViewById(R.id.sp_category);

        fragmentResult = this;
        list = new ArrayList<String>();
        dataLayer = new DataLayer(getActivity());
        fragmentType = getArguments().getInt(ARG_SECTION_NUMBER, 0);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM");
        todayString = df.format(c.getTime());

        ((Button) rootView.findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntryToDB();
            }
        });

        ((Button) rootView.findViewById(R.id.btn_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialog categoryDialog = new CategoryDialog();
                categoryDialog.setCallBack(fragmentResult);
                categoryDialog.show(getActivity().getFragmentManager(), "category");
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateAdapterList();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateAdapterList() {
        ArrayList<CategoryDTO> categoryList = null;
        if (fragmentType == 0)
            categoryList = dataLayer.getCategory("E");
        else if (fragmentType == 1)
            categoryList = dataLayer.getCategory("I");

        list = new ArrayList<String>();
        if (null != categoryList && categoryList.size() > 0)
            for (CategoryDTO categoryDTO : categoryList) {
                list.add(categoryDTO.getCategoryName());
            }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
    }

    private void saveEntryToDB() {
        String amount = etAmount.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString().trim();
        String description = etDescription.getText().toString().trim();
        if (StringUtils.isNotBlank(amount) && StringUtils.isNotBlank(description)) {
            ReportDTO expenseDTO = new ReportDTO(category, todayString, Integer.parseInt(amount), description);
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

    @Override
    public void onFragmentResult(String item) {
        updateAdapterList();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
