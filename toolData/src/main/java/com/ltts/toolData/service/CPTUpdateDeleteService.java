package com.ltts.toolData.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.toolData.model.Clients;
import com.ltts.toolData.model.CompositeDeleteRequest;
import com.ltts.toolData.model.CompositeRequest;
import com.ltts.toolData.model.Projects;
import com.ltts.toolData.model.Tools;
import com.ltts.toolData.repo.ClientsRepository;
import com.ltts.toolData.repo.ProjectsRepository;
import com.ltts.toolData.repo.ToolsRepository;

@Service
public class CPTUpdateDeleteService {

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ToolsRepository toolsRepository;

    @Transactional
    public void saveCompositeEntity(CompositeRequest request) {
        Clients client = new Clients();
        client.setName(request.getClientName());
        client.setValue(request.getClientValue());
        Clients savedClient = clientsRepository.save(client);

        Projects project = new Projects();
        project.setName(request.getProjectName());
        project.setValue(request.getProjectValue());
        project.setClient(savedClient);
        Projects savedProject = projectsRepository.save(project);

        List<Tools> tools = request.getTools().stream().map(toolRequest -> {
            Tools tool = new Tools();
            tool.setName(toolRequest.getToolName());
            tool.setValue(toolRequest.getToolValue());
            tool.setProject(savedProject);
            return tool;
        }).collect(Collectors.toList());

        toolsRepository.saveAll(tools);
    }
    
    @Transactional
    public void deleteCompositeEntity(CompositeDeleteRequest request) {
        if (request.getToolIds() != null && !request.getToolIds().isEmpty()) {
            toolsRepository.deleteAllById(request.getToolIds());
        }

        if (request.getProjectId() != null) {
            projectsRepository.deleteById(request.getProjectId());
        }

        if (request.getClientId() != null) {
            clientsRepository.deleteById(request.getClientId());
        }
    }
}