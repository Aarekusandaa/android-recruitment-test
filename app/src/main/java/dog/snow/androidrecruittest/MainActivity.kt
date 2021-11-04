package dog.snow.androidrecruittest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import androidx.fragment.app.add
import dog.snow.androidrecruittest.ui.DetailsFragment

class MainActivity : AppCompatActivity(R.layout.main_activity){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))

        val fragmentManager: FragmentManager = supportFragmentManager
        val listFragment = ListFragment()

        fragmentManager.beginTransaction().add(R.id.container, listFragment)
    }
}