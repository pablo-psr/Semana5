package cl.duoc.sumativa.data;

import cl.duoc.sumativa.model.Pedido;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Recurso compartido que controla el acceso concurrente a las encomiendas.
 */
public class ZonaDeCarga {
    private final Queue<Pedido> colaPedidos = new LinkedList<>();

    /**
     * Agrega un pedido a la zona de carga de forma thread-safe.
     */
    public synchronized void agregarPedido(Pedido p) {
        colaPedidos.add(p);
        System.out.println("Pedido #" + p.getId() + " agregado. Destino: " + p.getDireccionEntrega());
    }

    /**
     * Retira un pedido de la zona de carga evitando condiciones de carrera.
     * Retorna null si no hay pedidos disponibles.
     */
    public synchronized Pedido retirarPedido() {
        return colaPedidos.poll();
    }

    /**
     * Verifica si aún quedan pedidos pendientes por retirar.
     */
    public synchronized boolean tienePedidos() {
        return !colaPedidos.isEmpty();
    }
}