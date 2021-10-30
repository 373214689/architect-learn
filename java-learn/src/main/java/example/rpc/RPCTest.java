package example.rpc;

import example.rpc.model.RPCSample;
import example.rpc.model.RPCSampleImpl;
import example.rpc.utils.RPCUtils;

import java.net.InetSocketAddress;

public class RPCTest {

    private static final RPCServer server = new RPCServer(8888);

    public static void main(String[] args) {

        Thread thread = new Thread(server::start);
        thread.start();

        server.register(RPCSample.class, RPCSampleImpl.class);

        InetSocketAddress address = new InetSocketAddress("localhost", 8888);
        RPCSample sample = RPCUtils.getRemoteProxyObj(RPCSample.class, address);

        System.out.println(sample.test());
    }


}
