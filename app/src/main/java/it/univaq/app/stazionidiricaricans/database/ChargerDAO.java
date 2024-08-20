package it.univaq.app.stazionidiricaricans.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.univaq.app.stazionidiricaricans.model.Charger;

@Dao
public interface ChargerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertChargers(List<Charger> chargers);

    @Query("DELETE FROM chargers")
    public void deleteAll();

    @Query("SELECT * FROM chargers ORDER BY country ASC")
    public List<Charger> findAll();

}
