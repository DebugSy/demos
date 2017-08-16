package com.shi.java.RMI;

import com.shi.java.RMI.spring.RmiConfig;
import com.shi.java.RMI.spring.SpitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.rmi.RemoteException;

/**
 * Created by DebugSy on 2017/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= RmiConfig.class)
public class SpringRMITest {

	@Autowired
	private SpitterService spitterService;

	@Test
	public void runRmiService(){
		try {
			spitterService.helloRmi();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
