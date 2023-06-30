package com.example.myapplicationfmi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class activities_fragment extends Fragment {



    // Generate dynamic view IDs
    int previousDashboardTabDateId;
    int previousDashboardTabTitleId;
    int previousDashboardTabBodyId;
    int previousDashboardTabId;

    ImageView dashboardTabImage;
    Button dashboardTabDelete;
    private RelativeLayout activitiesRelativeLayout;
    private LinearLayout fillDashboardTabInfo;
    FloatingActionButton buttonCreateDashboardTab;
    private ArrayList<Integer> dashboardTabIds;
    private Button addDashboardTabInfo;
    private EditText addDashboardTitle;
    private EditText addDashboardBody;
    private EditText addDashboardLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        dashboardTabIds = new ArrayList<Integer>();

        View rootView = inflater.inflate(R.layout.fragment_activities_fragment, container, false);
        activitiesRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.activitiesRelativeLayout);
        dashboardTabImage = (ImageView) rootView.findViewById(R.id.dashboardTabImage);
        dashboardTabDelete = (Button) rootView.findViewById(R.id.dashboardTabDelete);

        fillDashboardTabInfo = (LinearLayout) rootView.findViewById(R.id.fillDashboardTabInfo);
        addDashboardTabInfo = (Button) rootView.findViewById(R.id.addDashboardTabInfo);
        addDashboardTitle = (EditText) rootView.findViewById(R.id.addDashboardTitle);
        addDashboardBody = (EditText) rootView.findViewById(R.id.addDashboardBody);
        addDashboardLink = (EditText) rootView.findViewById(R.id.addDashboardLink);

        //daca e admin
        //dashboardTabDelete.setVisibility(View.VISIBLE);


        buttonCreateDashboardTab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        //daca e admin
        buttonCreateDashboardTab.setVisibility(View.VISIBLE);


        for (int j = 0; j < activitiesRelativeLayout.getChildCount(); j++) {

            RelativeLayout childDashboard = (RelativeLayout) activitiesRelativeLayout.getChildAt(j);
            for (int i = 0; i < childDashboard.getChildCount(); i++) {
                View childView = childDashboard.getChildAt(i);

                if (childView instanceof ImageView) {
                    final int imageViewId = childView.getId();
                    childView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            handleButtonClick(imageViewId);
                        }
                    });
                }
                if (childView instanceof Button) {
                    final int buttonId = childView.getId();

                    //daca e admin facem butonul de delete visibil
                    ((Button)rootView.findViewById(buttonId)).setVisibility(View.VISIBLE);

                    RelativeLayout dashboardParent = (RelativeLayout) ((Button)rootView.findViewById(buttonId)).getParent();
                    childView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            activitiesRelativeLayout.removeView(dashboardParent);
                            if(dashboardTabIds.size() == 1)
                                previousDashboardTabId = R.id.failSafe;
                            else{
                                int nextId = dashboardTabIds.get(1);
                                previousDashboardTabId = nextId;
                            }
                            dashboardTabIds.remove((Object)dashboardParent.getId());
                        }
                    });
                }
            }
        }

        buttonCreateDashboardTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(fillDashboardTabInfo);
                fillDashboardTabInfo.setVisibility(View.VISIBLE);
            }
        });
        addDashboardTabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousDashboardTabId == 0){
                    previousDashboardTabId = R.id.dashboardTab;
                    previousDashboardTabTitleId = R.id.dashboardTabTitle;
                    previousDashboardTabDateId = R.id.dashboardTabDate;
                    previousDashboardTabBodyId = R.id.dashboardTabBody;
                    dashboardTabIds.add(previousDashboardTabId);
                }

                // Generate dynamic view IDs
                int newDashboardTabDateId = View.generateViewId();
                int newDashboardTabTitleId = View.generateViewId();
                int newDashboardTabBodyId = View.generateViewId();
                int newDashboardTabId = View.generateViewId();
                int newDashboardImageId = View.generateViewId();
                int newDashboardDeleteId = View.generateViewId();

                dashboardTabIds.add(newDashboardTabId);

                RelativeLayout dashboardTab = new RelativeLayout(v.getContext());
                dashboardTab.setId(newDashboardTabId);
                RelativeLayout.LayoutParams dashboardTabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabParams.setMargins(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), 0);
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
                dashboardTabDate.setPadding(dpToPx(v.getContext(),8), dpToPx(v.getContext(),4), dpToPx(v.getContext(),8), dpToPx(v.getContext(),4));
                dashboardTabDate.setText(sdf.format(new Date()));
                dashboardTabDate.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                dashboardTab.addView(dashboardTabDate);

                ImageView dashboardTabImage = new ImageView(v.getContext());
                dashboardTabImage.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30)));
                dashboardTabImage.setImageResource(R.drawable.baseline_open_in_new_24);
                dashboardTabImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                dashboardTabImage.setId(newDashboardImageId);
                RelativeLayout.LayoutParams dashboardTabImageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                dashboardTabImage.setLayoutParams(dashboardTabImageParams);
                dashboardTab.addView(dashboardTabImage);

                Button dashboardTabDelete = new Button(v.getContext());
                dashboardTabDelete.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30)));
                dashboardTabDelete.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.baseline_close_24));
                dashboardTabDelete.setId(newDashboardDeleteId);

                RelativeLayout.LayoutParams dashboardTabDeleteParams = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30)));
                dashboardTabDeleteParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                dashboardTabDeleteParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                dashboardTabDeleteParams.setMargins(dpToPx(v.getContext(),40), 0,0,0);
                dashboardTabDelete.setLayoutParams(dashboardTabDeleteParams);
                dashboardTab.addView(dashboardTabDelete);

                ///////////////////////////////////////////////////////////////////////////////////////////////////
                //temporar, va trebui scos cand le incarcam din baza
                dashboardTabDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activitiesRelativeLayout.removeView(dashboardTab);
                        if(dashboardTabIds.indexOf((Object)dashboardTab.getId()) == dashboardTabIds.size() - 1 && dashboardTabIds.size() > 1){
                            //is the last element in the dashboardTab list and the list is not made of only 1 element
                            int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) - 1);
                            previousDashboardTabId = previousId;
                        }
                        else if(dashboardTabIds.size() > 1){
                            int nextId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) + 1);

                            if(dashboardTabIds.indexOf((Object)dashboardTab.getId()) != 0){
//                               //is the first element in the dashboardTab list
//                                previousDashboardTabId = nextId;
//                            }
//                            else {
                                //is a middle element in the dashboardTab list
                                int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) - 1);
                                RelativeLayout nextDashboard = rootView.findViewById(nextId);

                                RelativeLayout.LayoutParams dashboardTabParamsToDelete = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dashboardTabParamsToDelete.addRule(RelativeLayout.BELOW, previousId);
                                dashboardTabParamsToDelete.setMargins(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), 0);
                                nextDashboard.setPadding(10, 10, 10, 10);
                                nextDashboard.setLayoutParams(dashboardTabParamsToDelete);

                            }
                        }
                        dashboardTabIds.remove((Object)dashboardTab.getId());
                    }
                });

                TextView dashboardTabTitle = new TextView(v.getContext());
                dashboardTabTitle.setId(newDashboardTabTitleId);
                RelativeLayout.LayoutParams dashboardTabTitleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabTitleParams.addRule(RelativeLayout.BELOW, newDashboardTabDateId);
                dashboardTabTitle.setLayoutParams(dashboardTabTitleParams);
                dashboardTabTitle.setText(addDashboardTitle.getText());
                dashboardTabTitle.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                dashboardTab.addView(dashboardTabTitle);


                TextView dashboardTabBody = new TextView(v.getContext());
                dashboardTabBody.setId(newDashboardTabBodyId);
                RelativeLayout.LayoutParams dashboardTabBody2Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabBody2Params.addRule(RelativeLayout.BELOW, newDashboardTabTitleId);
                dashboardTabBody.setLayoutParams(dashboardTabBody2Params);
                dashboardTabBody.setPadding(0, dpToPx(v.getContext(),5), 0, 0);
                dashboardTabBody.setEllipsize(TextUtils.TruncateAt.END);
                dashboardTabBody.setMaxLines(3);
                dashboardTabBody.setText(addDashboardBody.getText());
                dashboardTabBody.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                dashboardTab.addView(dashboardTabBody);

                RelativeLayout.LayoutParams dashboardTabParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabParams2.addRule(RelativeLayout.BELOW, previousDashboardTabId);
                dashboardTabParams2.setMargins(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), 0);
                dashboardTab.setPadding(10, 10, 10, 10);
                dashboardTab.setLayoutParams(dashboardTabParams2);


                activitiesRelativeLayout.addView(dashboardTab);

                previousDashboardTabBodyId = newDashboardTabBodyId;
                previousDashboardTabDateId = newDashboardTabDateId;
                previousDashboardTabId = newDashboardTabId;
                previousDashboardTabTitleId = newDashboardTabTitleId;

                addDashboardBody.setText("");
                addDashboardLink.setText("");
                addDashboardTitle.setText("");

                TransitionManager.beginDelayedTransition(fillDashboardTabInfo);
                fillDashboardTabInfo.setVisibility(View.GONE);
            }

            /*
            sa adaugam in baza ce completam aici ca sa incarcam dashboard-urile la fiecare deschidere de aplicatie
            optiune de formatare textȘ new line bold, underline, italic
            optiune adugare poza din galerie ca fundal pe dashboard
            extindere sau deschidere dashboard pt a vedea textul complet fara sa intram pe site
            titlul si body-ul textului sa aiba un minim de caractere
            link mandatoriu
            legare link sa deschida ceva dupa adaugarea in baza
             */
        });

        return rootView;
    }

    private void handleButtonClick(int buttonId) {

        if(buttonId == dashboardTabImage.getId()){
            goToUrl("https://fmi.unibuc.ro/noutati/conferinta-lunara-a-fmi-joi-15-iunie-2023-ora-14-amfiteatrul-stoilow-c-s-1-dr-dorin-popescu-imar-teorema-functiilor-implicite-si-aplicatiile-ei-in-algebra/\n");
        }
    }

    private void goToUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    boolean isWithinEditTextBounds(int xPoint, int yPoint) {
        int[] l = new int[2];
        fillDashboardTabInfo.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        int w = fillDashboardTabInfo.getWidth();
        int h = fillDashboardTabInfo.getHeight();

        if (xPoint< x || xPoint> x + w || yPoint< y || yPoint> y + h) {
            return false;
        }
        return true;
    }
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (fillDashboardTabInfo.getVisibility() == View.VISIBLE && !isWithinEditTextBounds((int) ev.getRawX(), (int) ev.getRawY())){
            TransitionManager.beginDelayedTransition(fillDashboardTabInfo);
            fillDashboardTabInfo.setVisibility(View.GONE);
            addDashboardBody.setText("");
            addDashboardLink.setText("");
            addDashboardTitle.setText("");
            return true;
        }
        return false;
    }

//    private int dpToPx(int dp) {
//        float density = getResources().getDisplayMetrics().density;
//        return Math.round(dp * density);
//    }

    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    int[] toIntArray(ArrayList<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }
}