package com.example.imagesearch.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearch.R
import com.example.imagesearch.data.repository.SearchRepositoryImpl
import com.example.imagesearch.databinding.FragmentLikeBinding
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.imagesearch.network.NetworkClient.networkClient
import com.example.imagesearch.presentation.ListItem
import com.example.imagesearch.presentation.adapter.RecyclerAdapter
import com.example.imagesearch.presentation.viewmodel.LikeEvent
import com.example.imagesearch.presentation.viewmodel.LikeViewModel
import com.example.imagesearch.presentation.viewmodel.LikeViewModelFactory
import com.example.imagesearch.presentation.viewmodel.SearchViewModel
import com.example.imagesearch.presentation.viewmodel.SearchViewModelFactory


class LikeFragment : Fragment(), LikeEvent {

    private var _binding : FragmentLikeBinding? = null
    private val binding get() = _binding!!

    private val likeViewModel : LikeViewModel by activityViewModels {
        LikeViewModelFactory(requireContext())
    }
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel(){
        likeViewModel.likes.observe(viewLifecycleOwner){
            recyclerAdapter.submitList(it)
        }
    }

    private fun initView()=with(binding){
        recyclerAdapter = RecyclerAdapter(this@LikeFragment)

        rvLike.apply {
            adapter = recyclerAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
    }


    override fun like(item: ListItem) {
        if (item is ListItem.ImageItem || item is ListItem.VideoItem) {
            likeViewModel.toggleAction(item)
        }
    }
}