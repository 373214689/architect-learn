package example.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCServer {

    private static final int processors = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool(processors);
    private static final HashMap<String, Class<?>> services = new HashMap<>();

    private boolean running = false;
    private int port = 8888;

    public RPCServer() {

    }

    public RPCServer(int port) {
        this.port = port;
    }

    public void register(Class<?> template, Class<?> impl) {
        services.put(template.getName(), impl);
    }

    public void start() {
        running = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                executor.execute(new ServiceHandler(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }

    private static class ServiceHandler implements Runnable {

        private final Socket client;
        public ServiceHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try (ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                 ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream())) {
                // 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class<?> serviceClass = services.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);
                // 3.将执行结果反序列化，通过socket发送给客户端
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
