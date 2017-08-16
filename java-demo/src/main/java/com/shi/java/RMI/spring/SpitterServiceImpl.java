package com.shi.java.RMI.spring;

import org.springframework.stereotype.Component;

import java.rmi.RemoteException;

/**
 * Created by DebugSy on 2017/8/16.
 */
@Component
public class SpitterServiceImpl implements SpitterService {
	@Override
	public String helloRmi() throws RemoteException {
		return "Hello Rmi";
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		return "Hello "+ name +" Rmi";
	}
}
