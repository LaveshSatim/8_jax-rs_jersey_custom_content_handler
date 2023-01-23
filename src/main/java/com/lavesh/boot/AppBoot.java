package com.lavesh.boot;

import java.util.HashSet;
import java.util.Set;

import com.lavesh.controller.Controller2;
import com.lavesh.msgconvertor.MsgReader;
import com.lavesh.msgconvertor.MsgWriter;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class AppBoot extends Application {

	private Set<Class<?>> classes;

	public AppBoot() {
		classes = new HashSet<Class<?>>();

		classes.add(MsgReader.class);
		classes.add(MsgWriter.class);
		classes.add(Controller2.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

}
