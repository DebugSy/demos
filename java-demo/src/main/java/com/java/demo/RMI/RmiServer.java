package com.java.demo.RMI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by DebugSy on 2017/8/16.
 */
public class RmiServer {
	IntHelloRmi helloRmi;
	public void server() throws RemoteException,MalformedURLException,AlreadyBoundException {
		helloRmi = new ImpHelloRmi();
		//远程对象注册表实例
		LocateRegistry.createRegistry(9999);
		//把远程对象注册到RMI注册服务器上
		Naming.bind("rmi://localhost:9999/HelloRmi",helloRmi);
		System.out.println("server：对象绑定成功");
	}

	public static void main(String[] args) {
		RmiServer rmiServer = new RmiServer();
		try {
			rmiServer.server();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

}
