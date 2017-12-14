package rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class InvocationProxy implements InvocationHandler {

    private String host;
    private int port;

    public InvocationProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = new Socket(host, port);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        output.writeUTF(method.getName());
        output.writeObject(method.getParameterTypes());
        output.writeObject(args);

        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        Object result = input.readObject();

        input.close();
        output.close();
        socket.close();

        return result;
    }
}
