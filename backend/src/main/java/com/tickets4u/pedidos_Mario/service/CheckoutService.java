package com.tickets4u.pedidos_Mario.service;

import com.tickets4u.pedidos_Mario.dto.CheckoutItem;
import com.tickets4u.pedidos_Mario.dto.CheckoutRequest;
import com.tickets4u.pedidos_Mario.model.Pedido;
import com.tickets4u.pedidos_Mario.model.Ticket;
import com.tickets4u.pedidos_Mario.repository.PedidoRepository;
import com.tickets4u.pedidos_Mario.repository.TicketRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {

    private final PedidoRepository pedidoRepo;
    private final TicketRepository ticketRepo;

    public CheckoutService(PedidoRepository pedidoRepo, TicketRepository ticketRepo) {
        this.pedidoRepo = pedidoRepo;
        this.ticketRepo = ticketRepo;
    }

    @Transactional
    public void procesarCompra(CheckoutRequest req) {

        long total = Math.round(
            req.getItems().stream()
               .mapToDouble(i -> i.getPrecio() * i.getCantidad())
               .sum()
        );

        for (CheckoutItem item : req.getItems()) {

            Pedido pedido = new Pedido();
            pedido.setIdCliente(req.getIdCliente());
            pedido.setIdEvento(item.getIdEvento());
            pedido.setTotal(total);
            pedido.setPago(req.getPago());

            Pedido pedidoGuardado = pedidoRepo.save(pedido);

            for (int i = 0; i < item.getCantidad(); i++) {
                Ticket t = new Ticket();
                t.setIdCliente(req.getIdCliente());
                t.setIdPedido(pedidoGuardado.getId());
                t.setIdEvento(item.getIdEvento());
                t.setQr(UUID.randomUUID().toString());
                t.setEstado("activo");
                ticketRepo.save(t);
            }
        }
    }

    public List<Ticket> obtenerTicketsCliente(Integer idCliente) {
        return ticketRepo.findByIdCliente(idCliente);
    }
}
