/*
 * A sliding menu for Android, very much like the Google+ and Facebook apps have.
 *
 * Copyright (C) 2012 CoboltForge
 *
 * Based upon the great work done by stackoverflow user Scirocco (http://stackoverflow.com/a/11367825/361413), thanks a lot!
 * The XML parsing code comes from https://github.com/darvds/RibbonMenu, thanks!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.de.views;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.de.activity.R;

import com.de.constant.AppConstants;
import com.de.controller.OnFragmentResult;
import com.de.provider.SHPreference;
import com.de.utils.StringUtils;


public class SlideMenu extends LinearLayout implements OnClickListener {
    private Set<Integer> boldElements = null;
    private Typeface mFont;

    // keys for saving/restoring instance state
    private final static String KEY_MENUSHOWN = "menuWasShown";
    private final static String KEY_STATUSBARHEIGHT = "statusBarHeight";
    private final static String KEY_SUPERSTATE = "superState";

    // this tells whether the menu is currently shown
    private boolean menuIsShown = false;
    // this just tells whether the menu was ever shown
    private boolean menuWasShown = false;
    private int statusHeight = -1;
    private static View menuSlider;
    private static ViewGroup content;
    private static FrameLayout parent;
    private static int menuSize;
    private Activity act;
    private TranslateAnimation slideRightAnim;
    private TranslateAnimation slideMenuLeftAnim;
    private TranslateAnimation slideContentLeftAnim;
    private SHPreference shPref;
    private SlideMenuInterface.OnSlideMenuItemClickListener callback;
    private OnFragmentResult frCallback;

    /**
     * Constructor used by the inflation apparatus. To be able to use the
     * SlideMenu, call the {@link #init init()} method.
     *
     * @param context
     */
    public SlideMenu(Context context) {
        super(context);
    }

    /**
     * Constructor used by the inflation apparatus. To be able to use the
     * SlideMenu, call the {@link #init init()} method.
     *
     * @param attrs
     */
    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructs a SlideMenu with the given menu XML.
     *
     * @param act           The calling activity.
     * @param menuResource  Menu resource identifier.
     * @param cb            Callback to be invoked on menu item click.
     * @param slideDuration Slide in/out duration in milliseconds.
     */
    public SlideMenu(Activity act, int menuResource, SlideMenuInterface.OnSlideMenuItemClickListener cb, int slideDuration,
                     OnFragmentResult fr) {
        super(act);
        init(act, menuResource, cb, slideDuration, fr);
    }

    /**
     * Constructs an empty SlideMenu.
     *
     * @param act           The calling activity.
     * @param cb            Callback to be invoked on menu item click.
     * @param slideDuration Slide in/out duration in milliseconds.
     */
    public SlideMenu(Activity act, SlideMenuInterface.OnSlideMenuItemClickListener cb, int slideDuration, OnFragmentResult fr) {
        this(act, 0, cb, slideDuration, fr);
    }

    /**
     * If inflated from XML, initializes the SlideMenu.
     *
     * @param act           The calling activity.
     * @param menuResource  Menu resource identifier, can be 0 for an empty SlideMenu.
     * @param cb            Callback to be invoked on menu item click.
     * @param slideDuration Slide in/out duration in milliseconds.
     */
    public void init(Activity act, int menuResource, SlideMenuInterface.OnSlideMenuItemClickListener cb, int slideDuration,
                     OnFragmentResult fr) {

        this.act = act;
        this.callback = cb;
        this.frCallback = fr;
        shPref = new SHPreference(act);
        // set menu size from dimension folder
        int menuWidth = (int) (getResources().getDimension(R.dimen.slide_menu_width) / getResources().getDisplayMetrics().density);
        menuSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, menuWidth, act.getResources().getDisplayMetrics());

        // create animations accordingly
        slideRightAnim = new TranslateAnimation(-menuSize, 0, 0, 0);
        slideRightAnim.setFillAfter(true);
        slideMenuLeftAnim = new TranslateAnimation(0, -menuSize, 0, 0);
        slideMenuLeftAnim.setFillAfter(true);
        slideContentLeftAnim = new TranslateAnimation(menuSize, 0, 0, 0);
        slideContentLeftAnim.setFillAfter(true);
        setAnimationDuration(slideDuration);
    }

    /**
     * Set how long slide animation should be
     *
     * @param slideDuration How long to set the slide animation
     * @see TranslateAnimation#setDuration(long)
     */
    public void setAnimationDuration(long slideDuration) {
        slideRightAnim.setDuration(slideDuration);
        slideMenuLeftAnim.setDuration(slideDuration * 3 / 2);
        slideContentLeftAnim.setDuration(slideDuration * 3 / 2);
    }

    /**
     * Set an Interpolator for the slide animation.
     *
     * @param i The {@link Interpolator} object to set.
     * @see TranslateAnimation#setInterpolator(Interpolator)
     */
    public void setAnimationInterpolator(Interpolator i) {
        slideRightAnim.setInterpolator(i);
        slideMenuLeftAnim.setInterpolator(i);
        slideContentLeftAnim.setInterpolator(i);
    }

    /**
     * Slide the menu in.
     */
    public void show() {
        this.show(true);
    }

    /**
     * Set the menu to shown status without displaying any slide animation.
     */
    public void setAsShown() {
        this.show(false);
    }

    @SuppressLint("NewApi")
    private void show(boolean animate) {

		/*
         * We have to adopt to status bar height in most cases, but not if there
		 * is a support actionbar!
		 */
        try {
            Method getSupportActionBar = act.getClass().getMethod("getSupportActionBar", (Class[]) null);
            Object sab = getSupportActionBar.invoke(act, (Object[]) null);
            sab.toString(); // check for null

            if (Build.VERSION.SDK_INT >= 11) {
                // over api level 11? add the margin
                getStatusbarHeight();
            }
        } catch (Exception es) {
            // there is no support action bar!
            getStatusbarHeight();
        }

        // modify content layout params
        try {
            content = ((LinearLayout) act.findViewById(android.R.id.content).getParent());
        } catch (ClassCastException e) {
            /*
             * When there is no title bar
			 * (android:theme="@android:style/Theme.NoTitleBar"), the
			 * android.R.id.content FrameLayout is directly attached to the
			 * DecorView, without the intermediate LinearLayout that holds the
			 * titlebar plus content.
			 */

            // if (Build.VERSION.SDK_INT < 18)
            // content = (ViewGroup) act.findViewById(android.R.id.content);
            // else
            content = (ViewGroup) act.findViewById(android.R.id.content).getParent(); // FIXME?
            // what
            // about
            // the
            // corner
            // cases
            // (fullscreen
            // etc)
        }

        FrameLayout.LayoutParams parm = new FrameLayout.LayoutParams(-1, -1, 3);
        parm.setMargins(menuSize, 0, -menuSize, 0);
        content.setLayoutParams(parm);

        // animation for smooth slide-out
        if (animate)
            content.startAnimation(slideRightAnim);

        // quirk for sony xperia devices on ICS only, shouldn't hurt on others
        if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 15 && Build.MANUFACTURER.contains("Sony") && menuWasShown)
            content.setX(menuSize);

        // add the slide menu to parent
        try {
            parent = (FrameLayout) content.getParent();
        } catch (ClassCastException e) {
            /*
             * Most probably a LinearLayout, at least on Galaxy S3.
			 * https://github.com/bk138/LibSlideMenu/issues/12
			 */
            LinearLayout realParent = (LinearLayout) content.getParent();
            parent = new FrameLayout(act);
            realParent.addView(parent, 0); // add FrameLayout to real parent of
            // content
            realParent.removeView(content); // remove content from real parent
            parent.addView(content); // add content to FrameLayout
        }

        refreshMenuSlider();

        if (animate)
            menuSlider.startAnimation(slideRightAnim);
    }

    private void refreshMenuSlider() {
        if (null != menuSlider)
            parent.removeView(menuSlider);

        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuSlider = inflater.inflate(R.layout.custom_slidemenu, null);

        FrameLayout.LayoutParams lays = new FrameLayout.LayoutParams(-1, -1, 3);
        lays.setMargins(0, statusHeight, 0, 0);
        menuSlider.setLayoutParams(lays);

        parent.addView(menuSlider);
//        HexagonImageView profile_side = (HexagonImageView) menuSlider.findViewById(R.id.profile_side);
//        String playerUrl = StringUtils.recreateUrl(URLManager.IMG_BASE_URL + config.getUserDao().getmThumbnail());
//        ImageLoaderManager.getImageLoaderObj(act.getApplicationContext()).DisplayImage(playerUrl, profile_side);
//        ((TextView) menuSlider.findViewById(R.id.tv_profileimage)).setText(config.getUserDao().getmDisplayName().trim());
//        ((TextView) menuSlider.findViewById(R.id.tv_home)).setOnClickListener(this);
//        // ((TextView)
//        // menuSlider.findViewById(R.id.tv_profileimage)).setOnClickListener(this);
//        ((HexagonImageView) menuSlider.findViewById(R.id.profile_side)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.textView1)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_notification)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_settings)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_language)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_share)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_store)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_feedback)).setOnClickListener(this);
//
//        ((Switch) menuSlider.findViewById(R.id.switch_language)).setOnClickListener(this);
//
//        if (LanguageCacheObj.getInstance().getLanguge().equalsIgnoreCase(AppConstants.LANG_AR)) {
//            ((Switch) menuSlider.findViewById(R.id.switch_language)).setChecked(true);
//            if (null != config.getUserDao() && null != config.getUserDao().getmTitleDescAr())
//                ((TextView) menuSlider.findViewById(R.id.tv_secondname)).setText(config.getUserDao().getmTitleDescAr().trim());
//
//        } else {
//            ((Switch) menuSlider.findViewById(R.id.switch_language)).setChecked(false);
//            if (null != config.getUserDao() && null != config.getUserDao().getmTitleDesc())
//                ((TextView) menuSlider.findViewById(R.id.tv_secondname)).setText(config.getUserDao().getmTitleDesc().trim());
//
//        }
//
//        ((TextView) menuSlider.findViewById(R.id.tv_addquestion)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_feedback)).setOnClickListener(this);
//        ((TextView) menuSlider.findViewById(R.id.tv_share)).setOnClickListener(this);

        menuSlider.findViewById(R.id.overlay).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideMenu.this.hide();
            }
        });
        enableDisableViewGroup(content, false);

        menuIsShown = true;
        menuWasShown = true;

        refreshFonts();
    }

    private void refreshFonts() {
        boldElements = new HashSet<Integer>();
        // boldElements.add(R.id.tv_home);
        // boldElements.add(R.id.tv_notification);
        // boldElements.add(R.id.tv_settings);
        // boldElements.add(R.id.tv_language);
        // boldElements.add(R.id.tv_addquestion);
        // boldElements.add(R.id.tv_feedback);
//        if (LanguageCacheObj.getInstance().getLanguge().equalsIgnoreCase(AppConstants.LANG_AR))
//            mFont = Typeface.createFromAsset(act.getAssets(), AppConstants.FONT_NORMAL_ARABIC);
//        else if (LanguageCacheObj.getInstance().getLanguge().equalsIgnoreCase(AppConstants.LANG_EN))
//            mFont = Typeface.createFromAsset(act.getAssets(), AppConstants.FONT_NORMAL_ENGLISH);
//        LinearLayout container = (LinearLayout) menuSlider.findViewById(R.id.cont_custom_slidemenu);
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        if (DisplayMetrics.DENSITY_LOW != dm.densityDpi) {
//            ViewUtils.setAppFont(container, mFont, boldElements);
//        }
    }

    /**
     * Slide the menu out.
     */
    @SuppressLint("NewApi")
    public void hide() {
        menuSlider.startAnimation(slideMenuLeftAnim);
        parent.removeView(menuSlider);

        content.startAnimation(slideContentLeftAnim);

        FrameLayout.LayoutParams parm = (FrameLayout.LayoutParams) content.getLayoutParams();
        parm.setMargins(0, 0, 0, 0);
        content.setLayoutParams(parm);
        enableDisableViewGroup(content, true);

        // quirk for sony xperia devices on ICS only, shouldn't hurt on others
        if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 15 && Build.MANUFACTURER.contains("Sony"))
            content.setX(0);

        menuIsShown = false;
    }

    private void getStatusbarHeight() {
        // Only do this if not already set.
        // Especially when called from within onCreate(), this does not return
        // the true values.
        if (statusHeight == -1) {
            Rect r = new Rect();
            Window window = act.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(r);
            statusHeight = r.top;
        }
    }

    // originally:
    // http://stackoverflow.com/questions/5418510/disable-the-touch-events-for-all-the-views
    // modified for the needs here
    private void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view.isFocusable())
                view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            } else if (view instanceof ListView) {
                if (view.isFocusable())
                    view.setEnabled(enabled);
                ListView listView = (ListView) view;
                int listChildCount = listView.getChildCount();
                for (int j = 0; j < listChildCount; j++) {
                    if (view.isFocusable())
                        listView.getChildAt(j).setEnabled(false);
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {

            if (state instanceof Bundle) {
                Bundle bundle = (Bundle) state;

                statusHeight = bundle.getInt(KEY_STATUSBARHEIGHT);

                if (bundle.getBoolean(KEY_MENUSHOWN))
                    show(false); // show without animation

                super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPERSTATE));

                return;
            }

            super.onRestoreInstanceState(state);

        } catch (NullPointerException e) {
            // in case the menu was not declared via XML but added from code
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPERSTATE, super.onSaveInstanceState());
        bundle.putBoolean(KEY_MENUSHOWN, menuIsShown);
        bundle.putInt(KEY_STATUSBARHEIGHT, statusHeight);

        return bundle;
    }

    @Override
    public void onClick(View v) {
//        if (null != v) {
//            switch (v.getId()) {
//                case R.id.tv_home:
//                    selectItem(0);
//                    break;
//                case R.id.profile_side:
//                    selectItem(1);
//                    break;
//                case R.id.textView1:
//                    selectItem(1);
//                    break;
//                case R.id.tv_notification:
//                    selectItem(2);
//                    break;
//                case R.id.tv_settings:
//                    selectItem(3);
//                    break;
//                case R.id.tv_language:
//                    selectItem(4);
//                    break;
//                case R.id.switch_language:
//                    switchLanguage();
//                    break;
//                case R.id.tv_addquestion:
//                    selectItem(5);
//                    break;
//                case R.id.tv_feedback:
//                    selectItem(6);
//                    break;
//                case R.id.tv_share:
//                    shareTHings();
//                    break;
//                case R.id.tv_store:
//                    selectItem(7);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    private void shareTHings() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide what to do
        // with it.
        intent.putExtra(Intent.EXTRA_SUBJECT, "This is demo text to share #kwizy");
        intent.putExtra(Intent.EXTRA_TEXT, "http://facebook.com");
        act.startActivity(Intent.createChooser(intent, "Share via"));
    }

    private void refreshCurrentFrgment() {
        String tagTab = "tagTab";
        Tab tab = act.getActionBar().getSelectedTab();
        if (null != tab)
            tagTab = (String) tab.getTag();
        frCallback.onFragmentResult(tagTab);
    }

    public void selectItem(int pos) {
        if (callback != null)
            callback.onSlideMenuItemClick(pos);
        if (null != menuSlider)
            hide();
    }

}
