package com.tickets4u.pedidos_Mario.repository;

import com.tickets4u.pedidos_Mario.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
