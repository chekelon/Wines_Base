package com.cursosant.winesbase.homeModule.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cursosant.winesbase.R
import com.cursosant.winesbase.commonModule.OnClickListener
import com.cursosant.winesbase.commonModule.dataAccess.retrofit.WineService
import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.commonModule.utils.Constants
import com.cursosant.winesbase.commonModule.view.WineBaseFragment
import com.cursosant.winesbase.homeModule.HomeViewModel
import com.cursosant.winesbase.homeModule.HomeViewModelFactory
import com.cursosant.winesbase.homeModule.intent.HomeIntent
import com.cursosant.winesbase.homeModule.model.HomeRepository
import com.cursosant.winesbase.homeModule.model.HomeState
import com.cursosant.winesbase.homeModule.model.RoomDatabase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
class HomeFragment : WineBaseFragment(), OnClickListener {

    private lateinit var adapter: WineListAdapter
    private lateinit var vm: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
        setupRecyclerView()
        setupSwipeRefresh()
        setupObservers()

    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this,
            HomeViewModelFactory(
                HomeRepository(RoomDatabase(),setupRetrofit())))[HomeViewModel::class.java]
    }

    private fun setupAdapter() {
        adapter = WineListAdapter()
        adapter.setOnClickListener(this)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
            adapter = this@HomeFragment.adapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.srlResults.setOnRefreshListener {
            getWines()
        }
    }

    private fun getWines() {
        lifecycleScope.launch(Dispatchers.IO) {

            vm.channel.send(HomeIntent.RequestWines)
        }
    }

    private fun setupRetrofit(): WineService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WineService::class.java)
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            vm.state.collect { state ->
                when(state){

                    is HomeState.Init -> {}
                    is HomeState.ShowProgress -> showProgress(true)
                    is HomeState.HideProgress -> showProgress(false)
                    is HomeState.RequestWinesSuccess -> {
                        adapter.submitList(state.list)
                        showNoDataView(state.list.isEmpty())
                        showRecyclerView(state.list.isNotEmpty())
                    }
                    is HomeState.AddWineSuccess -> showMsg(state.msgRes)
                    is HomeState.Fail -> handleError(state.code,state.msgRes)
                }



            }
        }
    }

    private fun handleError(code:Int, msgRes:Int){
        if(code == Constants.EC_REQUEST_NO_WINES){
            showNoDataView(true)
            showRecyclerView(false)
        }else{
            showMsg(msgRes)
        }
    }

    private fun showMsg(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    private fun showNoDataView(isVisible: Boolean) {
        binding.tvNoData.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showProgress(isVisible: Boolean) {
        binding.srlResults.isRefreshing = isVisible
    }

    private fun showRecyclerView(isVisible: Boolean) {
        binding.recyclerView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        getWines()
    }

    /*
    * OnClickListener
    * */
    override fun onFavorite(wine: Wine) {
        return
    }

    override fun onLongClick(wine: Wine) {
        val items = resources.getStringArray(R.array.array_home_options)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.home_dialog_title)
            .setItems(items) { _, index ->
                when(index) {
                    0 -> addToFavourites(wine)
                }
            }.show()
    }

    private fun addToFavourites(wine: Wine) {
        lifecycleScope.launch(Dispatchers.IO) {
            wine.isFavorite = true
            vm.channel.send(HomeIntent.AddWine(wine))
        }
    }
}