package com.hdfs.demo.hadooprpc.client;

import com.hdfs.demo.hadooprpc.server.ClientNameNodeProtocol;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpcClient {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 9999);
        ClientNameNodeProtocol namenode = RPC.getProxy(ClientNameNodeProtocol.class, 1L, address, new Configuration());
        String rpc_client = namenode.getMetaData("rpc client");
        System.out.println(rpc_client);
    }

}
