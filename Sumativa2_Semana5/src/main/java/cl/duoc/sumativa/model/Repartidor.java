package cl.duoc.sumativa.model;

import cl.duoc.sumativa.data.ZonaDeCarga;

/**
 * Hilo runnable que representa a un repartidor retirando y entregando pedidos.
 */
public class Repartidor implements Runnable {
    private final String nombre;
    private final ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {
        while (zonaDeCarga.tienePedidos()) {
            Pedido pedido = zonaDeCarga.retirarPedido();

            // Si otro hilo vació la cola justo antes, continuamos
            if (pedido == null) {
                break;
            }

            // Cambiar estado a EN_REPARTO
            pedido.setEstado(EstadoPedido.EN_REPARTO);
            System.out.println("[Repartidor - " + nombre + "] Retirando pedido #" + pedido.getId() + "...");
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());

            // Simulación del proceso de entrega con sleep
            try {
                System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getId() + "...");
                Thread.sleep(2000); // Espera de 2 segundos
            } catch (InterruptedException e) {
                System.err.println("[Repartidor - " + nombre + "] Fue interrumpido durante la entrega.");
                Thread.currentThread().interrupt();
            }

            // Cambiar estado a ENTREGADO
            pedido.setEstado(EstadoPedido.ENTREGADO);
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
        }
    }
}