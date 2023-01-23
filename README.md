## conclusion
>obj to json and json to obj coversion logic

>we can handle content easily


# dependencies

```xml
<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.glassfish.jersey.inject</groupId>
			<artifactId>jersey-hk2</artifactId>
			<version>3.0.8</version>
		</dependency>
		<dependency>
			<groupId>org.glassfish.jersey.containers</groupId>
			<artifactId>jersey-container-servlet</artifactId>
			<version>3.0.8</version>
		</dependency>
		<dependency>
			<groupId>org.glassfish.jersey.core</groupId>
			<artifactId>jersey-server</artifactId>
			<version>3.0.8</version>
		</dependency>
		<dependency>
			<groupId>org.glassfish.jersey.core</groupId>
			<artifactId>jersey-common</artifactId>
			<version>3.0.8</version>
		</dependency>
		<dependency>
			<groupId>jakarta.json.bind</groupId>
			<artifactId>jakarta.json.bind-api</artifactId>
			<version>3.0.0</version>
		</dependency>
		<dependency>
			<groupId>org.eclipse</groupId>
			<artifactId>yasson</artifactId>
			<version>3.0.2</version>
		</dependency>
	</dependencies>
```


# boot class 

```java
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


```


# controller class

```java

package com.lavesh.controller;

import java.util.UUID;

import javax.print.attribute.standard.Media;

import com.lavesh.dto.RechargeRequest;
import com.lavesh.dto.RechargeStatus;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/recharge")
public class Controller2 {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	public RechargeStatus get(RechargeRequest rechargereq) {

		RechargeStatus rechargeStatus = null;

		rechargeStatus = new RechargeStatus();

		rechargeStatus.setId(UUID.randomUUID().toString());
		rechargeStatus.setAmout(rechargereq.getAmout());
		rechargeStatus.setPack(rechargereq.getPack());
		rechargeStatus.setStatus("recharge successful");

		return rechargeStatus;
	}
}

```


## message reader

```java
package com.lavesh.msgconvertor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.lavesh.dto.RechargeRequest;

import jakarta.json.JsonBuilderFactory;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MsgReader implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (type.isAssignableFrom(RechargeRequest.class)) {
			return true;
		}

		return false;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		Jsonb jsonb = JsonbBuilder.create();
		Object object = jsonb.fromJson(entityStream, type);
		return object;
	}

}

```


## message writer

```java
package com.lavesh.msgconvertor;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.lavesh.dto.RechargeStatus;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MsgWriter implements MessageBodyWriter<Object> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (type.isAssignableFrom(RechargeStatus.class)) {
			return true;
		}

		return false;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {

		Jsonb create = JsonbBuilder.create();
		create.toJson(t, entityStream);
	}

}

```



## dto classes

```java
package com.lavesh.dto;

public class RechargeRequest {

	private String id;
	private String pack;
	private String amout;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getAmout() {
		return amout;
	}

	public void setAmout(String amout) {
		this.amout = amout;
	}

}

```


```java
package com.lavesh.dto;

public class RechargeStatus {

	private String id;
	private String pack;
	private String amout;
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getAmout() {
		return amout;
	}

	public void setAmout(String amout) {
		this.amout = amout;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

}

```