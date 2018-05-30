package app.komatatsu.todo

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object TodoRepository {
    private const val DB_NAME = "todo"
    private const val KEY = "data"

    internal fun loadItems(context: Context): ArrayList<ToDo> {
        val pref = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE)
        val data = pref.getString(KEY, "")
        return if (TextUtils.isEmpty(data) || "[]" == data) {
            ArrayList()
        } else {
            Gson().fromJson(data, object : TypeToken<ArrayList<ToDo>>() {
            }.type)
        }
    }

    internal fun addItem(context: Context, item: ToDo) {
        val items = loadItems(context)
        items.add(item)
        overrideItems(context, items)
    }

    internal fun deleteItem(context: Context, target: ToDo) {
        val items = loadItems(context)
        for (item in items) {
            if (item.id == target.id) {
                items.remove(item)
                break
            }
        }
        overrideItems(context, items)
    }

    internal fun updateItem(context: Context, target: ToDo) {
        val items = loadItems(context)
        for (item in items) {
            if (item.id == target.id) {
                item.title = target.title
                item.check = target.check
                break
            }
        }
        overrideItems(context, items)
    }

    private fun overrideItems(context: Context, items: ArrayList<ToDo>) {
        val pref = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(KEY, Gson().toJson(items)).apply()
    }
}
