package com.example.technicaltest.models.devices

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Heater constructor(var mode: String?, var temperature: Double?, id: Int?,
                         deviceName: String?, productType: String?
): Device(id, deviceName, productType) {

    @PrimaryKey(autoGenerate = true)
    var idHeater: Int = 0

}