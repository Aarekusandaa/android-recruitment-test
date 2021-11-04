package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.ui.viewmodels.DetailViewModel
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel

class DetailsFragment : Fragment(R.layout.details_fragment){

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container,false)



    }
}