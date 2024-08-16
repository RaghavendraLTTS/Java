package com.ltts.toolData.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ltts.toolData.model.AllTools;
import com.ltts.toolData.model.CompositeDeleteRequest;
import com.ltts.toolData.model.CompositeRequest;
import com.ltts.toolData.model.PredefinedToolReq;
import com.ltts.toolData.model.PredefinedToolsRes;
import com.ltts.toolData.model.ToolConfig;
import com.ltts.toolData.model.ToolConfigToComundaReq;
import com.ltts.toolData.model.ToolData;
import com.ltts.toolData.service.CPTUpdateDeleteService;
import com.ltts.toolData.service.ToolService;

@RestController
@RequestMapping("/api")
public class ToolController {

    @Autowired
    private ToolService toolConfigService;
    
    @Autowired
    private CPTUpdateDeleteService compositeService;
    
    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @GetMapping("/getToolConfig")
    public List<ToolConfig> getAllToolConfigs() {
        return toolConfigService.getAllToolConfigs();
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @GetMapping("/getTools")
    public List<AllTools> getAllTools2(){
    	return toolConfigService.getAllTool2();
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/saveToolConfig")
    public ResponseEntity<String> saveOrUpdate(@RequestBody ToolConfig toolConfig){
    	boolean exists = toolConfigService.existsById(toolConfig.getToolname());
    	toolConfigService.saveOrUpdate(toolConfig);
    	if(exists) {
    		return ResponseEntity.ok("Tool updated successfully");
    	} else {
    		return ResponseEntity.ok("Tool added successfully");
    	}
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/predefinedtools")
    public List<PredefinedToolsRes> getPredefinedTools(@RequestBody PredefinedToolReq request) {
       String toolName = request.getToolname();
       PredefinedToolsRes res = new PredefinedToolsRes(toolName, toolConfigService.getPredefinedToolsByToolname(toolName));
       return Collections.singletonList(res);
       
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/updatecpt")
    public ResponseEntity<String> createCompositeEntity(@RequestBody CompositeRequest request) {
        compositeService.saveCompositeEntity(request);
        return ResponseEntity.ok("Saved");
        
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @DeleteMapping("/deletecpt")
    public ResponseEntity<String> deleteCompositeEntity(@RequestBody CompositeDeleteRequest request) {
        compositeService.deleteCompositeEntity(request);
        return ResponseEntity.ok("Deleted");
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/toolConfigToComunda")
	public ResponseEntity<ArrayNode> echoJson(@RequestBody ToolConfigToComundaReq jsonInput){
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
    		
    		String jsonString = objectMapper.writeValueAsString(jsonInput);
    		JsonNode jsonNode = objectMapper.readTree(jsonString);
    		
    		ArrayNode arrayNode = objectMapper.createArrayNode();
    		arrayNode.add(jsonNode);
    		
    		return ResponseEntity.ok(arrayNode);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(500).build();
		}
		
	}
    
    private static final String JDBC_URL = "jdbc:mysql://10.129.216.76:3306/demo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ltts";
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @GetMapping("/getData")
    public List<ToolData> fetchData() {
    	  List<ToolData> dataList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "select " +
                    "t.id as t_id,c.id as c_id, c.name as c_name, c.value as c_value, " +
                    "p.id as p_id, p.name as p_name, p.value as p_value, p.client_id as p_client_id, " +
                    "t.name as t_name, t.value as t_value, t.project_id as t_project_id " +
                    "from clients c, tools t, projects p " +
                    "where c.id = p.client_id and p.id = t.project_id";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

            	while (resultSet.next()) {
                    ToolData data = new ToolData();
                    data.setCid(resultSet.getInt("c_id"));
                    data.setCname(resultSet.getString("c_name"));
                    data.setCvalue(resultSet.getString("c_value"));
                    data.setPid(resultSet.getInt("p_id"));
                    data.setPname(resultSet.getString("p_name"));
                    data.setPvalue(resultSet.getString("p_value"));
                    data.setpClientId(resultSet.getInt("p_client_id"));
                    data.setTid(resultSet.getInt("t_id"));
                    data.setTname(resultSet.getString("t_name"));
                    data.setTvalue(resultSet.getString("t_value"));
                    data.settPojectId(resultSet.getInt("t_project_id"));
                    dataList.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
