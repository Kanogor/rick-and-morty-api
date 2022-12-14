package ru.kanogor.rickandmortypedia.presentation.recyclerview

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.kanogor.rickandmortypedia.databinding.RickAndMortyItemCharactersBinding
import ru.kanogor.rickandmortypedia.entity.Results

class CharactersAdapter : PagingDataAdapter<Results, CharactersViewHolder>(DiffUtilCallback()) {

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = getItem(position)
        val charName = item?.name
        val charStatus = item?.status
        val charSpecies = item?.species
        val charGender = item?.gender
        val charLocation = item?.location?.name
        with(holder.binding) {
            name.text = "Name: $charName"
            status.text = "Status: $charStatus"
            species.text = "Species: $charSpecies"
            gender.text = "Gender: $charGender"
            location.text = "Location: $charLocation"
            statusColor.backgroundTintList = ColorStateList.valueOf(
                if (charStatus == "Alive") Color.GREEN
                else Color.RED
            )
            item?.let {
                Glide
                    .with(avatar)
                    .load(it.image)
                    .into(avatar)
            }
            root.setOnClickListener {
                // coming soon
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = RickAndMortyItemCharactersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharactersViewHolder(binding)
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Results>() {
    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean =
        oldItem.id == newItem.id
}

class CharactersViewHolder(val binding: RickAndMortyItemCharactersBinding) :
    ViewHolder(binding.root)
