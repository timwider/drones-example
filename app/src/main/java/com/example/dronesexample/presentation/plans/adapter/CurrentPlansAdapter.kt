package com.example.dronesexample.presentation.plans.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dronesexample.R
import com.example.dronesexample.data.models.FlightPlans
import com.example.dronesexample.data.models.FlightPlansRV

class CurrentPlansAdapter(
    private val isForFavorites: Boolean,
    private val ivFavoriteClickListener: (FlightPlansRV) -> Unit
):ListAdapter<FlightPlansRV, CurrentPlansAdapter.ViewHolder>(FlightPlansDiffUtilCallback()) {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.root_card_layout)
        val tvFlightDates: TextView = itemView.findViewById(R.id.tv_flight_dates)
        val tvDroneModel: TextView = itemView.findViewById(R.id.tv_drone_model_fp)
        val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.flight_plan_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.tvFlightDates.text =
            getRes(holder).getString(R.string.flight_dates, currentItem.periodStart, currentItem.periodEnd)
        holder.tvDroneModel.text = currentItem.droneName

        holder.ivFavorite.setOnClickListener {
            ivFavoriteClickListener(currentItem)
            if (isForFavorites) notifyItemRemoved(position) else notifyItemChanged(position)
        }
        val resId =
            if (currentItem.isFavorite == true) R.drawable.ic_is_favorite else R.drawable.ic_no_favorite

        setDrawable(resId, holder.ivFavorite)
    }

    private fun setDrawable(drawableId: Int, into: ImageView) {
        val drawable = ResourcesCompat.getDrawable(into.context.resources, drawableId, null)
        into.setImageDrawable(drawable)
    }

    private fun getRes(holder: ViewHolder) = holder.card.context.resources

    class FlightPlansDiffUtilCallback: DiffUtil.ItemCallback<FlightPlansRV>() {
        override fun areItemsTheSame(oldItem: FlightPlansRV, newItem: FlightPlansRV): Boolean =
            oldItem.detailsId == newItem.detailsId

        override fun areContentsTheSame(oldItem: FlightPlansRV, newItem: FlightPlansRV): Boolean =
            oldItem.detailsId == newItem.detailsId
    }
}