package com.oyelabs.marvel.universe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.oyelabs.marvel.universe.R
import com.oyelabs.marvel.universe.data.ApiHelper
import com.oyelabs.marvel.universe.data.RetrofitBuilder
import com.oyelabs.marvel.universe.databinding.FragmentDetailsBinding
import com.oyelabs.marvel.universe.model.CharacterDataWrapper
import com.oyelabs.marvel.universe.tools.Resource
import com.oyelabs.marvel.universe.tools.Status
import com.oyelabs.marvel.universe.viewModel.CharacterListViewModel
import com.oyelabs.marvel.universe.viewModel.ViewModelFactory

class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var characterListViewModel: CharacterListViewModel
    private var characterId: Int = 0
    private lateinit var binding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            characterId = DetailsFragmentArgs.fromBundle(it).argumentCharacterId
        }
        characterListViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.retrofitApi))
        )
            .get(CharacterListViewModel::class.java)
        characterListViewModel.getCharacterById(characterId)
            .observe(viewLifecycleOwner, characterListObserver)
    }

    private val characterListObserver = Observer<Resource<CharacterDataWrapper>> {
        it?.let { resource: Resource<CharacterDataWrapper> ->
            when (resource.status) {
                Status.SUCCESS -> {
                    val characterData = resource.data?.data?.results?.firstOrNull()
                    characterData?.let { character ->
                        binding.textCharacterName.text = character.name
                        binding.textCharacterDescription.text = character.description
                        if (character.description.isEmpty())
                            binding.textCharacterDescription.visibility = View.GONE
                        else
                            binding.textCharacterDescription.visibility = View.VISIBLE
                        Glide.with(this)
                            .load("${character.thumbnail.path}.${character.thumbnail.extension}")
                            .into(binding.imageViewCharacter)
                        binding.textCharacterComics.text =
                            getString(R.string.comics, character.comics.items.firstOrNull()?.name)

                        if (character.comics.items.firstOrNull()?.name.isNullOrEmpty())
                            binding.textCharacterComics.visibility = View.GONE
                        else
                            binding.textCharacterComics.visibility = View.VISIBLE
                        binding.textCharacterStories.text =
                            getString(R.string.stories, character.stories.items.firstOrNull()?.name)
                        if (character.stories.items.firstOrNull()?.name.isNullOrEmpty())
                            binding.textCharacterStories.visibility = View.GONE
                        else
                            binding.textCharacterStories.visibility = View.VISIBLE
                        binding.textCharacterEvents.text =
                            getString(R.string.events, character.events.items.firstOrNull()?.name)
                        if (character.events.items.firstOrNull()?.name.isNullOrEmpty())
                            binding.textCharacterEvents.visibility = View.GONE
                        else
                            binding.textCharacterEvents.visibility = View.VISIBLE
                        binding.textCharacterSeries.text =
                            getString(R.string.series, character.series.items.firstOrNull()?.name)
                        if (character.series.items.firstOrNull()?.name.isNullOrEmpty())
                            binding.textCharacterSeries.visibility = View.GONE
                        else
                            binding.textCharacterSeries.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                }
            }
        }
    }
}