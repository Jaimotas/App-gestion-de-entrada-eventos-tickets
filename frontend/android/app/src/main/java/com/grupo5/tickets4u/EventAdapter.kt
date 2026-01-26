package com.grupo5.tickets4u

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // <--- ASEGÚRATE DE ESTA IMPORTACIÓN


class EventAdapter(private val events: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.event_name)
        val location: TextView = itemView.findViewById(R.id.event_location)
        val date: TextView = itemView.findViewById(R.id.event_date)
        val image: ImageView = itemView.findViewById(R.id.event_image)
        val trendingBadge: TextView = itemView.findViewById(R.id.event_trending_badge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.name.text = event.nombre        // Antes era name
        holder.location.text = event.ubicacion // Antes era location
        holder.date.text = event.fechaInicio   // Antes era date

        Glide.with(holder.itemView.context)
            .load(event.foto)                  // Antes era imageUrl o imageResId
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.image)

        // El badge de trending ahora lo podrías mostrar si la categoría es DESTACADO
        holder.trendingBadge.visibility = if (event.categoria == "DESTACADO") View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int = events.size
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.name.text = event.name
        holder.location.text = event.location
        holder.date.text = event.date
        holder.image.setImageResource(event.imageResId)

        // Mostrar u ocultar el badge según isTrending
        if (event.isTrending) {
            holder.trendingBadge.visibility = View.VISIBLE
        } else {
            holder.trendingBadge.visibility = View.GONE
        }

        // ✅ CLICK → PAGINA COMPRA (reemplaza el TODO)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PaginaCompraActivity::class.java)
            intent.putExtra("EVENTO_ID", event.id)
            intent.putExtra("EVENTO_NOMBRE", event.name)
            intent.putExtra("EVENTO_UBICACION", event.location)
            intent.putExtra("EVENTO_FECHA", event.date)
            intent.putExtra("EVENTO_IMAGEN", event.imageResId)
            context.startActivity(intent)
        }
    }
}
