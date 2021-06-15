package com.example.technicaltest.models.response

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity
class User {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var firstName: String? = null
    var lastName: String? = null
    @Ignore
    var address: Address? = null
    var addressString: String? = createAddressString()
    var birthDate: Long? = null
    var email: String? = null

    fun getDate(): String? {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(birthDate)
    }

    fun createAddressString(): String{
        return "${address?.streetCode}, ${address?.street} - ${address?.postalCode} ${address?.city} - ${address?.country}"
    }

}