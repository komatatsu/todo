package app.komatatsu.todo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQ_ADD = 1
    }

    lateinit var adapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.adapter = ToDoAdapter(this)
        val list: RecyclerView = findViewById(R.id.list)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val add: FloatingActionButton = findViewById(R.id.add)
        add.setOnClickListener({
            startActivityForResult(AddActivity.getIntent(this), REQ_ADD)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_ADD) {
            adapter.reload()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

