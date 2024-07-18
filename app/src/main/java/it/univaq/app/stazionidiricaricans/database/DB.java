package it.univaq.app.stazionidiricaricans.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import it.univaq.app.stazionidiricaricans.model.Charger;

@Database(entities = { Charger.class}, version = 1)
public abstract class DB extends RoomDatabase{

    public abstract ChargerDAO getChargerDAO();

    //SINGLETON
    //volatile assicura che ogni cambiamento di instance sia subito visibile a tutti i threads
    public volatile static DB instance = null;

    //synchronized rende il metodo "thread-safe"
    public static synchronized DB getInstance(Context context) {
        if(instance == null) {
            //solo un thread alla volta
            synchronized (DB.class) {
                //build() fornirà l'implementazione di "getChargerDAO()"
                instance = Room.databaseBuilder(context, DB.class, "database.db")
                        .build();
            }
        }
        return instance;
    }
}
