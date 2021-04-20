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
		//que usaremos para almacenar los mensajes que nos env�a el servidor.
		//La configuraci�n es la misma que la del publisher; abrimos una conexi�n y un canal, 
		//y declaramos la cola desde la que vamos a consumir. 
		//Tenga en cuenta que esto coincide con la cola a la que Send.java env�a publicaciones.
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		//Tenga en cuenta que aqu� tambi�n declaramos la cola. Debido a que podr�amos comenzar al consumidor antes que al publisher,
		//queremos asegurarnos de que la cola exista antes de intentar consumir mensajes de ella.
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		//Estamos a punto de decirle al servidor que nos env�e los mensajes de la cola. 
		//Dado que nos enviar� mensajes de forma asincr�nica, proporcionamos una devoluci�n de llamada en forma de un objeto que almacenar�
		//los mensajes en el b�fer hasta que estemos listos para usarlos. Eso es lo que hace una subclase de DeliverCallback .
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
		};
		
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
			
		});
	}
}

//NOTA: �Por qu� no usamos una declaraci�n de prueba con recursos para cerrar autom�ticamente el canal y la conexi�n? 
//Al hacerlo, simplemente har�amos que el programa avance, cierre todo y salga. 
//Esto ser�a inc�modo porque queremos que el proceso se mantenga vivo mientras el consumidor escucha de forma asincr�nica la llegada de mensajes.