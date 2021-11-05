package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.*
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.ui.adapter.ListAdapter
import dog.snow.androidrecruittest.ui.viewmodels.ListViewModel
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment(R.layout.list_fragment) {

    private val viewModel:ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        }
    }
}