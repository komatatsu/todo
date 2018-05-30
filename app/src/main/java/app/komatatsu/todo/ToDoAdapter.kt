package app.komatatsu.todo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ToDoAdapter(private val context: Context) : RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {

    private var items: ArrayList<ToDo> = load()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        return ToDoHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false))
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoHolder, position: Int) {
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

    inner class ToDoHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), CompoundButton.OnCheckedChangeListener {
        private val context: Context = itemView.context
        private val title: TextView = itemView.findViewById(R.id.title)
        private val check: CheckBox = itemView.findViewById(R.id.check)
        private val delete: ImageView = itemView.findViewById(R.id.delete)
        private lateinit var toDo: ToDo

        init {
            check.setOnCheckedChangeListener(this)
            delete.setOnClickListener({ removeItem(toDo) })
        }

        internal fun onBind(toDo: ToDo) {
            this.toDo = toDo
            title.text = toDo.title
            check.isChecked = toDo.check
        }

        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            if (toDo.check != isChecked) {
                toDo.check = isChecked
                TodoRepository.updateItem(context, toDo)
                notifyDataSetChanged()
            }
        }
    }
}
