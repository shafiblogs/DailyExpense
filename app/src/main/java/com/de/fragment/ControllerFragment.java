package com.de.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.de.activity.R;

import java.util.Locale;

/**
 * Created by Shafi on 6/17/2015.
 */
public class ControllerFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FragmentActivity myContext;
    private int fragmentType = 0;
    private View view;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ControllerFragment newInstance(int sectionNumber) {
        ControllerFragment fragment = new ControllerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_viewpager, container, false);
        fragmentType = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            if (fragmentType == 0)
                fragment = EntryFragment.newInstance(position, mViewPager);
            else if (fragmentType == 1)
                fragment = ReportFragment.newInstance(position);
            else if (fragmentType == 2)
                fragment = CategoryFragment.newInstance(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return ("Expense").toUpperCase(l);
                case 1:
                    return ("Income").toUpperCase(l);

            }
            return null;
        }
    }
}
