package com.example.technicaltest.models.devices

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RollerShutter constructor(var position: Int?, id: Int?, deviceName: String?,
                                productType: String?
): Device(id,
    deviceName, productType
) {

    @PrimaryKey(autoGenerate = true)
    var idRollerShutter: Int = 0
}