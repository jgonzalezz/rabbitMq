package HelloWorld_1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {
	
	//https://www.rabbitmq.com/tutorials/tutorial-one-java.html

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		//crear una conexión con el servidor
		//La conexión abstrae la conexión del socket y se encarga de la negociación y autenticación de la versión del protocolo,
		//y así sucesivamente. Aquí nos conectamos a un corredor en la máquina local, de ahí el localhost . 
		//Si quisiéramos conectarnos a un corredor en una máquina diferente, simplemente especificaríamos aquí su nombre o dirección IP.
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
		try (Connection connection = factory.newConnection(); 
				Channel channel = connection.createChannel()) {
			
			
			//Luego creamos un canal, que es donde reside la mayor parte de la API para hacer las cosas. 
			//Tenga en cuenta que podemos usar una declaración de prueba con recursos porque tanto Connection como Channel 
			//implementan java.io.Closeable. De esta manera, no necesitamos cerrarlos explícitamente en nuestro código.
			//Declarar una cola es idempotente: solo se creará si aún no existe. 
			//El contenido del mensaje es una matriz de bytes, por lo que puede codificar lo que quiera allí.
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World! 2s";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
			System.out.println(" [x] Sent '" + message + "'");
			
		}
	}
}