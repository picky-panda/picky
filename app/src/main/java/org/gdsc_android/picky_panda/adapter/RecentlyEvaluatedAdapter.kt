package org.gdsc_android.picky_panda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.gdsc_android.picky_panda.R
import org.gdsc_android.picky_panda.data.ResponseMyStoreListData
import org.gdsc_android.picky_panda.data.ResponseRegisterStoreData

class RecentlyEvaluatedAdapter(private val items: List<ResponseMyStoreListData.Data.RecentlyEvaluatedList>) :
    RecyclerView.Adapter<RecentlyEvaluatedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.placeNameTextView.text = item.placeName
        holder.addressTextView.text = item.address
        holder.optionTextView.text = item.options
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeNameTextView: TextView = view.findViewById(R.id.recentlyEvaluatedRestaurantNameTextView)
        val addressTextView: TextView = view.findViewById(R.id.recentlyEvaluatedAddressTextView)
        val optionTextView: TextView = view.findViewById(R.id.recentlyEvaluatedOptionTextView)
    }
}
