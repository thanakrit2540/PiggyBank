package buu.informatics.s59160102.piggybankv2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_table")
data class Bank(
    @PrimaryKey(autoGenerate = true)
    var bankId: Long = 0L,

    @ColumnInfo(name = "sum")
    var sum : Int = 0
)

