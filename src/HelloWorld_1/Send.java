package HelloWorld_1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {
	
	//https://www.rabbitmq.com/tutorials/tutorial-one-java.html

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		//crear una conexi�n con el servidor
		//La conexi�n abstrae la conexi�n del socket y se encarga de la negociaci�n y autenticaci�n de la versi�n del protocolo,
		//y as� sucesivamente. Aqu� nos conectamos a un corredor en la m�quina local, de ah� el localhost . 
		//Si quisi�ramos conectarnos a un corredor en una m�quina diferente, simplemente especificar�amos aqu� su nombre o direcci�n IP.
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
		try (Connection connection = factory.newConnection(); 
				Channel channel = connection.createChannel()) {
			
			
			//Luego creamos un canal, que es donde reside la mayor parte de la API para hacer las cosas. 
			//Tenga en cuenta que podemos usar una declaraci�n de prueba con recursos porque tanto Connection como Channel 
			//implementan java.io.Closeable. De esta manera, no necesitamos cerrarlos expl�citamente en nuestro c�digo.
			//Declarar una cola es idempotente: solo se crear� si a�n no existe. 
			//El contenido del mensaje es una matriz de bytes, por lo que puede codificar lo que quiera all�.
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World! 2s";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
			System.out.println(" [x] Sent '" + message + "'");
			
		}
	}
}