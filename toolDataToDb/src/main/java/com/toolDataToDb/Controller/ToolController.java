package com.toolDataToDb.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toolDataToDb.Model.ProcessData;
import com.toolDataToDb.Model.SearchRequest;
import com.toolDataToDb.Model.ToolResponse;
import com.toolDataToDb.Repo.ProcessDataRepository;
import com.toolDataToDb.Repo.ToolRepository;

@RestController
@RequestMapping("/api")
public class ToolController {

    @Autowired
    private ToolRepository toolRepository;
    
    @Autowired
    private ProcessDataRepository pdRepo;

    @Autowired
    private ObjectMapper objectMapper;

//    @PostMapping("/saveToolOutputToDB")
//    public ResponseEntity<Tool> createTool(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
//        Tool tool = new Tool();
//        tool.setProcessInstanceId((String) payload.get("processInstanceId"));
//        tool.setToolName((String) payload.get("toolName"));
//
//        // Convert the nested JSON array to a String
//        String data = objectMapper.writeValueAsString(payload.get("data"));
//        tool.setData(data);
//
//        Tool savedTool = toolRepository.save(tool);
//        return ResponseEntity.ok(savedTool);
//    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/process-data")
    public ResponseEntity<ProcessData> createProcessData(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
    	ProcessData tool = new ProcessData();
        tool.setProcessInstanceId((String) payload.get("processInstanceId"));
        tool.setToolname((String) payload.get("toolname"));

        // Convert the nested JSON array to a String
        String data = objectMapper.writeValueAsString(payload.get("data"));
        tool.setData(data);

        ProcessData savedTool = pdRepo.save(tool);
        return ResponseEntity.ok(savedTool);
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/getToolOutputFromDB")
    public ResponseEntity<List<ToolResponse>> getToolsByUniqueIDndToolName(@RequestBody SearchRequest request) {
        String processInstanceId = request.getProcessInstanceId();
        String toolname = request.getToolName();

        List<ProcessData> tools = toolRepository.findByProcessInstanceidAndToolname(processInstanceId, toolname);
        System.out.println("Resutl : " + tools);

        List<ToolResponse> formattedTools = tools.stream().map(tool -> {
            String data = tool.getData();
            JsonNode jsonData = null;
            try {
                jsonData = objectMapper.readTree(data);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            // Create a response object with the deserialized data
            ToolResponse response = new ToolResponse(
                tool.getId(),
                tool.getProcessInstanceId(),
                tool.getToolname(),
                jsonData,
                tool.getTimestamp()
            );
            return response;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(formattedTools);
    }
    
    @CrossOrigin(origins = "http://ltts-ui.production.k-meain.he-pi-os-ohn-004.k8s.dyn.nesc.nokia.net")
    @PostMapping("/getAllToolsProcessedData")
    public ResponseEntity<Object> executeQuery(@RequestBody Map<String, String> requestBody) {
        String processInstanceId = requestBody.get("processInstanceId");
        String toolname = requestBody.get("toolname");

        String query = "SELECT JSON_OBJECTAGG(toolname, data) AS result " +
                       "FROM (" +
                       "    SELECT pd.toolname, pd.data " +
                       "    FROM process_data pd " +
                       "    JOIN (" +
                       "        SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(predefinedtools, ',', numbers.n), ',', -1)) AS toolname " +
                       "        FROM (" +
                       "            SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL " +
                       "            SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL " +
                       "            SELECT 9 UNION ALL SELECT 10 " +
                       "        ) numbers " +
                       "        JOIN toolconfig tc ON CHAR_LENGTH(tc.predefinedtools) " +
                       "            -CHAR_LENGTH(REPLACE(tc.predefinedtools, ',', ''))>=numbers.n-1 " +
                       "        WHERE tc.toolname = ? " +
                       "    ) t ON pd.toolname = t.toolname " +
                       "    WHERE pd.process_instanceId = ? " +
                       ") AS tools_data";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://10.129.216.76:3306/demo", "root", "ltts");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, toolname);
            stmt.setString(2, processInstanceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String jsonResult = rs.getString("result");

                // Parse the JSON string into a JSON object
                ObjectMapper mapper = new ObjectMapper();
                Object jsonObject = mapper.readValue(jsonResult, Object.class);

                return ResponseEntity.ok(jsonObject);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}