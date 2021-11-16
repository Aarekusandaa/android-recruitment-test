package dog.snow.androidrecruittest.ui

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.text.LoginFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.repos.PhotoRepo
import dog.snow.androidrecruittest.ui.viewmodels.DetailViewModel
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsFragment : Fragment(R.layout.details_fragment){

    private val viewModel: DetailViewModel by viewModels()
    //var ids: Int = 0
    /*lateinit var photo: ImageView
    lateinit var photo_title: TextView
    lateinit var album_title: TextView
    lateinit var username: TextView
    lateinit var email: TextView
    lateinit var phone: TextView*/


    override fun onAttach(context: Context) {
        super.onAttach(context)

        //val bundle = Bundle()
        //ids  = bundle.getInt("id", -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.details_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var ids = -1
        //val bundle = Bundle()
        if (arguments != null){
            ids  = arguments?.getInt("id")!!
        }

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "users_db"
        ).build()

        val detailsDao = db.detailsDao()

        viewModel.detail.observe(viewLifecycleOwner, { detail ->
            tv_photo_title.setText(detail.photoTitle)
            tv_album_title.setText(detail.albumTitle)
            tv_username.setText(detail.username)
            tv_email.setText(detail.email)
            tv_phone.setText(detail.phone)
            val url = GlideUrl(
                detail.url, LazyHeaders.Builder()
                    .addHeader("User-Agent", "Lejdi")
                    .build()
            )
            Glide.with(iv_photo)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .fallback(R.drawable.ic_placeholder)
                .into(iv_photo)
        })


        viewModel.getDetails(detailsDao, ids)

    }

}