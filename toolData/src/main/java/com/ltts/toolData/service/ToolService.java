package com.ltts.toolData.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltts.toolData.model.AllTools;
import com.ltts.toolData.model.PredefinedTools;
import com.ltts.toolData.model.ToolConfig;
import com.ltts.toolData.repo.AllToolsRepo;
import com.ltts.toolData.repo.PredefinedToolRepo;
import com.ltts.toolData.repo.ToolRepo;

@Service
public class ToolService {

    @Autowired
    private ToolRepo toolConfigRepository;
    
    @Autowired
    private PredefinedToolRepo predefinedToolRepo;
    
    @Autowired
    private AllToolsRepo toolsRepo;

    public List<ToolConfig> getAllToolConfigs() {
        return toolConfigRepository.findAll();
    }
    
    public List<AllTools> getAllTool2(){
    	return toolsRepo.findAll();
    }

//    public List<ToolConfig> insertToolConfig(List<ToolConfig> toolConfig) {
//        return toolConfigRepository.saveAll(toolConfig);
//    }
    
    public ToolConfig saveOrUpdate(ToolConfig toolConfig) {
    	return toolConfigRepository.save(toolConfig);
    }
    
    public boolean existsById(String toolname) {
    	return toolConfigRepository.existsById(toolname);
    }
    
    
    public List<String> getPredefinedToolsByToolname(String toolname) {
    	PredefinedTools toolConfig = predefinedToolRepo.findByToolname(toolname);
        if (toolConfig != null && toolConfig.getPredefinedtools() != null) {
        	return Arrays.asList(toolConfig.getPredefinedtools().split(","));
        }
        return null;
    }
}
