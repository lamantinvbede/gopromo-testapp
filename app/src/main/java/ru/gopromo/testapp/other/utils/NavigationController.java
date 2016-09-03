package ru.gopromo.testapp.other.utils;


import android.support.v4.app.Fragment;

public interface NavigationController {
    void replaceFragment(Fragment fragment, boolean addBackStack);
}
