package app.komatatsu.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class TodoRepository {
    private static final String DB_NAME = "todo";
    private static final String KEY = "data";

    static ArrayList<ToDo> loadItems(Context context) {
        SharedPreferences pref = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        String data = pref.getString(KEY, "");
        Log.w("debug!load", data);
        if (TextUtils.isEmpty(data) || data.equals("[]")) {
            return new ArrayList<>();
        } else {
            return new Gson().fromJson(data, new TypeToken<ArrayList<ToDo>>() {
            }.getType());
        }
    }

    static void addItem(Context context, ToDo item) {
        ArrayList<ToDo> items = loadItems(context);
        items.add(item);
        overrideItems(context, items);
    }

    static void deleteItem(Context context, ToDo target) {
        ArrayList<ToDo> items = loadItems(context);
        for (ToDo item : items) {
            if (item.getId() == target.getId()) {
                items.remove(item);
                break;
            }
        }
        overrideItems(context, items);
    }

    static void updateItem(Context context, ToDo target) {
        ArrayList<ToDo> items = loadItems(context);
        for (ToDo item : items) {
            if (item.getId() == target.getId()) {
                item.setTitle(target.getTitle());
                item.setCheck(target.getCheck());
                break;
            }
        }
        overrideItems(context, items);
    }

    private static void overrideItems(Context context, ArrayList<ToDo> items) {
        SharedPreferences pref = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        pref.edit().putString(KEY, new Gson().toJson(items)).apply();
    }
}
