package com.oyelabs.marvel.universe.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oyelabs.marvel.universe.databinding.ItemCharacterBinding
import com.oyelabs.marvel.universe.model.Character

class CharacterListAdapter(
    private val characterList: ArrayList<Character>
) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount() = characterList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(characters: ArrayList<Character>) {
        characterList.addAll(characters)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearList() {
        characterList.clear()
        notifyDataSetChanged()
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            val imageUrl = character.thumbnail.path + "." + character.thumbnail.extension
            binding.textCharacterListName.text = character.name
            Glide.with(binding.imageViewCharacterList.context).load(imageUrl)
                .into(binding.imageViewCharacterList)
            binding.textCharacterListDescription.text = character.description
            if (character.description.isEmpty())
                binding.textCharacterListDescription.visibility = View.GONE
            else
                binding.textCharacterListDescription.visibility = View.VISIBLE
            binding.layoutItemCharacter.setOnClickListener {
                val action =
                    ListFragmentDirections.navToDetails(character.id)
                itemView.findNavController().navigate(action)
            }
        }
    }
}