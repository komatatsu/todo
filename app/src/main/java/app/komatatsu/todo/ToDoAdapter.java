package app.komatatsu.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoHolder> {

    private Context context;
    private ArrayList<ToDo> items;

    ToDoAdapter(Context context) {
        this.context = context;
        this.items = load();
    }


    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToDoHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void reload() {
        items.clear();
        items = load();
        notifyDataSetChanged();
    }

    void removeItem(ToDo target) {
        items.remove(target);
        TodoRepository.deleteItem(context, target);
        notifyDataSetChanged();
    }

    private ArrayList<ToDo> load() {
        return TodoRepository.loadItems(context);
    }

    class ToDoHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private Context context;
        private TextView title;
        private CheckBox check;
        private ImageView delete;
        private ToDo toDo;

        ToDoHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            title = itemView.findViewById(R.id.title);
            check = itemView.findViewById(R.id.check);
            delete = itemView.findViewById(R.id.delete);
            check.setOnCheckedChangeListener(this);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toDo != null) {
                        removeItem(toDo);
                    }
                }
            });
        }

        void onBind(final ToDo toDo) {
            this.toDo = toDo;
            this.title.setText(toDo.getTitle());
            this.check.setChecked(toDo.getCheck());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (toDo != null && toDo.getCheck() != isChecked) {
                toDo.setCheck(isChecked);
                TodoRepository.updateItem(context, toDo);
                notifyDataSetChanged();
            }
        }
    }
}
