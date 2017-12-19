package com.java.demo.RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by DebugSy on 2017/8/16.
 */
public class HelloRmiClient {

	IntHelloRmi helloRmi;
	public void client() throws RemoteException,MalformedURLException,NotBoundException {
		helloRmi=(IntHelloRmi) Naming.lookup("rmi://localhost:9999/HelloRmi");
		System.out.println("client:");
		System.out.println(helloRmi.helloRmi());
		System.out.println(helloRmi.sayHello("yiyong"));
	}

	public static void main(String[] args) {
		HelloRmiClient client = new HelloRmiClient();
		try {
			client.client();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
