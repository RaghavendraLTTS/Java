package com.ltts.fileConversion.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NokiaService {

    public List<JsonNode> convertXmlToJson(String filePath) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode rootNode = xmlMapper.readTree(new File(filePath));
        JsonNode cmData = rootNode.path("cmData");
        Iterator<JsonNode> managedObjects = cmData.path("managedObject").elements();
        List<JsonNode> resultList = new ArrayList<>();

        while (managedObjects.hasNext()) {
            JsonNode managedObject = managedObjects.next();
            resultList.add(parseManagedObject(managedObject));
        }

        return resultList;
    }

    private JsonNode parseManagedObject(JsonNode managedObject) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultNode = mapper.createObjectNode();

        String className = managedObject.get("class").asText();
        String distName = managedObject.get("distName").asText();
        String[] distNameParts = distName.split("/");

        resultNode.put("class", className);

        for (String part : distNameParts) {
            if (part.startsWith("MRBTS-")) {
                resultNode.put("MRBTS", Integer.parseInt(part.split("-")[1]));
            } else if (part.startsWith("LNBTS-")) {
                resultNode.put("LNBTS", Integer.parseInt(part.split("-")[1]));
            } else if (part.startsWith("LNCEL-")) {
                resultNode.put("LNCEL", Integer.parseInt(part.split("-")[1]));
            } else if (part.startsWith("LNREL-")) {
                resultNode.put("LNREL", Integer.parseInt(part.split("-")[1]));
            }
        }

        Iterator<JsonNode> attributes = managedObject.elements();
        while (attributes.hasNext()) {
            JsonNode attribute = attributes.next();
            JsonNode nameNode = attribute.get("name");
            if (nameNode != null) {
                String name = nameNode.asText();
                String value = attribute.get("value").asText("");
                if (!value.isEmpty() && value.matches("-?\\d+")) {
                    resultNode.put(name, Integer.parseInt(value));
                } else {
                    resultNode.put(name, value);
                }
            }
        }

        return resultNode;
    }
}
