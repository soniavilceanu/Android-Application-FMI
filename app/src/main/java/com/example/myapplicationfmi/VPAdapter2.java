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

public class VPAdapter2 extends FragmentStateAdapter {
    private static List<Fragment> fragments;

    public VPAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new volunteerings_fragment(this);
                break;
            case 1:
                fragment = new internships_fragment(this);
                break;
            default:
                fragment = new volunteerings_fragment(this);
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
            if (fragments.get(i) instanceof internships_fragment && rootView instanceof internships_fragment ||
                    fragments.get(i) instanceof volunteerings_fragment && rootView instanceof volunteerings_fragment) {
                fragments.remove(fragments.get(i));
                fragments.add(rootView);
            }
        }
    }
}

