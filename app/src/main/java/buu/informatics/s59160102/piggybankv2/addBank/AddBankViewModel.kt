package buu.informatics.s59160102.piggybankv2.addBank

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import buu.informatics.s59160102.piggybankv2.database.Bank
import buu.informatics.s59160102.piggybankv2.database.BankDatabaseDao
import kotlinx.coroutines.*

class AddBankViewModel (val database: BankDatabaseDao,
                        application: Application
) : AndroidViewModel(application) {


    private var viewModelJob = Job()

//    private var bank = MutableLiveData<Bank?>()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _sum = MutableLiveData<Int>()
    val sum: LiveData<Int>
        get() = _sum

    private val _eventCreateFinish = MutableLiveData<Boolean>()
    val eventCreateFinish: LiveData<Boolean>
        get() = _eventCreateFinish

    fun onCreateFinish() {
        _eventCreateFinish.value = true
    }
    fun onGameFinishComplete() {
        _eventCreateFinish.value = false
    }

    init {

        Log.i("AddBankViewModel", "AddBankViewModel created!")
        _sum.value = 0

    }
//    fun onSetValue(sumState:Int){
//        _sum.value = sumState
//    }

    fun onCreate(bankSum : Int) {
        uiScope.launch {
            _sum.value = bankSum

            val newBank = Bank()
            newBank.sum = sum.value.toString().toInt()

            insert(newBank)
        }
    }

    private suspend fun insert(bank: Bank){
        withContext(Dispatchers.IO) {
            database.insert(bank)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("AddBankViewModel", "AddBankViewModel destroyed!")
        viewModelJob.cancel()
    }
    fun onClickOne(){
        _sum.value = (sum.value)?.plus(1)
        Log.i("AddBankViewModel", "1 ->"+_sum.value.toString())

    }
    fun onClickTwo(){
        _sum.value = (sum.value)?.plus(2)
        Log.i("AddBankViewModel", "2 ->"+_sum.value.toString())

    }
    fun onClickFive(){
        _sum.value = (sum.value)?.plus(5)
        Log.i("AddBankViewModel", "5 ->"+_sum.value.toString())

    }
    fun onClickTen(){
        _sum.value = (sum.value)?.plus(10)

        Log.i("AddBankViewModel", "10 ->"+_sum.value.toString())

    }





}
