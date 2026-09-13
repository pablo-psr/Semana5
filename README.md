
# SpeedFast – Sincronizando procesos en sistemas concurrentes (Semana 5)

Proyecto formativo del curso **Desarrollo Orientado a Objetos II**. Continúa el caso de la empresa **SpeedFast**, esta vez resolviendo un problema de **concurrencia**: varios repartidores retiran pedidos al mismo tiempo desde una zona de carga común, lo que puede provocar que un mismo pedido sea entregado dos veces.

## Objetivo

Simular, con **hilos (Thread/Runnable)** y **mecanismos de sincronización**, un sistema en el que:

- Los pedidos llegan a una zona de carga compartida.
- Varios repartidores retiran pedidos en paralelo.
- Cada pedido es retirado y entregado por **un único** repartidor (sin duplicados ni condiciones de carrera).

## Estructura de clases

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `EstadoPedido` | `enum` | Define los estados `PENDIENTE`, `EN_REPARTO`, `ENTREGADO`. |
| `Pedido` | Clase | Modela un pedido: `id`, `direccionEntrega`, `estado`. Incluye constructor, getters, setters, `toString()` y `setEstado(String nuevoEstado)`. |
| `ZonaDeCarga` | Clase (recurso compartido) | Guarda los pedidos pendientes (`BlockingQueue<Pedido>` o `List<Pedido>` protegida). Expone `agregarPedido(Pedido p)` y `retirarPedido()`, ambos `synchronized`, para que ningún pedido se entregue a dos repartidores a la vez. |
| `Repartidor` | Clase (`implements Runnable`) | Tiene `nombre` y una referencia a la `ZonaDeCarga`. En `run()`: retira un pedido, lo pasa a `EN_REPARTO`, simula la entrega con `Thread.sleep()` y lo marca `ENTREGADO`. |
| `Main` | Clase | Crea la `ZonaDeCarga`, agrega al menos 5 pedidos, lanza 3 hilos `Repartidor` (con `Thread` o `ExecutorService`), espera a que terminen y muestra el mensaje final. |

## Flujo de ejecución

1. `Main` crea la `ZonaDeCarga` y agrega los pedidos iniciales (estado `PENDIENTE`).
2. Se crean 3 `Repartidor`, cada uno en su propio hilo.
3. Cada repartidor, dentro de `run()`, retira un pedido de forma segura (`retirarPedido()` sincronizado), lo marca `EN_REPARTO`, "entrega" (espera unos segundos) y lo marca `ENTREGADO`.
4. Cuando todos los hilos terminan, `Main` imprime:
   `"Todos los pedidos han sido entregados correctamente."`

## Requisitos técnicos

- Java (proyecto desarrollado en **IntelliJ IDEA**).
- Uso de `Thread` / `Runnable`.
- Sincronización mediante `synchronized` (o alternativamente `Semaphore` / `BlockingQueue`) para proteger el acceso a `ZonaDeCarga`.

## Cómo ejecutar

Desde IntelliJ IDEA:

1. Abrir el proyecto.
2. Ejecutar la clase `Main`.
3. Revisar en consola que cada pedido es retirado, puesto en reparto y entregado por un solo repartidor, sin duplicados.

## Autor

Pablo — estudiante de Desarrollo Orientado a Objetos II, Duoc UC.
