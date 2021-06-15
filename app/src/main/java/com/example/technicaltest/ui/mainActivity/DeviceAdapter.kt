package com.example.technicaltest.ui.mainActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.R
import com.example.technicaltest.models.devices.Device
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.models.devices.Light
import com.example.technicaltest.models.devices.RollerShutter
import com.example.technicaltest.utils.Constants.HEATER
import com.example.technicaltest.utils.Constants.LIGHT
import com.example.technicaltest.utils.Constants.ROLLER_SHUTTER

class DeviceAdapter(private val adapterCallback: AdapterCallback): RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    private var listDevice = mutableListOf<Device>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textDevice.text = listDevice[position].deviceName

        holder.itemView.setOnLongClickListener {
            when ( listDevice[position].productType) {
                HEATER -> adapterCallback.deleteDevice(listDevice[position] as Heater)
                LIGHT -> adapterCallback.deleteDevice(listDevice[position] as Light)
                ROLLER_SHUTTER -> adapterCallback.deleteDevice(listDevice[position] as RollerShutter)
            }
            true
        }

        holder.itemView.setOnClickListener {
            adapterCallback.openPageDevice(listDevice[position])
        }
    }

    override fun getItemCount(): Int {
        return listDevice.size
    }

    fun addList(receivedList: MutableList<Device>) {
        this.listDevice.clear()
        this.listDevice.addAll(receivedList)
        notifyDataSetChanged()
    }

    interface AdapterCallback {
        fun deleteDevice(device: Device)
        fun openPageDevice(device: Device)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textDevice: TextView = itemView.findViewById(R.id.textNameDevice)
    }
}