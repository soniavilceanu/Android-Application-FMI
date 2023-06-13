package com.example.myapplicationfmi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class activities_fragment extends Fragment {

    FloatingActionButton buttonCreateDashboardTab;

    // Generate dynamic view IDs
    int previousDashboardTabDateId;
    int previousDashboardTabTitleId;
    int previousDashboardTabBodyId;
    int previousDashboardTabId;
    private RelativeLayout activitiesRelativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_activities_fragment, container, false);
        activitiesRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.activitiesRelativeLayout);

        buttonCreateDashboardTab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        buttonCreateDashboardTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousDashboardTabId == 0){
                    previousDashboardTabId = R.id.dashboardTab;
                    previousDashboardTabTitleId = R.id.dashboardTabTitle;
                    previousDashboardTabDateId = R.id.dashboardTabDate;
                    previousDashboardTabBodyId = R.id.dashboardTabBody;
                }

                // Generate dynamic view IDs
                int newDashboardTabDateId = View.generateViewId();
                int newDashboardTabTitleId = View.generateViewId();
                int newDashboardTabBodyId = View.generateViewId();
                int newDashboardTabId = View.generateViewId();

                RelativeLayout dashboardTab = new RelativeLayout(v.getContext());
                dashboardTab.setId(newDashboardTabId);
                RelativeLayout.LayoutParams dashboardTabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabParams.setMargins(dpToPx(30), dpToPx(30), dpToPx(30), 0);
                dashboardTab.setPadding(10, 10, 10, 10);
                dashboardTab.setLayoutParams(dashboardTabParams);
                dashboardTab.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.dashboard_article_background));

                TextView dashboardTabDate = new TextView(v.getContext());
                dashboardTabDate.setId(newDashboardTabDateId);
                RelativeLayout.LayoutParams dashboardTabDateParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabDateParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                dashboardTabDateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                dashboardTabDate.setLayoutParams(dashboardTabDateParams);
                dashboardTabDate.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.lavender_border));
                dashboardTabDate.setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4));
                dashboardTabDate.setText(R.string.dashboard_date);
                dashboardTabDate.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                dashboardTab.addView(dashboardTabDate);


                ImageView dashboardTabImage = new ImageView(v.getContext());
                dashboardTabImage.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(30), dpToPx(30)));
                dashboardTabImage.setImageResource(R.drawable.baseline_open_in_new_24);
                dashboardTabImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                dashboardTabImage.setId(View.generateViewId());
                RelativeLayout.LayoutParams dashboardTabImageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                dashboardTabImage.setLayoutParams(dashboardTabImageParams);
                dashboardTabImage.setColorFilter(ContextCompat.getColor(v.getContext(), android.R.color.black));
                dashboardTab.addView(dashboardTabImage);


                TextView dashboardTabTitle = new TextView(v.getContext());
                dashboardTabTitle.setId(newDashboardTabTitleId);
                RelativeLayout.LayoutParams dashboardTabTitleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabTitleParams.addRule(RelativeLayout.BELOW, newDashboardTabDateId);
                dashboardTabTitle.setLayoutParams(dashboardTabTitleParams);
                dashboardTabTitle.setText("Dap, alt titlu");
                dashboardTabTitle.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                dashboardTab.addView(dashboardTabTitle);


                TextView dashboardTabBody = new TextView(v.getContext());
                dashboardTabBody.setId(newDashboardTabBodyId);
                RelativeLayout.LayoutParams dashboardTabBody2Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabBody2Params.addRule(RelativeLayout.BELOW, newDashboardTabTitleId);
                dashboardTabBody.setLayoutParams(dashboardTabBody2Params);
                dashboardTabBody.setPadding(0, dpToPx(5), 0, 0);
                dashboardTabBody.setEllipsize(TextUtils.TruncateAt.END);
                dashboardTabBody.setMaxLines(3);
                dashboardTabBody.setText("Mai mult lorem ipsum, dar mai putin, draci, laci, etc.");
                dashboardTabBody.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                dashboardTab.addView(dashboardTabBody);

                RelativeLayout.LayoutParams dashboardTabParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabParams2.addRule(RelativeLayout.BELOW, previousDashboardTabId);
                dashboardTab.setLayoutParams(dashboardTabParams2);


                activitiesRelativeLayout.addView(dashboardTab);

                previousDashboardTabBodyId = newDashboardTabBodyId;
                previousDashboardTabDateId = newDashboardTabDateId;
                previousDashboardTabId = newDashboardTabId;
                previousDashboardTabTitleId = newDashboardTabTitleId;

            }
        });

        return rootView;
    }


    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

//    public static int dpToPx(Context context, float dp) {
//        float density = context.getResources().getDisplayMetrics().density;
//        return Math.round(dp * density);
//    }

    int[] toIntArray(ArrayList<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }
}