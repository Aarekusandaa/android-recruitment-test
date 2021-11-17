package dog.snow.androidrecruittest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import androidx.fragment.app.add
import androidx.lifecycle.MutableLiveData
import dog.snow.androidrecruittest.ui.DetailsFragment
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.layout_search.view.*

class MainActivity : AppCompatActivity(R.layout.main_activity){

    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))


        fragmentManager = supportFragmentManager
        val listFragment = ListFragment()

        fragmentManager.beginTransaction().add(R.id.container, listFragment)

    }
}