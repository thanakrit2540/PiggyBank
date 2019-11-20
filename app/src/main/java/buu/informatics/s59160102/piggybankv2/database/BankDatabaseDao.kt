package buu.informatics.s59160102.piggybankv2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BankDatabaseDao {
    @Insert
    fun insert(bank: Bank)

    @Update
    fun update(bank: Bank)

    @Query("SELECT * FROM bank_table WHERE bankId = :key")
    fun get(key: Long) : Bank

    @Query("SELECT * from bank_table WHERE bankId = :key")
    fun getBankWithId(key: Long): LiveData<Bank>

    @Query("DELETE FROM bank_table")
    fun clear()

    @Query("DELETE FROM bank_table WHERE bankId = :key")
    fun deleteById(key: Long)

    @Query("SELECT * FROM bank_table ORDER BY bankId DESC")
    fun  getAllBank(): LiveData<List<Bank>>
}