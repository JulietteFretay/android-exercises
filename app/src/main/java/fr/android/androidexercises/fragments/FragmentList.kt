package fr.android.androidexercises.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.android.androidexercises.Book
import fr.android.androidexercises.HenriPotierService
import fr.android.androidexercises.R
import fr.android.androidexercises.viewholders.MyBookViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class FragmentList : Fragment(){
    private var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val retrofit = Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(HenriPotierService::class.java)
        var books: List<Book>? = null
        val fragment = this
        val fragmentContext = context
        api.listBooks().enqueue(object: Callback<List<Book>> {
            override fun onFailure(call: Call<List<Book>>, t: Throwable){
                Timber.e("error on download data")
            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>){
                books = response.body()
                val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(fragment.context)
                recyclerView.adapter = MyBookViewHolder(fragmentContext!!, books!!, { book -> onItemPressed(book)} )
            }
        })
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        when (context) {
            is Listener -> listener = context
            else -> throw Exception("...")
        }
    }
    fun onItemPressed(book: Book) {
        Log.i("click", "clickfragment")
        listener?.onBookSelected(book)
    }

    interface Listener {
        fun onBookSelected(book: Book)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}

