package HelloWorld_1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

	private final static String QUEUE_NAME = "hello";

	/**
	 * @param argv
	 * @throws Exception
	 */
	public static void main(String[] argv) throws Exception {
		//El DefaultConsumer adicional es una clase que implementa la interfaz de consumidor
		//que usaremos para almacenar los mensajes que nos envía el servidor.
		//La configuración es la misma que la del publisher; abrimos una conexión y un canal, 
		//y declaramos la cola desde la que vamos a consumir. 
		//Tenga en cuenta que esto coincide con la cola a la que Send.java envía publicaciones.
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		//Tenga en cuenta que aquí también declaramos la cola. Debido a que podríamos comenzar al consumidor antes que al publisher,
		//queremos asegurarnos de que la cola exista antes de intentar consumir mensajes de ella.
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		//Estamos a punto de decirle al servidor que nos envíe los mensajes de la cola. 
		//Dado que nos enviará mensajes de forma asincrónica, proporcionamos una devolución de llamada en forma de un objeto que almacenará
		//los mensajes en el búfer hasta que estemos listos para usarlos. Eso es lo que hace una subclase de DeliverCallback .
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
		};
		
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
			
		});
	}
}

//NOTA: ¿Por qué no usamos una declaración de prueba con recursos para cerrar automáticamente el canal y la conexión? 
//Al hacerlo, simplemente haríamos que el programa avance, cierre todo y salga. 
//Esto sería incómodo porque queremos que el proceso se mantenga vivo mientras el consumidor escucha de forma asincrónica la llegada de mensajes.