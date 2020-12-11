package br.com.casalmoney.app.authenticated.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.casalmoney.app.authenticated.domain.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val explanation: String,
    val amount: Double,
    val date: String,
    val location: String? = null
)

class LocationTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToLocation(string: String?) : Location? {
        if (string == null) {
            return null
        }
        val location = object : TypeToken<Location>() {
        }.type
        return gson.fromJson(string, location)
    }

    @TypeConverter
    fun locationToString(location: Location?): String? {
        return gson.toJson(location)
    }
}