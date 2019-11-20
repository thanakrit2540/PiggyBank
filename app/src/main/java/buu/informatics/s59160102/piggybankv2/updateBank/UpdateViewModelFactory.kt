package buu.informatics.s59160102.piggybankv2.updateBank



import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import buu.informatics.s59160102.piggybankv2.database.BankDatabaseDao
import kotlinx.coroutines.*

class UpdateViewModelFactory(
    private val dataSource: BankDatabaseDao,
    private val sumId: Long,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateViewModel::class.java)) {
            return UpdateViewModel(dataSource,sumId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
