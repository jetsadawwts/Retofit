package com.jetsada.Retofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jetsada.Retofit.adapter.MyAdapter
import com.jetsada.Retofit.model.Post
import com.jetsada.Retofit.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { MyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost("11112222")
//        observeViewModel()
        setupRecyclerView()

//        val myPost = Post(2,2,"Jetsada-Wongwit","Android Developer Programing")
//        viewModel.pushPost(myPost)
//        viewModel.pushPost2(2,2,"Jetsada-Wongwit","Android Developer Programing")
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful) {
                Log.d("Success Post Data", response.body().toString())
                Log.d("Success Post Code", response.code().toString())
                Log.d("Success Post Message", response.headers().toString())
            }
            else {
                Log.e("Error Post Code", response.errorBody().toString())
            }
        })
        button.setOnClickListener {
//            val myNumber = number_editText.text.toString()
//            viewModel.getPost2(Integer.parseInt(myNumber))
//            viewModel.myResponse2.observe(this, Observer { response ->
//                if (response.isSuccessful) {
//                    textView.text = response.body().toString()
//                }
//                else {
//                    textView.text = response.code().toString()
//                }
//            })

//            val myNumber = number_editText.text.toString()
//            viewModel.getCustomPosts(Integer.parseInt(myNumber), "id", "asc")
//            viewModel.myResponseCustomPosts .observe(this, Observer { response ->
//                if (response.isSuccessful) {
//                    Log.d("ResponseNumber", response.body()?.size.toString())
//                    response.body()?.forEach { it ->
//                        Log.d("Response", it.userId.toString())
//                        Log.d("Response", it.id.toString())
//                        Log.d("Response", it.title)
//                        Log.d("Response", it.body)
//                        Log.d("Response", "----------------------------------------")
//                    }
//                }
//                else {
//                    textView.text = response.code().toString()
//                }
//            })

//            val options: HashMap<String, String> = HashMap()
//            options["_sort"] = "id"
//            options["_order"] = "desc"
//
//            val myNumber = number_editText.text.toString()
//            viewModel.getCustomPosts2(Integer.parseInt(myNumber), options)
//            viewModel.myResponseCustomPosts2 .observe(this, Observer { response ->
//                if (response.isSuccessful) {
//                    Log.d("ResponseNumber", response.body()?.size.toString())
//                    response.body()?.forEach { it ->
//                        Log.d("Response", it.userId.toString())
//                        Log.d("Response", it.id.toString())
//                        Log.d("Response", it.title)
//                        Log.d("Response", it.body)
//                        Log.d("Response", "----------------------------------------")
//                    }
//                }
//                else {
//                    textView.text = response.code().toString()
//                }
//            })

            val myNumber = number_editText.text.toString()
            viewModel.getCustomPosts(Integer.parseInt(myNumber),"id","desc")
            viewModel.myResponseCustomPosts.observe(this, Observer { response ->
                if(response.isSuccessful){
                    if(response.body().isNullOrEmpty()) {
                        Toast.makeText(this, "DataNotFound!", Toast.LENGTH_SHORT).show()
                    } else {
                        response.body()?.let { it -> myAdapter.setData(it) }
                    }
                } else {
                    Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setupRecyclerView() {
       recyclerview.adapter = myAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.myResponse.observe(this, Observer {response ->
            if(response.isSuccessful) {
                Log.d("Response", response.body()?.userId.toString())
                Log.d("Response", response.body()?.id.toString())
                Log.d("Response", response.body()?.title.toString())
                Log.d("Response", response.body()?.body.toString())
//                textView.text = response.body()?.title.toString()
            } else {
                Log.e("ResponseError", response.errorBody().toString())
//                textView.text = response.errorBody().toString()
            }
        })

    }
}