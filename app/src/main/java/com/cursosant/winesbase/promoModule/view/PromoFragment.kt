package com.cursosant.winesbase.promoModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.winesbase.databinding.FragmentPromoBinding
import com.cursosant.winesbase.promoModule.PromoViewModel
import com.cursosant.winesbase.promoModule.PromoViewModelFactory
import com.cursosant.winesbase.promoModule.intent.PromoIntent
import com.cursosant.winesbase.promoModule.model.Database
import com.cursosant.winesbase.promoModule.model.PromoRepository
import com.cursosant.winesbase.promoModule.model.PromoState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


/****
 * Project: Wines
 * From: com.cursosant.wines
 * Created by Alain Nicolás Tello on 06/02/24 at 20:23
 * All rights reserved 2024.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class PromoFragment : Fragment() {
    private var _binding: FragmentPromoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PromoListAdapter

    private lateinit var vm : PromoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
            lifecycleScope.launch {
                vm.state.collect { state ->
                    when(state){
                        is PromoState.Init -> getPromos()
                        is PromoState.RequestPromoSuccess -> adapter.submitList(state.list)
                        is PromoState.Fail -> showMsg(state.msgRes)
                    }
                }
            }
    }

    private  fun setupViewModel(){
        vm = ViewModelProvider(
            this, PromoViewModelFactory(PromoRepository(Database()))
        )[PromoViewModel::class.java]
    }

    private fun setupAdapter() {
        adapter = PromoListAdapter()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@PromoFragment.adapter
        }
    }

    private fun showMsg(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    private fun getPromos() {
        lifecycleScope.launch {
            vm.channel.send(PromoIntent.RequestPromos)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}