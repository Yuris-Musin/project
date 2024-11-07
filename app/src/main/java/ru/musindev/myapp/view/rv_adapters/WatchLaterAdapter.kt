package ru.musindev.myapp.view.rv_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.musindev.myapp.R
import ru.musindev.myapp.data.WatchLater
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WatchLaterAdapter(
    private val watchLaterList: List<WatchLater>,
    private val onEditClick: (WatchLater) -> Unit
) : RecyclerView.Adapter<WatchLaterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Найдите элементы для отображения данных
        val filmTitle: TextView = itemView.findViewById(R.id.film_title)
        val reminderTime: TextView = itemView.findViewById(R.id.reminder_time)
        val button: Button = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val watchLater = watchLaterList[position]
        holder.filmTitle.text = watchLater.film.title
        holder.reminderTime.text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(
            Date(watchLater.dateTimeInMillis)
        )

        holder.button.setOnClickListener {
            onEditClick(watchLater)
        }
    }

    override fun getItemCount(): Int = watchLaterList.size
}
