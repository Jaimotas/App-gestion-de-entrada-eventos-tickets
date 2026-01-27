package com.grupo5.tickets4u

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventAdapter(
    private val events: List<Event>,
    private val onEdit: (Event) -> Unit,
    private val onDelete: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var isEditMode = false

    fun setEditMode(enabled: Boolean) {
        isEditMode = enabled
        notifyDataSetChanged()
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.event_name)
        val location: TextView = itemView.findViewById(R.id.event_location)
        val date: TextView = itemView.findViewById(R.id.event_date)
        val image: ImageView = itemView.findViewById(R.id.event_image)
        val trendingBadge: TextView = itemView.findViewById(R.id.event_trending_badge)
        val adminActions: LinearLayout = itemView.findViewById(R.id.layoutAdminActions)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEditEvent)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.name.text = event.nombre
        holder.location.text = event.ubicacion
        holder.date.text = event.fechaInicio

        Glide.with(holder.itemView.context)
            .load(event.foto)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.image)

        holder.trendingBadge.visibility = if (event.categoria == "DESTACADO") View.VISIBLE else View.GONE

        // Mostrar/Ocultar botones de admin
        holder.adminActions.visibility = if (isEditMode) View.VISIBLE else View.GONE

        holder.btnEdit.setOnClickListener { onEdit(event) }
        holder.btnDelete.setOnClickListener { onDelete(event) }

        holder.itemView.setOnClickListener {
            if (!isEditMode) {
                val context = holder.itemView.context
                val intent = Intent(context, PaginaCompraActivity::class.java).apply {
                    putExtra("EVENTO_ID", event.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = events.size
}