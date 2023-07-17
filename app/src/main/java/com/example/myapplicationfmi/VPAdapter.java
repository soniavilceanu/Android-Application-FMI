package com.example.myapplicationfmi;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class VPAdapter extends FragmentStateAdapter {
    private static List<Fragment> fragments;

    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new announcements_fragment(this);
                break;
            case 1:
                fragment = new activities_fragment(this);
                break;
            default:
                fragment = new announcements_fragment(this);
                break;
        }
        fragments.add(fragment);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public Fragment getFragment(int position) {
        if (position >= 0 && position < fragments.size()) {
            return fragments.get(position);
        }
        return null;
    }
    public void updateFragments(Fragment rootView) {
        for(int i = 0; i < fragments.size(); i ++){
            if (fragments.get(i) instanceof activities_fragment && rootView instanceof activities_fragment ||
                    fragments.get(i) instanceof announcements_fragment && rootView instanceof announcements_fragment) {
                fragments.remove(fragments.get(i));
                fragments.add(rootView);
            }
        }
    }
}

