package com.netty.rpc.app.server;
import com.netty.rpc.service.ContextHolder;
import com.netty.rpc.service.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
@ComponentScan({
        "com.netty.rpc.service",
        "com.netty.rpc.app.server"
})
@EnableAutoConfiguration
public class MainServer {

    @Autowired
    private RpcServer rpcServer;

    @Autowired
    private ContextHolder contextHolder;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF).profiles()
                .sources(MainServer.class)
                .web(false).run(args);
    }

}
