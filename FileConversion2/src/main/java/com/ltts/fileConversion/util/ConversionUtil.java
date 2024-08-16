package com.ltts.fileConversion.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.aspose.cells.Cell;
import com.aspose.cells.CellValueType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ConversionUtil {
	
	 public static String replacePKeyWithClass(String jsonString) throws Exception {
	        // Parse the JSON string into a JsonNode
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonString);

	        // Navigate to the "managedObject" array and replace "p" key with "class" value
	        JsonNode managedObjectArray = rootNode.at("/cmData/managedObject");
	        if (managedObjectArray.isArray()) {
	            for (JsonNode managedObject : managedObjectArray) {
	                if (managedObject.has("class")) {
	                    String className = managedObject.get("class").asText();
	                    ArrayNode classArrayNode = objectMapper.createArrayNode();
	                    
	                    // Iterate over the "p" array and add its elements to the new array node
	                    JsonNode pNode = managedObject.get("p");
	                    if (pNode != null && pNode.isArray()) {
	                        for (JsonNode pNodeItem : pNode) {
	                            // Create a new object node with the "name" as key and its value as the value
	                            ObjectNode classItemNode = objectMapper.createObjectNode();
	                            String keyName = pNodeItem.get("name").asText();
	                            String value = pNodeItem.get("").asText(); // Assuming "" is the key's value
	                            classItemNode.put(keyName, value);
	                            classArrayNode.add(classItemNode);
	                        }
	                    }

	                    // Replace the "p" key with the class name and its array of objects
	                    ((ObjectNode) managedObject).remove("p");
	                    ((ObjectNode) managedObject).set(className, classArrayNode);
	                }
	            }
	        }

	        // Convert the modified JsonNode back to a JSON string
	        return objectMapper.writeValueAsString(rootNode);
	    }
	 
	 public static String getCellValueAsString(Cell cell) {
	        if (cell == null) {
	            return "";
	        }

	        switch (cell.getType()) {
	            case CellValueType.IS_STRING:
	                return cell.getStringValue();
	            case CellValueType.IS_NUMERIC:
	                return String.valueOf(cell.getStringValue());
	            case CellValueType.IS_BOOL:
	                return String.valueOf(cell.getBoolValue());
	            case CellValueType.IS_DATE_TIME:
	                return cell.getDateTimeValue().toString();
	            default:
	                return "";
	        }
	    }
	 
	 public static String convertCsvToJson(MultipartFile file) {
	        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
	            // Read CSV headers (first row)
	            String[] headers = csvReader.readNext();
	            if (headers == null) {
	                throw new IOException("CSV file is empty or headers are missing.");
	            }

	            List<Map<String, String>> jsonData = new ArrayList<>();
	            String[] line;
	            while ((line = csvReader.readNext()) != null) {
	                Map<String, String> rowMap = new HashMap<>();
	                for (int i = 0; i < headers.length; i++) {
	                    String value = (i < line.length) ? line[i] : ""; // Handle missing values
	                    rowMap.put(headers[i], value);
	                }
	                jsonData.add(rowMap);
	            }

	            // Convert list of maps to JSON string
	            return convertToJson(jsonData);
	        } catch (IOException | CsvException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	            return null; // Return null or throw a custom exception based on your error handling strategy
	        }
	    }
	 
	 public static String convertCsvFileToJson(File file) {
	        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
	            // Read CSV headers (first row)
	            String[] headers = csvReader.readNext();
	            if (headers == null) {
	                throw new IOException("CSV file is empty or headers are missing.");
	            }

	            List<Map<String, String>> jsonData = new ArrayList<>();
	            String[] line;
	            while ((line = csvReader.readNext()) != null) {
	                Map<String, String> rowMap = new HashMap<>();
	                for (int i = 0; i < headers.length; i++) {
	                    String value = (i < line.length) ? line[i] : ""; // Handle missing values
	                    rowMap.put(headers[i], value);
	                }
	                jsonData.add(rowMap);
	            }

	            // Convert list of maps to JSON string
	            return convertToJson(jsonData);
	        } catch (IOException | com.opencsv.exceptions.CsvException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	            return null; // Return null or throw a custom exception based on your error handling strategy
	        }
	    }

	    public static String convertToJson(List<Map<String, String>> jsonData) {
	        Gson gson = new Gson();
	        return gson.toJson(jsonData);
	    }

}
