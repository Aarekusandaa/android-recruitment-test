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
    var id: Int? = -1
    lateinit var photo: ImageView
    lateinit var photo_title: TextView
    lateinit var album_title: TextView
    lateinit var username: TextView
    lateinit var email: TextView
    lateinit var phone: TextView


    override fun onAttach(context: Context) {
        super.onAttach(context)

        id = arguments?.getInt("id")
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

        val db = Room.databaseBuilder(
            this@DetailsFragment,
            AppDatabase::class.java, "users_db"
        ).build()

        val listDao = db.listDao()
        val detailsDao = db.detailsDao()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.detail.value = viewModel.getDetails(detailsDao, id)
            }
        }

        viewModel.detail.observe(this, { detail ->
            tv_photo_title.setText(detail.photoTitle)
            tv_album_title.setText(detail.albumTitle)
            tv_username.setText(detail.username)
            tv_email.setText(detail.email)
            tv_phone.setText(detail.phone)
            Glide.with(iv_photo)
                .load(detail.url)
                .circleCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .fallback(R.drawable.ic_placeholder)
                .into(iv_photo)
        })
    }
}