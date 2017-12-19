package com.java.demo.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by DebugSy on 2017/8/16.
 */
public interface IntHelloRmi extends Remote{
	public String helloRmi() throws RemoteException;
	public String sayHello(String name)throws RemoteException;
}
