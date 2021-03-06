package com.example.calreminder;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.room.AutoMigration;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


@Entity
class ComponentData{

    @PrimaryKey(autoGenerate = true)
    public Integer Id;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "colorHex")
    public String colorHex;

    @ColumnInfo(name = "color")
    public Integer color;

    @ColumnInfo(name = "isNotified")
    public Boolean isNotified;

    public ComponentData(String text, String date, String time, String place, String colorHex, Integer color, Boolean isNotified) {
        this.Id = null;
        this.text = text;
        this.date = date;
        this.time = time;
        this.place = place;
        this.colorHex = colorHex;
        this.color = color;
        this.isNotified = isNotified;
    }

    @Ignore
    public ComponentData(Component component) {
        this.Id = null;
        this.text = component.text;
        this.date = component.date;
        this.time = component.time;
        this.place = component.place;
        this.colorHex = component.colorHex;
        this.color = component.color;
        this.isNotified = component.isNotified;
    }
}

@Dao
interface ComponentDataDao {

    @Insert
    public long insertComponent(ComponentData componentData);

    @Query("DELETE FROM ComponentData")
    public void deleteAllComponent();

    @Query("DELETE FROM ComponentData WHERE Id == :id")
    public void deleteComponent(int id);

    @Query("SELECT * FROM ComponentData WHERE Id == :id")
    public Component getComponentById(int id);

    @Query("SELECT * FROM ComponentData")
    public List<Component> getAllComponent();

    @Query("SELECT id FROM ComponentData WHERE rowId = :rowId")
    public Integer getId(long rowId);

    @Query("SELECT * FROM ComponentData WHERE date LIKE '%/%' ")
    public List<Component> getHasDateComponent();

    @Query("SELECT * FROM ComponentData WHERE text LIKE '%' || :input || '%'")
    public List<Component> getSearchedData(String input);

    @Query("SELECT * FROM ComponentData WHERE date LIKE '%' || :date || '%'")
    public List<Component> getSelectedDateData(String date);

    @Query("UPDATE ComponentData SET isNotified = :notified WHERE Id == :id")
    public void updateNotified(Integer id, Boolean notified);

    @Query("UPDATE ComponentData " +
            "SET text = :text," +
            "date = :date," +
            "time = :time," +
            "place = :place," +
            "colorHex = :colorHex," +
            "color = :color " +
            "WHERE Id = :id")
    public void updateComponent(Integer id, String text, String date, String time, String place, String colorHex, Integer color);
}

class Component {
    @PrimaryKey(autoGenerate = true)
    public Integer Id;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "colorHex")
    public String colorHex;

    @ColumnInfo(name = "color")
    public Integer color;

    @ColumnInfo(name = "isNotified")
    public Boolean isNotified;
}

@Database(
        entities = {ComponentData.class},
        version = 2,
        exportSchema = false)
abstract class AppDatabase extends RoomDatabase {
    public abstract ComponentDataDao componentDataDao();

}

public class CalreminderData {
    //??????????????? ????????? ???????????? ???????????? ?????????
    public static AppDatabase db;
    public static ComponentDataDao componentDataDao;
    public static final Integer baseId = 0x800;
    CalreminderData() {
    }
}
