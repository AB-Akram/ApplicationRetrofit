package com.codabee.applicationretrofit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.codabee.applicationretrofit.core.RetrofitInstance
import com.codabee.applicationretrofit.databinding.ActivityMainBinding
import com.codabee.applicationretrofit.model.Albums
import com.codabee.applicationretrofit.network.AlbumService
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) //binding.root is reference to root view. || root view is outermost view container in your layout

        enableEdgeToEdge() /* Jetpack Compose Team Has Introduced The enableEdgeToEdge() to avoid Boilerplate code related to System Bars Like Status Bar, Navigation Bar, etc

                              you need to update to the latest version of implemenration('androidx.activity:activity:1.8.1') to use enableEdgeToEdge features */

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val retrofitService =
            RetrofitInstance.getRetrofitInstance()
                .create(AlbumService::class.java) /* Retrofit automatically generates an implementation of AlbumService that handles network calls based on the annotations you have defined in the interface, such as @GET, @POST, etc.*/

        /*LiveData is good for memory /memory leaks
        * LiveData is a class from the Android Jetpack library used for real-time data management in Android applications.
        * It's particularly useful for cases where you have data that needs to be observed by UI components such as fragments or activities and updated accordingly.
        * Here are some key points about LiveData:
        * Observability: LiveData makes your data observable, meaning that components can be notified when this data changes.
        * Lifecycle-aware: LiveData is aware of the lifecycle of Android components such as activities and fragments, which allows it to automatically manage subscription and unsubscription to updates based on these lifecycles.
        * Ease of use: LiveData simplifies data updates and subscription management, reducing the risk of memory leaks and making reactive coding easier.
        */

        val responseLiveData: LiveData<Response<Albums>> =
            liveData {
                val response = retrofitService.getAlbums()
                emit(response)
            }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    binding.txtId.append(albumItem.title)
                }
            }
        })
    }
}
