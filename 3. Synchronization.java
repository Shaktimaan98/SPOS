import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ProducerConsumerQueue {
    // Shared buffer with fixed capacity
    private static final int BUFFER_SIZE = 5;
    private final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);

    // Producer Thread
    class Producer extends Thread {
        public void run() {
            int item = 1;
            try {
                while (true) {
                    System.out.println("Producer trying to produce: " + item);
                    buffer.put(item); // Automatically waits if buffer is full
                    System.out.println("Producer produced: " + item);
                    item++;
                    Thread.sleep(1000); // Simulate time delay
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Consumer Thread
    class Consumer extends Thread {
        public void run() {
            try {
                while (true) {
                    System.out.println("Consumer waiting to consume...");
                    int item = buffer.take(); // Automatically waits if buffer is empty
                    System.out.println("Consumer consumed: " + item);
                    Thread.sleep(1500); // Simulate time delay
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerQueue pc = new ProducerConsumerQueue();
        Producer producer = pc.new Producer();
        Consumer consumer = pc.new Consumer();

        producer.start();
        consumer.start();
    }
}
