package cl.duoc.sumativa.ui;

import cl.duoc.sumativa.data.ZonaDeCarga;
import cl.duoc.sumativa.model.Pedido;
import cl.duoc.sumativa.model.Repartidor;

public class Main {
    public static void main(String[] args) {
        System.out.println("[Zona de carga inicializada]");

        // 1. Instanciar ZonaDeCarga
        ZonaDeCarga zona = new ZonaDeCarga();

        // 2. Agregar 5 pedidos al sistema
        zona.agregarPedido(new Pedido(1, "Santiago Centro"));
        zona.agregarPedido(new Pedido(2, "Providencia"));
        zona.agregarPedido(new Pedido(3, "Ñuñoa"));
        zona.agregarPedido(new Pedido(4, "Recoleta"));
        zona.agregarPedido(new Pedido(5, "Las Condes"));

        // 3. Crear los objetos Runnable (Repartidores)
        Repartidor repartidor1 = new Repartidor("Juan", zona);
        Repartidor repartidor2 = new Repartidor("Camila", zona);
        Repartidor repartidor3 = new Repartidor("Pedro", zona);

        // 4. Crear los Hilos (Thread) pasándoles las instancias Runnable
        Thread hilo1 = new Thread(repartidor1);
        Thread hilo2 = new Thread(repartidor2);
        Thread hilo3 = new Thread(repartidor3);

        System.out.println("\n--- Iniciando entregas con hilos ---\n");

        // 5. Iniciar la ejecución paralela de cada hilo (.start())
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // 6. APLICACIÓN DE JOIN: Esperar a que todos los hilos terminen
        try {
            hilo1.join(); // El hilo main se pausa hasta que hilo1 (Juan) finalice
            hilo2.join(); // El hilo main se pausa hasta que hilo2 (Camila) finalice
            hilo3.join(); // El hilo main se pausa hasta que hilo3 (Pedro) finalice
        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        // 7. Mensaje final una vez que TODOS los hilos han terminado
        System.out.println("\nTodos los pedidos han sido entregados correctamente.");
    }
}