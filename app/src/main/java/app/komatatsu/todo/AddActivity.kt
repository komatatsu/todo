package app.komatatsu.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class AddActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, AddActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val title = findViewById<EditText>(R.id.edit)
        val add = findViewById<Button>(R.id.add)
        add.setOnClickListener {
            val todo = ToDo(
                    System.currentTimeMillis(),
                    title.text.toString(),
                    false
            )
            TodoRepository.addItem(this@AddActivity, todo)
            finish()
        }
    }
}
