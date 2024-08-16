package com.ltts.compareReport.CrService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.compareReport.CrEntity.ProcessData;
import com.ltts.compareReport.CrRepository.ProcessDataRepository;

@Service
public class ProcessDataService {

    @Autowired
    private ProcessDataRepository processDataRepository;

    public List<Map<String, Object>> getReportByToolnameAndTimerange(String toolname, LocalDateTime startTime, LocalDateTime endTime) throws JsonMappingException, JsonProcessingException {
        List<ProcessData> processDataList = processDataRepository.findByToolnameAndTimestampBetween(toolname, startTime, endTime);
        List<Map<String, Object>> reportList = new ArrayList<>();
        for (ProcessData processData : processDataList) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(processData.getData());
            JsonNode reportNode = jsonNode.get("report");
            Map<String, Object> reportMap = new HashMap<>();
//            reportMap.put("transactionId", processData.getTransactionId());
//            reportMap.put("processInstanceId", processData.getProcessInstanceId());
            reportMap.put("timestamp", processData.getTimestamp());
            reportMap.putAll(objectMapper.convertValue(reportNode, Map.class));
            reportList.add(reportMap);
        }
        return reportList;
    }

}


