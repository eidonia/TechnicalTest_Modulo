package com.example.technicaltest.models.devices

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Light constructor(var intensity: Int?, var mode: String?, id: Int?,
                        deviceName: String?, productType: String?
): Device(id, deviceName, productType){

    @PrimaryKey(autoGenerate = true)
    var idLight: Int = 0

}
