package com.example.dronesexample.presentation.home.recyclerview_drones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dronesexample.R
import com.example.dronesexample.data.models.Drone

class DronesAdapter(
    private val longClickListener: (Drone) -> Unit
): ListAdapter<Drone, DronesAdapter.ViewHolder>(DronesDiffUtilCallback()) {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rootLayout: LinearLayout = itemView.findViewById(R.id.drone_root)
        val tvDroneModel: TextView = itemView.findViewById(R.id.tv_drone_model)
        val tvSerialNumber: TextView = itemView.findViewById(R.id.tv_serial_number)
        val tvWeight: TextView = itemView.findViewById(R.id.tv_weight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.drone_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.tvDroneModel.text = getRes(holder).getString(R.string.drone_model, currentItem.model)
        holder.tvSerialNumber.text = getRes(holder).getString(R.string.serial_number, currentItem.serial)
        holder.tvWeight.text = getRes(holder).getString(R.string.weight, currentItem.weight)
        holder.rootLayout.setOnLongClickListener {
            longClickListener(currentItem)
            notifyItemRemoved(position)
            true
        }
    }

    private fun getRes(holder: ViewHolder) = holder.rootLayout.context.resources

    class DronesDiffUtilCallback: DiffUtil.ItemCallback<Drone>() {
        override fun areItemsTheSame(oldItem: Drone, newItem: Drone): Boolean =
            oldItem.serial == newItem.serial
        override fun areContentsTheSame(oldItem: Drone, newItem: Drone): Boolean =
            oldItem.serial == newItem.serial
    }
}