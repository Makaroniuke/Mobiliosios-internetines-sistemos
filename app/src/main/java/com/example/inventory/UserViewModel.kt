package com.example.inventory

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import com.example.inventory.data.Measurement
import com.example.inventory.data.Strength
import com.example.inventory.data.User
import com.example.inventory.network.Measurements

import com.example.inventory.network.SignalApi
import com.example.inventory.network.Strengths
import com.example.inventory.network.Users
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

class UserViewModel(private val itemDao: ItemDao) : ViewModel() {
//    class UserViewModel() : ViewModel() {
//    val allItems: LiveData<List<User>> = userDao.getUsers().asLiveData()
//    private val allUsers: LiveData<List<User>> = itemDao.getUsers().asLiveData()
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()
    val allMeasurements: LiveData<List<Measurement>> = itemDao.getMeasurements().asLiveData()
    var allStrengths: LiveData<List<Strength>> = itemDao.getStrengths().asLiveData()
    var allUsers: LiveData<List<User>> = itemDao.getUsers().asLiveData()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    var interleavedValues = arrayListOf<Int>()

    private val _users = MutableLiveData<List<Users>>()
    val users: LiveData<List<Users>> = _users

    private val _measurements = MutableLiveData<List<Measurements>>()
    val measurements: LiveData<List<Measurements>> = _measurements

    private val _strengths = MutableLiveData<List<Strengths>>()
    val strengths: LiveData<List<Strengths>> = _strengths




//    @WorkerThread
    fun getData() {
        viewModelScope.launch {
            try{
                _users.value = SignalApi.retrofitService.getUsers()
                _measurements.value = SignalApi.retrofitService.getMeasurements()
                _strengths.value = SignalApi.retrofitService.getStrengths()
            }catch (e: Exception){
                _status.value = e.toString()
            }

            try{
            for (i in 0 until _users.value!!.count()){
                var user = User(
                    mac = _users.value!![i].mac,
                    sensorius = _users.value!![i].sensorius ,
                    stiprumas = _users.value!![i].stiprumas
                )
                itemDao.insert(user)
            }
        }catch (e: Exception){
            _status.value = e.toString()
        }

            try{
                for (i in 0 until _measurements.value!!.count()){

                    var measurement = Measurement(
                        matavimas = _measurements.value!![i].matavimas,
                        x = _measurements.value!![i].x ,
                        y = _measurements.value!![i].y,
                        atstumas = _measurements.value!![i].atstumas,
                    )
                    itemDao.insert(measurement)
                }
            }catch (e: Exception){
                _status.value = e.toString()
            }

            try{
                for (i in 0 until _strengths.value!!.count()){
                    var strength = Strength(
                        matavimas = _strengths.value!![i].matavimas,
                        sensorius = _strengths.value!![i].sensorius ,
                        stiprumas = _strengths.value!![i].stiprumas
                    )
                    itemDao.insert(strength)
                }
            }catch (e: Exception){
                _status.value = e.toString()
            }
        }
    }

    fun addUser() {
        viewModelScope.launch {
            try{
                for (i in 0 until _users.value!!.count()){
                    var user = User(
                        mac = _users.value!![i].mac,
                        sensorius = _users.value!![i].sensorius ,
                        stiprumas = _users.value!![i].stiprumas
                    )
                    itemDao.insert(user)
                }
            }catch (e: Exception){
                _status.value = e.toString()
            }




        }
    }

    fun addMeasurements() {
        viewModelScope.launch {
            try{
                for (i in 0 until _measurements.value!!.count()){

                    var measurement = Measurement(
                        matavimas = _measurements.value!![i].matavimas,
                        x = _measurements.value!![i].x ,
                        y = _measurements.value!![i].y,
                        atstumas = _measurements.value!![i].atstumas,
                    )
                    itemDao.insert(measurement)
                }
            }catch (e: Exception){
                _status.value = e.toString()
            }
        }
    }

    fun addStrengths() {
        viewModelScope.launch {
            try{
                for (i in 0 until _strengths.value!!.count()){
                    var strength = Strength(
                        matavimas = _strengths.value!![i].matavimas,
                        sensorius = _strengths.value!![i].sensorius ,
                        stiprumas = _strengths.value!![i].stiprumas
                    )
                    itemDao.insert(strength)
                }
            }catch (e: Exception){
                _status.value = e.toString()
            }
        }
    }


    fun returnFirst ()
    : String {

        return allUsers.value!![0].sensorius
    }




}


class UserViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
