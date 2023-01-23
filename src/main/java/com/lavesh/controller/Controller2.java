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
