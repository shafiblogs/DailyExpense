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
import com.de.dto.ExpenseDTO;
import com.de.provider.DataLayer;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);
        initializeViews(rootView);
        dataLayer = new DataLayer(getActivity());

        return rootView;
    }

    private void initializeViews(View rootView) {
        etAmount = (EditText) rootView.findViewById(R.id.et_amount);
        etDescription = (EditText) rootView.findViewById(R.id.et_description);
        spCategory = (Spinner) rootView.findViewById(R.id.sp_category);

        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("Select Category");
        list.add("Rent");
        list.add("Food");
        list.add("Recharge");
        list.add("Metro");
        list.add("Purchase");
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        final String asGmt = df.format(c.getTime()) + " GMT";

        ((Button) rootView.findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseDTO expenseDTO = new ExpenseDTO(spCategory.getSelectedItem().toString(), asGmt, 1, etDescription.getText().toString());
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                dataLayer.saveExpense(expenseDTO);

                etAmount.setText("");
                etDescription.setText("");
                spCategory.setSelection(0);
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
