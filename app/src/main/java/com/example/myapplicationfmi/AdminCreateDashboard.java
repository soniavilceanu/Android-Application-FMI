package com.example.myapplicationfmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class AdminCreateDashboard extends AppCompatActivity {

    Button buttonCreateDashboardTab;

    // Generate dynamic view IDs
    int previousDashboardTabDateId;
    int previousDashboardTabTitleId;
    int previousDashboardTabBodyId;
    int previousDashboardTabId;
    RelativeLayout activitiesRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_dashboard);

//        View inflatedView = getLayoutInflater().inflate(R.layout.fragment_activities_fragment, null);
//        activitiesRelativeLayout = (RelativeLayout) inflatedView.findViewById(R.id.activitiesRelativeLayout);

//        // Get the FrameLayout container from the activity's layout
//        FrameLayout frameLayout = findViewById(R.id.frameLayoutContainer);
//        // Replace the content of the FrameLayout with the inflated view
//        frameLayout.addView(inflatedView);

        Button buttonVeziActiv = findViewById(R.id.buttonVeziActiv);
        buttonVeziActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCreateDashboard.this, DashboardActivity.class));
            }
        });

        buttonCreateDashboardTab = findViewById(R.id.buttonCreateDashboardTab);
        buttonCreateDashboardTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousDashboardTabId == 0){
                    previousDashboardTabId = R.id.dashboardTab;
                    previousDashboardTabTitleId = R.id.dashboardTabTitle;
                    previousDashboardTabDateId = R.id.dashboardTabDate;
                    previousDashboardTabBodyId = R.id.dashboardTabBody;


//                    View inflatedView = getLayoutInflater().inflate(R.layout.fragment_activities_fragment, null);
//                    previousDashboardTabId = inflatedView.findViewById(R.id.dashboardTab).getId();
//                    previousDashboardTabTitleId = inflatedView.findViewById(R.id.dashboardTabTitle).getId();
//                    previousDashboardTabDateId = inflatedView.findViewById(R.id.dashboardTabDate).getId();
//                    previousDashboardTabBodyId = inflatedView.findViewById(R.id.dashboardTabBody).getId();
                }

                // Generate dynamic view IDs
                int newDashboardTabDateId = View.generateViewId();
                int newDashboardTabTitleId = View.generateViewId();
                int newDashboardTabBodyId = View.generateViewId();
                int newDashboardTabId = View.generateViewId();

                // Inflate the parent layout from the desired layout file
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                //   ?
                View parentLayout = inflater.inflate(R.layout.fragment_activities_fragment, null);

               // ConstraintLayout container = parentLayout.findViewById(R.id.activitiesRelativeLayout);

                // Create a new ConstraintLayout
                ConstraintLayout constraintLayout = new ConstraintLayout(v.getContext());
                constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                constraintLayout.setId(newDashboardTabId);
                constraintLayout.setBackgroundResource(R.drawable.dashboard_article_background);
                constraintLayout.setPadding(10, 10, 10, 10);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
                layoutParams.setMarginStart(30);
                layoutParams.setMarginEnd(30);
                layoutParams.topMargin = 30;
                constraintLayout.setLayoutParams(layoutParams);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(constraintLayout.getId(), ConstraintSet.TOP,
                        previousDashboardTabId, ConstraintSet.BOTTOM);
                constraintSet.applyTo(constraintLayout);

                /*
                layoutParams.bottomToTop = R.id.dashboardTab;
                // Add the ConstraintLayout to its parent view
                ConstraintLayout parentLayout = findViewById(R.id.parentLayout); // Replace with the ID of the parent layout
                parentLayout.addView(constraintLayout);
                 */

                // Create the TextView for dashboardTabDate
                TextView dashboardTabDate = new TextView(v.getContext());
                dashboardTabDate.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                dashboardTabDate.setId(newDashboardTabDateId);
                dashboardTabDate.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.lavender_border));
                //dashboardTabDate.setBackgroundResource(R.drawable.lavender_border);
                dashboardTabDate.setPadding(8, 4, 8, 4);
                //dashboardTabDate2.setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4));
                dashboardTabDate.setText("11.11.1999");
                dashboardTabDate.setTypeface(null, Typeface.BOLD);
                dashboardTabDate.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                layoutParams = (ConstraintLayout.LayoutParams) dashboardTabDate.getLayoutParams();
                layoutParams.setMarginEnd(dpToPx(0));
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                dashboardTabDate.setLayoutParams(layoutParams);
                constraintLayout.addView(dashboardTabDate);



                // Create the ImageView
                ImageView imageView = new ImageView(v.getContext());
                imageView.setLayoutParams(new ConstraintLayout.LayoutParams(
                        dpToPx(30), dpToPx(30)));
                imageView.setId(View.generateViewId());
                imageView.setImageResource(R.drawable.baseline_open_in_new_24);
                imageView.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.black));

                layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                imageView.setLayoutParams(layoutParams);
                constraintLayout.addView(imageView);


                // Create the TextView for dashboardTabTitle
                TextView dashboardTabTitle = new TextView(v.getContext());
                dashboardTabTitle.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                dashboardTabTitle.setId(newDashboardTabTitleId);
                dashboardTabTitle.setText("TITLU NOU");
                dashboardTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                dashboardTabTitle.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));

                constraintSet = new ConstraintSet();         //?
                constraintSet.clone(constraintLayout);
                constraintSet.connect(dashboardTabTitle.getId(), ConstraintSet.TOP,
                        newDashboardTabDateId, ConstraintSet.BOTTOM);
                constraintSet.applyTo(constraintLayout);

                constraintLayout.addView(dashboardTabTitle);



                // Create the TextView for dashboardTabBody
                TextView dashboardTabBody = new TextView(v.getContext());
                dashboardTabBody.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                dashboardTabBody.setId(newDashboardTabBodyId);
                //dashboardTabBody2.setPadding(0, getResources().getDimensionPixelSize(R.dimen.margin_top), 0, 0);
                dashboardTabBody.setPadding(0, dpToPx(5), 0, 0);
                dashboardTabBody.setEllipsize(TextUtils.TruncateAt.END);
                dashboardTabBody.setMaxLines(3);
                dashboardTabBody.setText("LOREM lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem");
                //dashboardTabBody2.setTextColor(getResources().getColor(R.color.black));
                dashboardTabBody.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


                constraintSet = new ConstraintSet();       //?
                constraintSet.clone(constraintLayout);
                constraintSet.connect(dashboardTabBody.getId(), ConstraintSet.TOP,
                        newDashboardTabTitleId, ConstraintSet.BOTTOM);
                constraintSet.applyTo(constraintLayout);

                constraintLayout.addView(dashboardTabBody);



                previousDashboardTabBodyId = newDashboardTabBodyId;
                previousDashboardTabDateId = newDashboardTabDateId;
                previousDashboardTabId = newDashboardTabId;
                previousDashboardTabTitleId = newDashboardTabTitleId;

                //setContentView(parentLayout);
                //ViewGroup layout = (ViewGroup) findViewById(R.id.activitiesRelativeLayout);

//                ViewGroup parentLayout2 = (ViewGroup) inflater.inflate(R.layout.fragment_activities_fragment, null);
//                parentLayout2.addView(constraintLayout);


                //activitiesRelativeLayout.addView(constraintLayout);

                activities_fragment fragment = (activities_fragment) getSupportFragmentManager().findFragmentByTag("fragment_activities_fragment");
                View fragmentView = fragment.getView();
                if (fragmentView != null) {
                    RelativeLayout fragmentActivitiesRelativeLayout = fragmentView.findViewById(R.id.activitiesRelativeLayout);
                    activitiesRelativeLayout.addView(fragmentActivitiesRelativeLayout);
                }
                Intent intent = new Intent(AdminCreateDashboard.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /*
    private int dpToPx(Context context, int dp) {
    float density = context.getResources().getDisplayMetrics().density;
    return Math.round(dp * density);
}
     */
}