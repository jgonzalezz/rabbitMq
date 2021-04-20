package WorkQueues_2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		//colas de trabajo
		//En la parte anterior de este tutorial, enviamos un mensaje que contenía "¡Hola Mundo!". Ahora enviaremos Strings que representan tareas complejas.
		//No tenemos una tarea del mundo real, como imágenes para cambiar el tamaño o archivos PDF para ser renderizados, así que simulemos fingiendo que estamos ocupados, 
		//usando la función Thread.sleep () . 
		//Tomaremos el número de puntos en la cadena como su complejidad; 
		//cada punto representará un segundo de "trabajo". 
		//Por ejemplo, una tarea falsa descrita por Hello ... tomará tres segundos.
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection(); 
				Channel channel = connection.createChannel()) {
			
			
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
			String message = "3..............................";

			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
		}
	}

}
