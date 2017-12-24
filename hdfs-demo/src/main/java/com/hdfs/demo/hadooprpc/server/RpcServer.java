package com.hdfs.demo.hadooprpc.server;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

import java.io.IOException;

public class RpcServer {

    public static void main(String[] args) throws IOException {
        RPC.Builder builder = new RPC.Builder(new Configuration());
        builder.setBindAddress("localhost")
                .setPort(9999)
                .setProtocol(ClientNameNodeProtocol.class)
                .setInstance(new ClientNameNodeProtocolImpl());

        Server server = builder.build();
        server.start();

    }

}
