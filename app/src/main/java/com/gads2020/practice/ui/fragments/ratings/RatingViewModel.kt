package com.gads2020.practice.ui.fragments.ratings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gads2020.practice.models.Learner
import com.gads2020.practice.network.RetrofitClient
import com.gads2020.practice.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingViewModel : ViewModel() {

    private var _index : Int = 0
//    val index: LiveData<Int> = _index

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _learningLeaders = MutableLiveData<List<Learner>>().apply { value = ArrayList() }
    private val learningLeaders: LiveData<List<Learner>> = _learningLeaders

    private val _skillIQLeaders = MutableLiveData<List<Learner>>().apply { value = ArrayList() }
    private val skillIQLeaders: LiveData<List<Learner>> = _skillIQLeaders

    private var retrofitClient = RetrofitClient.getInstance()

    fun learners(): LiveData<List<Learner>> =
        if (_index == 0) learningLeaders else skillIQLeaders

    fun size(): Int {
        val totalType1: Int = learningLeaders.value?.let { return@let it.size }!!
        val totalType2: Int = skillIQLeaders.value?.let { return@let it.size }!!
       // val index: Int = index.value!!

        return if (_index == 0) totalType1 else totalType2
    }

    fun fetchTopByHours() {
        Utils.showLog("RatingViewModel fetchTopByHours() entered")
        _index = 0
        toggleLoading(true)
        retrofitClient.fetchByHours().enqueue(object : Callback<List<Learner>>{
            override fun onResponse(call: Call<List<Learner>>, response: Response<List<Learner>>) {
                if(response.isSuccessful){
                   // Utils.showLog(response.toString())
                    response.body()?.let { list ->
                        _learningLeaders.value = list.sortedByDescending { it.hours }
                    }
                }
                toggleLoading(false)
            }

            override fun onFailure(call: Call<List<Learner>>, t: Throwable) {
                toggleLoading(false)
            }

        })


    }

    fun fetchTopSkill(){
        _index = 1
        toggleLoading(true)
        retrofitClient.fetchBySkillIQ().enqueue(object : Callback<List<Learner>>{
            override fun onResponse(call: Call<List<Learner>>, response: Response<List<Learner>>) {
                if(response.isSuccessful){
                    response.body()?.let { list ->
                        _skillIQLeaders.value = list.sortedByDescending { it.score }.take(20)
                    }
                }
                toggleLoading(false)
            }

            override fun onFailure(call: Call<List<Learner>>, t: Throwable) {
                toggleLoading(false)
            }

        })
    }

    private fun toggleLoading(value: Boolean) {
        _loading.value = value
    }
}