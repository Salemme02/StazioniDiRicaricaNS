package it.univaq.app.stazionidiricaricans.service;

import androidx.lifecycle.AndroidViewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import it.univaq.app.stazionidiricaricans.NSApplication;
import it.univaq.app.stazionidiricaricans.database.DB;
import it.univaq.app.stazionidiricaricans.model.Charger;

import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel { //usiamo AndroidViewModel quando abbiamo bisogno del contesto

    private MutableLiveData<List<Charger>> chargers = new MutableLiveData<>();

    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);


        repository = ((NSApplication) application).getRepository();
        //recupero la lista dal DB prima di fare la richiesta internet
        List<Charger> list = DB.getInstance(getApplication()).getChargerDAO().findAll();

        if (list.isEmpty()) {
            repository.downloadData(application, new Request.RequestCallback() {

                @Override
                public void onCompleted(UrlRequest request, UrlResponseInfo info, byte[] data, CronetException error) {

                    List<Charger> temp = new ArrayList<>();

                    //parsing dei dati
                    if (data != null) {
                        String response = new String(data);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject item = array.optJSONObject(i);
                                Charger s = Charger.parseJSON(item);
                                if (s != null) temp.add(s);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error != null) {
                            error.printStackTrace();
                        }
                    }
                    //salvataggio sul DB
                    DB.getInstance(getApplication()).getChargerDAO().insertChargers(temp);

                    //salvataggio sulla lista contenuta dal ViewModel
                    chargers.postValue(temp);
                }
            });

        } else {
            //main thread
            chargers.setValue(list);
        }
    }

    public MutableLiveData<List<Charger>> getChargers() {
        return chargers;
    }
}


