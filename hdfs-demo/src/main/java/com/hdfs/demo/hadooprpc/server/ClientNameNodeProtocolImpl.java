package com.hdfs.demo.hadooprpc.server;

public class ClientNameNodeProtocolImpl implements ClientNameNodeProtocol {
    @Override
    public String getMetaData(String name) {
        return "hadoop rpc return " + name;
    }
}
