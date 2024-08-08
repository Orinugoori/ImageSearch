package com.example.imagesearch.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.example.imagesearch.data.repository.SearchRepositoryImpl
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.imagesearch.network.NetworkClient.networkClient
import com.example.imagesearch.presentation.ListItem
import com.example.imagesearch.presentation.adapter.RecyclerAdapter
import com.example.imagesearch.presentation.viewmodel.LikeEvent
import com.example.imagesearch.presentation.viewmodel.LikeViewModel
import com.example.imagesearch.presentation.viewmodel.LikeViewModelFactory
import com.example.imagesearch.presentation.viewmodel.SearchViewModel
import com.example.imagesearch.presentation.viewmodel.SearchViewModelFactory


class SearchFragment : Fragment(), LikeEvent {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels<SearchViewModel>{
        SearchViewModelFactory(
            SearchRepositoryImpl(
                networkClient
            )
        )
    }

    private val likeViewModel : LikeViewModel by activityViewModels {
        LikeViewModelFactory(requireContext())
    }

    private lateinit var recyclerAdapter: RecyclerAdapter

    private val result = mutableListOf<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initViewModel()
        loadLastKeyword()
        initScrollListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViewModel() {
        searchViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            val likedItems = likeViewModel.likes.value.orEmpty()
            val updatedResults = results.map { item ->
                when (item) {
                    is ListItem.ImageItem -> item.copy(isLiked = likedItems.any { it.uid == item.uid })
                    is ListItem.VideoItem -> item.copy(isLiked = likedItems.any { it.uid == item.uid })
                    else -> item
                }
            }
            recyclerAdapter.submitList(updatedResults)
        }

        likeViewModel.likes.observe(viewLifecycleOwner) { likes ->
            recyclerAdapter.submitList(recyclerAdapter.currentList.map { item ->
                when (item) {
                    is ListItem.ImageItem -> item.copy(isLiked = likes.any { it.uid == item.uid })
                    is ListItem.VideoItem -> item.copy(isLiked = likes.any { it.uid == item.uid })
                    else -> item
                }
            })
        }
    }

    private fun initView() = with(binding) {


        recyclerAdapter = RecyclerAdapter(this@SearchFragment).apply {
            submitList(listOf())
        }

        btnSearch.setOnClickListener {
            val keyword = etSearch.text.toString()

            if(keyword.isNotEmpty()){
                searchViewModel.searchImages(keyword)
                saveLastKeyword(keyword)
                hideKeyboard()
            }
        }

        rvSearchTab.apply {
            adapter = recyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        fabSearch.setOnClickListener {
            rvSearchTab.smoothScrollToPosition(0)
        }
    }

    private fun initScrollListener() = with(binding) {
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 400 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 400 }
        var isTop = true

        rvSearchTab.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!rvSearchTab.canScrollVertically(-1)&&newState == RecyclerView.SCROLL_STATE_IDLE){
                    fabSearch.startAnimation(fadeOut)
                    fabSearch.visibility = View.GONE
                    isTop = true
                }else if(isTop){
                    fabSearch.visibility = View.VISIBLE
                    fabSearch.startAnimation(fadeIn)
                    isTop= false
                }
            }
        })
    }



    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun saveLastKeyword(keyword : String){
        val sharedPreference = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPreference.edit()){
            putString("last_keyword", keyword)
            apply()
        }
    }

    private fun loadLastKeyword(){
        val sharedPreference = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val lastKeyword = sharedPreference.getString("last_keyword", "")
        binding.etSearch.setText(lastKeyword)
    }

    override fun like(item: ListItem) {
        if(item is ListItem.ImageItem|| item is ListItem.VideoItem){
            likeViewModel.toggleAction(item)
        }
    }
}


