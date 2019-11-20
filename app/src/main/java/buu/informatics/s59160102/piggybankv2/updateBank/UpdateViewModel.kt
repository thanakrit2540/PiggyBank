package buu.informatics.s59160102.piggybankv2.updateBank

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import buu.informatics.s59160102.piggybankv2.database.Bank
import buu.informatics.s59160102.piggybankv2.database.BankDatabaseDao
import kotlinx.coroutines.*


class UpdateViewModel(val dataSource: BankDatabaseDao,
                           val bankId: Long,
                           application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val database = dataSource

    private val bank: LiveData<Bank>
    fun getBank() = bank

    private val _sum = MutableLiveData<Int>()
    val sum: LiveData<Int> get() = _sum


    init {
        Log.i("UpdateBank", bankId.toString())
        bank = database.getBankWithId(bankId)

        val bankString = Transformations.map(bank) {
            Log.i("Update Bank", it.sum.toString())
        }


    }

    fun onSetValue(sumState: Int) {
        _sum.value = sumState

    }

    fun onUpdate(sumBank: String) {
        uiScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            _sum.value = sumBank.toInt()


            Log.i("Update Bank", sum.value.toString())

            var oldBank: Bank = getBank(bankId)
            oldBank.sum = sum.value ?: 0

            update(oldBank)

        }
    }

    fun onDelete() {
        uiScope.launch {
            delete(bankId)

        }
    }

    private suspend fun delete(bankId: Long) {
        withContext(Dispatchers.IO) {
            database.deleteById(bankId)
        }
    }

    private suspend fun getBank(bankId: Long): Bank {
        return withContext(Dispatchers.IO) {
            database.get(bankId)
        }
    }

    private suspend fun update(bank: Bank) {
        withContext(Dispatchers.IO) {
            database.update(bank)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToListBank = MutableLiveData<Boolean?>()

    val navigateToListBank: LiveData<Boolean?>
        get() = _navigateToListBank

    fun doneNavigating() {
        _navigateToListBank.value = null
    }

    fun onClose() {
        _navigateToListBank.value = true
    }
}
