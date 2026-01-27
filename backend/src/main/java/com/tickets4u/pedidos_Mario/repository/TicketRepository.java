package com.tickets4u.pedidos_Mario.repository;

import com.tickets4u.pedidos_Mario.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByIdCliente(Integer idCliente);
}