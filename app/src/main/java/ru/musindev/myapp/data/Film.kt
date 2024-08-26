package ru.musindev.myapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    var isInFavorites: Boolean = false
) : Parcelable