package com.hdfs.demo.hadooprpc.server;

public interface ClientNameNodeProtocol {
    static final long versionID = 1L;//会读取这个版本号， 但可以和客户端的不一样， 没有校验
    String getMetaData(String name);
}
