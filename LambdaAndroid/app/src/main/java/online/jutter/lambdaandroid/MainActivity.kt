package online.jutter.lambdaandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import online.jutter.lambdaandroid.server.Api
import online.jutter.lambdaandroid.server.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val listAdapter = ListAdapter(this::openCard)

    private val okhttp = OkHttpClient.Builder()
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp)
        .baseUrl("https:jutter.online/LambdaTestApi/")
        .build()
    private val service = ApiService(retrofit.create(Api::class.java))
    private val job = SupervisorJob()
    override val coroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList.adapter = listAdapter
        rvList.layoutManager = GridLayoutManager(baseContext, 2)

        launchUI {
            val list = withIO { service.getList() }
            pbLoading.visibility = View.GONE
            rvList.visibility = View.VISIBLE
            listAdapter.addList(list)
        }
    }

    private fun openCard(item: String, count: Int) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra("count", count)
        intent.putExtra("item", item)
        startActivity(intent)
    }

    fun CoroutineScope.launchUI(callback: suspend () -> Unit) = launch(Dispatchers.Main) { callback() }
    suspend fun <T> withIO(callback: suspend () -> T) = withContext(Dispatchers.IO) { callback() }
}