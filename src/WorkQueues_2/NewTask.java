package WorkQueues_2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		//colas de trabajo
		//En la parte anterior de este tutorial, enviamos un mensaje que conten�a "�Hola Mundo!". Ahora enviaremos Strings que representan tareas complejas.
		//No tenemos una tarea del mundo real, como im�genes para cambiar el tama�o o archivos PDF para ser renderizados, as� que simulemos fingiendo que estamos ocupados, 
		//usando la funci�n Thread.sleep () . 
		//Tomaremos el n�mero de puntos en la cadena como su complejidad; 
		//cada punto representar� un segundo de "trabajo". 
		//Por ejemplo, una tarea falsa descrita por Hello ... tomar� tres segundos.
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
