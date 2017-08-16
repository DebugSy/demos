package com.shi.java.RMI.spring;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by DebugSy on 2017/8/16.
 */
public interface SpitterService extends Remote{
	public String helloRmi() throws RemoteException;
	public String sayHello(String name)throws RemoteException;
}
