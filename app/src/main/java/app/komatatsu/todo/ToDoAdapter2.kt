package app.komatatsu.todo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ToDoAdapter2(private val context: Context) : RecyclerView.Adapter<ToDoAdapter2.ToDoHolder>() {

    private var items: ArrayList<ToDo> = load()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        return ToDoHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false))
    }

    override fun onBindViewHolder(holder: ToDoAdapter2.ToDoHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal fun reload() {
        items.clear()
        items = load()
        notifyDataSetChanged()
    }

    internal fun removeItem(target: ToDo) {
        items.remove(target)
        TodoRepository.deleteItem(context, target)
        notifyDataSetChanged()
    }

    private fun load(): ArrayList<ToDo> {
        return TodoRepository.loadItems(context)
    }

    inner class ToDoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        private val title: TextView = itemView.findViewById(R.id.title)
        private val check: CheckBox = itemView.findViewById(R.id.check)
        private val delete: ImageView = itemView.findViewById(R.id.delete)

        fun onBind(toDo: ToDo) {
            title.text = toDo.title
            check.isChecked = toDo.check
            check.setOnCheckedChangeListener { _, isChecked ->
                if (toDo.check != isChecked) {
                    toDo.check = isChecked
                    TodoRepository.updateItem(context, toDo)
                }
            }
            delete.setOnClickListener { removeItem(toDo) }
        }
    }
}
