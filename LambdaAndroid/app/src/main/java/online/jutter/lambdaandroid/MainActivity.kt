package online.jutter.lambdaandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val listAdapter = ListAdapter(this::openCard, this::onAdd)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList.adapter = listAdapter
        rvList.layoutManager = GridLayoutManager(baseContext, 2)
    }

    private fun openCard(count: Int) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra("count", count)
        startActivity(intent)
    }

    private fun onAdd() {
        rvList.layoutManager?.scrollToPosition(listAdapter.itemCount - 1)
    }
}