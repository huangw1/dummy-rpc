package rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class WorkThread implements Runnable {

    private ServerSocket server;
    private Object service;

    public WorkThread(ServerSocket server, Object service) {
        this.server = server;
        this.service = service;
    }

    public void run() {
        while(true) {
            try {
                final Socket socket = server.accept();
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[])input.readObject();
                Object[] arguments = (Object[])input.readObject();
                Method method = service.getClass().getMethod(methodName, parameterTypes);
                Object result = method.invoke(service, arguments);

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}















