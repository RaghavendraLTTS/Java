package com.ltts.toolData.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltts.toolData.model.ToolConfigToComundaReq;
import com.ltts.toolData.service.CamundaStarterService2;


@RestController
@RequestMapping("/api")
public class CamundaStarterController {

    @Autowired
    private CamundaStarterService2 processStarter;

    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/startProcess")
    public String startProcess(@RequestBody ToolConfigToComundaReq jsonInput) {
        try {
        	return processStarter.startProcess(jsonInput);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "process start Error :"+ e.getMessage();
		}
        
    }
}