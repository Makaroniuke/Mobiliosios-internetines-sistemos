package com.example.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
//import com.example.inventory.network.SignalApi
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(firstSignal: String, secondSignal: String, thirdSignal: String): Item {
        return Item(
            firstSignal = firstSignal.toInt(),
            secondSignal = secondSignal.toInt(),
            thirdSignal = thirdSignal.toInt()
        )
    }

    fun addNewItem(firstSignal: String, secondSignal: String, thirdSignal: String) {
        val newItem = getNewItemEntry(firstSignal, secondSignal, thirdSignal)
        insertItem(newItem)
    }

    fun isEntryValid(firstSignal: String, secondSignal: String, thirdSignal: String): Boolean {
        if (firstSignal.isBlank() || secondSignal.isBlank() || thirdSignal.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        firstSignal: String,
        secondSignal: String,
        thirdSignal: String
    ): Item {
        return Item(
            id = itemId,
            firstSignal = firstSignal.toInt(),
            secondSignal = secondSignal.toInt(),
            thirdSignal = thirdSignal.toInt()
        )
    }



    fun updateItem(
        itemId: Int,
        firstSignal: String,
        secondSignal: String,
        thirdSignal: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, firstSignal, secondSignal, thirdSignal)
        updateItem(updatedItem)
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }



}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
