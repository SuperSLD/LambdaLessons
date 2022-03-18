package online.jutter.lambdaandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_card.*

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        val count = intent.getIntExtra("count", 0)
        val item = intent.getStringExtra("item")

        tvItem.text = item
        tvDescription.text = "Карточка №$count"

    }
}