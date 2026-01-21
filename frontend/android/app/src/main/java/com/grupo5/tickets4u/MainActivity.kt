package com.grupo5.tickets4u

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    // Recyclers
    private lateinit var destacadosRecycler: RecyclerView
    private lateinit var actualesRecycler: RecyclerView
    private lateinit var internacionalesRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbarAndDrawer()
        setupRecyclerViews()

        // Llamada inicial al backend
        fetchEventos()

        // Configuración del botón flotante para crear eventos
        val fab: FloatingActionButton = findViewById(R.id.fabAddEvent)
        fab.setOnClickListener {
            val dialog = CrearEventoDialogFragment(onEventoCreado = {
                // Se ejecuta cuando el servidor responde con éxito (201 Created)
                fetchEventos()
            })
            dialog.show(supportFragmentManager, "CrearEventoDialog")
        }
    }

    private fun setupToolbarAndDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = getColor(android.R.color.white)

        navView.setNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId) {
                R.id.nav_home -> { fetchEventos() }
                R.id.nav_tickets -> { /* Ir a mis tickets */ }
                R.id.nav_settings -> { /* Configuración */ }
                R.id.nav_help -> { /* Ayuda */ }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Flechas de scroll manual para las listas horizontales
        findViewById<ImageView>(R.id.arrow_eventos_actuales).setOnClickListener {
            actualesRecycler.smoothScrollBy(500, 0)
        }
        findViewById<ImageView>(R.id.arrow_eventos_internacionales).setOnClickListener {
            internacionalesRecycler.smoothScrollBy(500, 0)
        }
    }

    private fun setupRecyclerViews() {
        // Vertical (Destacados)
        destacadosRecycler = findViewById(R.id.eventos_recyclerview)
        destacadosRecycler.layoutManager = LinearLayoutManager(this)

        // Horizontal (Actuales)
        actualesRecycler = findViewById(R.id.eventos_actuales_recyclerview)
        actualesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Horizontal (Internacionales)
        internacionalesRecycler = findViewById(R.id.mas_eventos_recyclerview)
        internacionalesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    /**
     * Obtiene los eventos de la API y los filtra por categoría
     */
    private fun fetchEventos() {
        lifecycleScope.launch {
            try {
                val listaEventos = RetrofitClient.instance.getEventos()

                if (listaEventos.isNotEmpty()) {
                    // 1. Destacados: Filtramos por el string exacto que definiste en el backend
                    val destacados = listaEventos.filter { it.categoria.equals("DESTACADO", ignoreCase = true) }
                    destacadosRecycler.adapter = EventAdapter(destacados)

                    // 2. Actuales
                    val actuales = listaEventos.filter { it.categoria.equals("ACTUAL", ignoreCase = true) }
                    actualesRecycler.adapter = EventAdapter(actuales)

                    // 3. Internacionales
                    val internacionales = listaEventos.filter { it.categoria.equals("INTERNACIONAL", ignoreCase = true) }
                    internacionalesRecycler.adapter = EventAdapter(internacionales)
                } else {
                    Log.d("API_INFO", "La lista de eventos está vacía")
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al cargar eventos: ${e.message}")
                Toast.makeText(this@MainActivity, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }
}