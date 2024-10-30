package ru.musindev.myapp.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.musindev.myapp.data.WatchLater
import ru.musindev.myapp.databinding.FragmentWatchLaterBinding
import ru.musindev.myapp.utils.AnimationHelper
import ru.musindev.myapp.utils.WatchLaterRepository
import ru.musindev.myapp.view.rv_adapters.WatchLaterAdapter
import java.util.Calendar

class WatchLaterFragment : Fragment() {

    private lateinit var binding: FragmentWatchLaterBinding
    private lateinit var adapter: WatchLaterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchLaterBinding.inflate(inflater, container, false)

        val recyclerView = binding.rcView

        // Настройка адаптера
        adapter = WatchLaterAdapter(WatchLaterRepository.watchLaterList) { watchLater ->
            showEditDateTimeDialog(watchLater)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.watchLaterFragmentRoot, requireActivity(), 3)

    }

    private fun showEditDateTimeDialog(watchLater: WatchLater) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = watchLater.dateTimeInMillis

        // Аналогичный код выбора даты
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        // Создаем новый объект WatchLater с новым временем
                        val newDateTime = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth, hourOfDay, minute, 0)
                        }.timeInMillis

                        // Обновляем список
                        updateWatchLaterReminder(watchLater, newDateTime)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateWatchLaterReminder(watchLater: WatchLater, newDateTime: Long) {
        // Находим индекс и обновляем
        val index = WatchLaterRepository.watchLaterList.indexOf(watchLater)
        if (index != -1) {
            WatchLaterRepository.watchLaterList[index] = watchLater.copy(dateTimeInMillis = newDateTime)
            adapter.notifyItemChanged(index) // Уведомляем адаптер об изменении
        }
    }

}