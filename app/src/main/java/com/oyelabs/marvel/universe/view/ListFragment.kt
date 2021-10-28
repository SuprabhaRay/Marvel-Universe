package com.oyelabs.marvel.universe.view
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oyelabs.marvel.universe.data.ApiHelper
import com.oyelabs.marvel.universe.data.RetrofitBuilder
import com.oyelabs.marvel.universe.databinding.FragmentListBinding
import com.oyelabs.marvel.universe.model.CharacterDataWrapper
import com.oyelabs.marvel.universe.tools.EndlessRecyclerViewScrollListener
import com.oyelabs.marvel.universe.tools.Resource
import com.oyelabs.marvel.universe.tools.Status
import com.oyelabs.marvel.universe.viewModel.CharacterListViewModel
import com.oyelabs.marvel.universe.viewModel.ViewModelFactory
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewModelCharacterList: CharacterListViewModel
    private lateinit var listAdapter: CharacterListAdapter
    private lateinit var binding: FragmentListBinding
    private var characterListObserver: Observer<Resource<CharacterDataWrapper>>? = null
    private var isSearching = false
    private var nameStartsWith = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelCharacterList = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.retrofitApi))
        )
            .get(CharacterListViewModel::class.java)
        listAdapter = CharacterListAdapter(arrayListOf())
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (layoutManager).orientation
            )
        )
        binding.recyclerView.adapter = listAdapter
        binding.recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (isSearching) {
                    viewModelCharacterList.getCharacterByName(nameStartsWith, listAdapter.itemCount)
                        .observe(viewLifecycleOwner, characterListObserver!!)
                } else {
                    viewModelCharacterList.getCharacterList(listAdapter.itemCount)
                        .observe(viewLifecycleOwner, characterListObserver!!)
                }
            }
        })
        characterListObserver = Observer<Resource<CharacterDataWrapper>> {
            it?.let {
                    resource: Resource<CharacterDataWrapper> ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingProgressBar.visibility = View.GONE
                        resource.data?.data?.results?.let { users ->
                            listAdapter.addData(users)
                        }
                    }
                    Status.ERROR -> {
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.loadingProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
        viewModelCharacterList.getCharacterList(listAdapter.itemCount)
            .observe(viewLifecycleOwner, characterListObserver!!)
        binding.editTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(nameStartsWith: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (nameStartsWith != null) {
                    if (nameStartsWith.isNotBlank()) {
                            isSearching = true
                            listAdapter.clearList()
                        this@ListFragment.nameStartsWith = nameStartsWith.toString()
                        viewModelCharacterList.getCharacterByName(
                            nameStartsWith.toString(),
                            listAdapter.itemCount
                        )
                            .observe(viewLifecycleOwner, characterListObserver!!)
                    } else {
                        isSearching = false
                        listAdapter.clearList()
                        viewModelCharacterList.getCharacterList(listAdapter.itemCount)
                            .observe(viewLifecycleOwner, characterListObserver!!)
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}