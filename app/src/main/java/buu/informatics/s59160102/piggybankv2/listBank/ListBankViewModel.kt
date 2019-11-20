package buu.informatics.s59160102.piggybankv2.listBank

import android.app.Application
import android.util.Log

import androidx.lifecycle.*
import buu.informatics.s59160102.piggybankv2.database.BankDatabaseDao

import kotlinx.coroutines.*

class ListBankViewModel(val dataSource: BankDatabaseDao,
                        application: Application
) : AndroidViewModel(application)  {

    private var viewModelJob = Job()
    private val _sum = MutableLiveData<Int>()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToInsert = MutableLiveData<Boolean>()
    val navigateToInsert: LiveData<Boolean> get() = _navigateToInsert

    private val _navigateToEdit = MutableLiveData<Long>()
    val navigateToEdit: LiveData<Long> get() = _navigateToEdit

    val database = dataSource

    val banks = database.getAllBank()

    val sum: LiveData<Int>
        get() = _sum



    init {
        Log.i("ListBankViewModel", "Final sum is $_sum")

    }

//    val bankString = Transformations.map(banks) { banks ->
//        formatBank(banks, application.resources)
//    }
    fun onClickAdd(){
        uiScope.launch {
            _navigateToInsert.value = true
        }
    }
//    fun doneNavigating(){
//        _navigateToInsert.value = null
//    }

    fun onClear() {
        uiScope.launch {
            // Clear the database table.



            clear()

        }
    }
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onBankClicked(id: Long) {

        _navigateToEdit.value = id
    }
//    fun onEditNavigated() {
//        _navigateToEdit.value = null
//    }
}