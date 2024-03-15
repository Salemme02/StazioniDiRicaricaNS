package com.example.stazionidiricaricans;

import android.app.Application;

import com.example.stazionidiricaricans.service.Repository;

public class NSApplication extends Application {

    Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        repository = new Repository();
    }

    public Repository getRepository() {
        return repository;
    }
}
