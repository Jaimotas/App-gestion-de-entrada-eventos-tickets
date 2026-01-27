package com.tickets4u.pedidos_Mario.controller;

import com.tickets4u.pedidos_Mario.dto.CheckoutRequest;
import com.tickets4u.pedidos_Mario.model.Ticket;
import com.tickets4u.pedidos_Mario.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // NUEVO: para probar que el backend responde en /api
    @GetMapping
    public String apiRoot() {
        return "API Tickets4U OK";
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@RequestBody CheckoutRequest request) {
        checkoutService.procesarCompra(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/clientes/{idCliente}/tickets")
    public List<Ticket> listarTickets(@PathVariable Integer idCliente) {
        return checkoutService.obtenerTicketsCliente(idCliente);
    }
}