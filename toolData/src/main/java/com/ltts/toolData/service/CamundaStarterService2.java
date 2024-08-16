package com.ltts.toolData.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

//import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ltts.toolData.model.ToolConfigToComundaReq;

@Service
public class CamundaStarterService2 {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Autowired
//    private RuntimeService runtimeService;

    public String startProcess(ToolConfigToComundaReq jsonInput) throws Exception {
//        // Call the first API
//        String convertFilesUrl = "http://localhost:8080/api/convertFilesToJson";
//        HttpRequest convertFilesRequest = HttpRequest.newBuilder()
//                .uri(new URI(convertFilesUrl))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.noBody())
//                .build();
//
//        HttpResponse<String> convertFilesResponse = httpClient.send(convertFilesRequest, HttpResponse.BodyHandlers.ofString());
//
//        // Handle the response from /convertFilesToJson
//        ArrayNode rawData = null;
//        if (convertFilesResponse.statusCode() == 200) {
//            rawData = (ArrayNode) objectMapper.readTree(convertFilesResponse.body());
//            System.out.println("Files converted successfully: " + rawData);
//        } else {
//            System.out.println("Error converting files: " + convertFilesResponse.statusCode());
//        }
        
        // Call the second API
        String toolConfigUrl = "http://ltts-toolconfig.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net/api/toolConfigToComunda";
        String jsonInputString = objectMapper.writeValueAsString(jsonInput);
        HttpRequest toolConfigRequest = HttpRequest.newBuilder()
                .uri(new URI(toolConfigUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                .build();

        HttpResponse<String> toolConfigResponse = httpClient.send(toolConfigRequest, HttpResponse.BodyHandlers.ofString());

        // Handle the response from /toolConfigToComunda
        ArrayNode toolsData = null;
        if (toolConfigResponse.statusCode() == 200) {
            toolsData = (ArrayNode) objectMapper.readTree(toolConfigResponse.body());
            System.out.println("Tools printed successfully: " + toolsData);
        } else {
            System.out.println("Error printing tools: " + toolConfigResponse.statusCode());
        }

        // Prepare variables to be passed to the Camunda process instance
        Map<String, Object> variables = new HashMap<>();
//        variables.put("rawData", createCamundaVariable(objectMapper.writeValueAsString(rawData), "Json"));
        variables.put("toolsData", createCamundaVariable(objectMapper.writeValueAsString(toolsData), "Json"));

        // Create the final JSON payload
        Map<String, Object> camundaPayload = new HashMap<>();
        camundaPayload.put("variables", variables);
        camundaPayload.put("businessKey", "myBusinessKey"); // Add any additional keys you need

        String camundaVariablesJson = objectMapper.writeValueAsString(camundaPayload);
        System.out.println("CAMUNDA VARIABLES: " + camundaVariablesJson);

        // Call the Camunda REST API to start a process instance
        String camundaStartProcessUrl = "http://ltts-myproj.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net/engine-rest/process-definition/key/my-project-process/start";
        HttpRequest camundaRequest = HttpRequest.newBuilder()
                .uri(new URI(camundaStartProcessUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(camundaVariablesJson))
                .build();

        HttpResponse<String> camundaResponse = httpClient.send(camundaRequest, HttpResponse.BodyHandlers.ofString());

        if (camundaResponse.statusCode() == 200 || camundaResponse.statusCode() == 201) {
            System.out.println("Started Camunda process instance: " + camundaResponse.body());
        } else {
            System.out.println("Error starting Camunda process instance: " + camundaResponse.statusCode());
            System.out.println("Response: " + camundaResponse.body());
        }

        return camundaResponse.body();
    }

    private Map<String, Object> createCamundaVariable(String value, String type) {
        Map<String, Object> variable = new HashMap<>();
        variable.put("value", value);
        variable.put("type", type);
        return variable;
    }
}