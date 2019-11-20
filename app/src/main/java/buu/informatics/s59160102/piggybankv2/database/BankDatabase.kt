package buu.informatics.s59160102.piggybankv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Bank::class], version = 1, exportSchema = false)
abstract class BankDatabase : RoomDatabase() {
    abstract val bankDatabaseDao: BankDatabaseDao

    companion object{
        @Volatile
        private  var INSTANCE: BankDatabase? = null

        fun getInstance(context: Context):BankDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BankDatabase::class.java,
                        "bank_list_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return  instance
            }
        }
    }


}