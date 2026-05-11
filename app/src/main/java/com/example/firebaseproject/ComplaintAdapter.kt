package com.example.firebaseproject

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ComplaintAdapter(
    private val complaints: List<Complaint>,
    private val onItemClick: (Complaint) -> Unit
) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

    class ComplaintViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvItemTitle)
        val tvStudent: TextView = view.findViewById(R.id.tvItemStudent)
        val tvRoll: TextView = view.findViewById(R.id.tvItemRoll)
        val tvCategory: TextView = view.findViewById(R.id.tvItemCategory)
        val tvPriority: TextView = view.findViewById(R.id.tvItemPriority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complaint, parent, false)
        return ComplaintViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val complaint = complaints[position]
        holder.tvTitle.text = complaint.title
        holder.tvStudent.text = complaint.studentName
        holder.tvRoll.text = complaint.rollNumber
        holder.tvCategory.text = complaint.category
        holder.tvPriority.text = complaint.priority?.uppercase()

        val context = holder.itemView.context
        
        // Dynamic Priority Styling
        val (bgColor, txtColor) = when (complaint.priority?.lowercase()) {
            "low" -> Pair(R.color.bg_low, R.color.txt_low)
            "medium" -> Pair(R.color.bg_medium, R.color.txt_medium)
            "high" -> Pair(R.color.bg_high, R.color.txt_high)
            "urgent" -> Pair(R.color.bg_urgent, R.color.txt_urgent)
            else -> Pair(R.color.bg_low, R.color.txt_low)
        }

        val shape = holder.tvPriority.background as GradientDrawable
        shape.setColor(ContextCompat.getColor(context, bgColor))
        holder.tvPriority.setTextColor(ContextCompat.getColor(context, txtColor))

        holder.itemView.setOnClickListener { onItemClick(complaint) }
    }

    override fun getItemCount() = complaints.size
}
