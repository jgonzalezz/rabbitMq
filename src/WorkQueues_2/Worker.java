package WorkQueues_2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Worker {
	
	//https://www.cloudamqp.com/blog/2017-05-31-work-queues-in-rabbitmq-for-resource-intensive-tasks.html
	//https://www.rabbitmq.com/tutorials/tutorial-two-java.html
	
    private static final String TASK_QUEUE_NAME = "task_queue";

    /**
     * @param argv
     * @throws Exception
     */
    public static void main(String[] argv) throws Exception {
       
    	
    	
    	
    	
    	
    	ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        
        //https://www.rabbitmq.com/releases/rabbitmq-java-client/v1.7.0/rabbitmq-java-client-javadoc-1.7.0/com/rabbitmq/client/Channel.html
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");	

            System.out.println(" [x] Tarea Received '" + message + "'");
            try {
            	
                doWork(message);
                
            } finally {
                System.out.println(" [x] Tarea "+ message +"realizada");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);//confirme uno o varios mensajes recibidos
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    
    
    //dependiendo del id dek string ejecuta una tarea
    //if(taskId = "CONSULTAANTICIPOS"){
    //
    /**
     * Metodo para simular la tarea
     * @param task
     */
    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    
}

