package com.de.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.de.activity.R;
import com.de.adapter.ReportAdapter;
import com.de.provider.DataLayer;

/**
 * Created by Shafi on 5/27/2015.
 */
public class ListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listView;
    private DataLayer dataLayer;
    private int fragmentType = 0;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListFragment newInstance(int sectionNumber) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        dataLayer = new DataLayer(getActivity());
        fragmentType = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        if (fragmentType == 0 && null != dataLayer.getExpense() && dataLayer.getExpense().size() > 0) {
            ReportAdapter catAdapt = new ReportAdapter(getActivity(), dataLayer.getExpense());
            listView.setAdapter(catAdapt);
        } else if (fragmentType == 1 && null != dataLayer.getIncome() && dataLayer.getIncome().size() > 0) {
            ReportAdapter catAdapt = new ReportAdapter(getActivity(), dataLayer.getIncome());
            listView.setAdapter(catAdapt);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((HomeActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
