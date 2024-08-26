package ru.musindev.myapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.musindev.myapp.R
import ru.musindev.myapp.data.ApiConstants
import ru.musindev.myapp.databinding.FragmentDetailsBinding
import ru.musindev.myapp.data.Film

@Suppress("DEPRECATION")
class DetailsFragment : Fragment() {
    private lateinit var film: Film
    private lateinit var binding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsDetails()

        binding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.favorite)
                film.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.baseline_favorite_border)
                film.isInFavorites = false
            }
        }

        binding.detailsFab.setOnClickListener {
            //Создаем интент
            val intent = Intent()
            //Укзываем action с которым он запускается
            intent.action = Intent.ACTION_SEND
            //Кладем данные о нашем фильме

            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            //УКазываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            //Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }


    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        film = arguments?.get("film") as Film
        //Устанавливаем заголовок
        binding.detailsToolbar.title = film.title
        //Устанавливаем картинку
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        //Устанавливаем описание
        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.baseline_favorite_border
            else R.drawable.favorite
        )
    }


}