package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*
import androidx.fragment.app.ListFragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.ui.adapter.ListAdapter
import dog.snow.androidrecruittest.ui.viewmodels.ListViewModel
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.layout_search.view.*
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment(R.layout.list_fragment) {

    private val viewModel:ListViewModel by viewModels()
    private lateinit var adapter1: ListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_items)

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "users_db"
        ).build()

        val listDao = db.listDao()

        adapter1 = ListAdapter(onClick = {listItem, position, view ->

            val detailsFragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putInt("id", listItem.id)
            detailsFragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, detailsFragment)
                .addToBackStack(null)
                .commit()
        })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapter1
        }

        viewModel.list.observe(viewLifecycleOwner, { list ->
            adapter1.setList(list)
            if (list.isNotEmpty()){
                empty.visibility = View.INVISIBLE
            }else{
                empty.visibility = View.VISIBLE
            }
        })

        viewModel.getList(listDao)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != ""){
                    viewModel.getSearchList(listDao, s.toString())
                }else
                    viewModel.getList(listDao)
            }
        })

        /*val search = MutableLiveData(et_search.text)
        search.observe(viewLifecycleOwner, { text ->
            if (text.toString() != ""){
                viewModel.getSearchList(listDao, text.toString())
            }else{
                viewModel.getList(listDao)
            }
        })*/
    }
}

/*
viewModel.list.observe(viewLifecycleOwner, { list ->
    rv_items.apply {
        layoutManager = LinearLayoutManager(activity)

        adapter = ListAdapter(onClick = {listItem, position, view ->

            val listFragment = ListFragment()
            val bundle = Bundle()
            bundle.putInt("id", listItem.id)
            listFragment.arguments = bundle
            val fragment = parentFragmentManager.beginTransaction()
            fragment.replace(R.id.container, listFragment)
            fragment.commit()
        })
        adapter.setList(list)
    }

})*/