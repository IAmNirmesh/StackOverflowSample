package com.android.nirmesh.stackoverflowsample.screens.common.activities;

import android.support.v7.app.AppCompatActivity;

import com.android.nirmesh.stackoverflowsample.MyApplication;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.CompositionRoot;

public class BaseActivity extends AppCompatActivity {

    protected CompositionRoot getCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
