package buu.informatics.s59160102.piggybankv2.listBank

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import buu.informatics.s59160102.piggybankv2.database.BankDatabaseDao

class ListBankViewModelFactory(private val dataSource: BankDatabaseDao,
                               private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListBankViewModel::class.java)) {
            return ListBankViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}