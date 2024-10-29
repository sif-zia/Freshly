package com.example.freshly.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProductPagerAdapter extends FragmentStateAdapter {
    public ProductPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FruitsFragment();
            case 2:
                return new VegetablesFragment();
            case 3:
                return new DryFruitsFragment();
            default:
                return new AllProdFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
