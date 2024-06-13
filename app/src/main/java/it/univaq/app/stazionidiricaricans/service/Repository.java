package it.univaq.app.stazionidiricaricans.service;
import android.content.Context;

/*
    classe che conterrà in memoria le variabili che ci permetteranno di effettuare le richieste internet
    l'istanza di questa classe sarà unica in tutta la applicazione, e verrà istanziata all'interno di NSApplication)
 */
public class Repository {

    //metodo statico che verrà chiamato quando vogliamo fare una richiesta internet
    public void downloadData(Context context, Request.RequestCallback callback) {
        Request.getInstance(context)
                .requestDownload(callback);

    }
}
