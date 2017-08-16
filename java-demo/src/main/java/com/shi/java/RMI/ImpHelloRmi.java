package com.shi.java.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by DebugSy on 2017/8/16.
 */
public class ImpHelloRmi extends UnicastRemoteObject implements IntHelloRmi {

	private static final Long serialVersionUID  = 1L;

	public ImpHelloRmi() throws RemoteException{super();}

	@Override
	public String helloRmi() throws RemoteException {
		return "Hello Rmi";
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		return "Hello "+ name +" Rmi";
	}


}
