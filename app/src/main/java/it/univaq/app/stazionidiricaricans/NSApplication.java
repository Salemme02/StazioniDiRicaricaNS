package it.univaq.app.stazionidiricaricans;

import android.app.Application;

import it.univaq.app.stazionidiricaricans.service.Repository;

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
