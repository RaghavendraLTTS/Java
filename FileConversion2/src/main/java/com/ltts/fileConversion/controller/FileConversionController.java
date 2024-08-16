package com.ltts.fileConversion.controller;

import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspose.cells.Cells;
import com.aspose.cells.Row;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ltts.fileConversion.Config.FileProperties;
import com.ltts.fileConversion.util.ConversionUtil;

@RestController
@RequestMapping("/api")
public class FileConversionController {
	
//	@Autowired
//	private NokiaService nokiaService;

    private static final String JSON_OUTPUT_PATH = "src/main/resources/output.json";
//    private String DRIVE_C_PATH = "C:/nokia"; // Path to the C: drive  
    
    private final FileProperties fileProperties;
    
    @Autowired
    public FileConversionController(FileProperties fileProperties) {
    	this.fileProperties = fileProperties;
    }
    
    
    @PostMapping("/convertFilesToJson")
    public ResponseEntity<?> convertFilesToJson() throws IOException {
        List<File> files = getFilesFromDriveC(); // Get list of files from C: drive
		if (files.isEmpty()) {
		    return ResponseEntity.badRequest().body("No files found on drive C:");
		}

		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode resultArray = objectMapper.createArrayNode();

		for (File file : files) {
		    try {
		        ObjectNode fileResult = processFile(file);
		        if(fileResult !=null) {
		        resultArray.add(fileResult);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                             .body("Error processing file: " + file.getName() + ", " + e.getMessage());
		    }
		}
		System.out.println("Shabaz code :" );
		return ResponseEntity.ok(resultArray);
    }
    
    private List<File> getFilesFromDriveC() {
        List<File> files = new ArrayList<>();
        File[] driveCFiles = new File(fileProperties.getPath()).listFiles();
        if (driveCFiles != null) {
            for (File file : driveCFiles) {
                if (file.isFile()) {
                    files.add(file);
                }
            }
        }
        return files;
    }

//    private ObjectNode processFile(File file) throws Exception {
//        String originalFilename = file.getName();
//        ObjectNode fileResult = new ObjectMapper().createObjectNode();
//
//        if (originalFilename.toLowerCase().endsWith(".csv")) {
//            // Handle CSV file
//            String jsonData = ConversionUtil.convertCsvFileToJson(file);
//            ArrayNode arrayNode = (ArrayNode) new ObjectMapper().readTree(jsonData);
//            fileResult.put("filename", originalFilename);
//            fileResult.set("data", arrayNode);
//        } else if (originalFilename.toLowerCase().endsWith(".xlsb")) {
//            // Handle Excel file
//            String jsonData = convertExcelToJson(file);
//            ArrayNode arrayNode = (ArrayNode) new ObjectMapper().readTree(jsonData);
//            fileResult.put("filename", originalFilename);
//            fileResult.set("data", arrayNode);
//        } else if (originalFilename.toLowerCase().endsWith(".xml")) {
//            // Handle XML file
//            String jsonData = convertXmlToJson(file);
//            JsonNode jsonNode = new ObjectMapper().readTree(jsonData);
//            fileResult.put("filename", originalFilename);
//            fileResult.set("data", jsonNode);
//        } else {
//            // Unsupported file type
//            fileResult.put("filename", originalFilename);
//            fileResult.put("error", "Unsupported file format");
//        }
//
//        return fileResult;
//    }
    
    
    //Note : this following code is written to ignore other formats except csv,xml,xlsb and not to include them in response
    
    
    private ObjectNode processFile(File file) throws Exception {
        String originalFilename = file.getName();
        ObjectNode fileResult = null;

        if (originalFilename.toLowerCase().endsWith(".csv")) {
            // Handle CSV file
            fileResult = new ObjectMapper().createObjectNode();
            String jsonData = ConversionUtil.convertCsvFileToJson(file);
            ArrayNode arrayNode = (ArrayNode) new ObjectMapper().readTree(jsonData);
            fileResult.put("filename", originalFilename);
            fileResult.set("data", arrayNode);
        } else if (originalFilename.toLowerCase().endsWith(".xlsb")) {
            // Handle Excel file
            fileResult = new ObjectMapper().createObjectNode();
            String jsonData = convertExcelToJson(file);
            ArrayNode arrayNode = (ArrayNode) new ObjectMapper().readTree(jsonData);
            fileResult.put("filename", originalFilename);
            fileResult.set("data", arrayNode);
        } else if (originalFilename.toLowerCase().endsWith(".xml")) {
            // Handle XML file
            fileResult = new ObjectMapper().createObjectNode();
            String jsonData = convertXmlToJson(file);
            JsonNode jsonNode = new ObjectMapper().readTree(jsonData);
            fileResult.put("filename", originalFilename);
            fileResult.set("data", jsonNode);
        }

        return fileResult;
    }

    private String convertExcelToJson(File file) throws Exception {
        Workbook workbook = new Workbook(file.getAbsolutePath());
        Worksheet sheet = workbook.getWorksheets().get(0);
        Cells cells = sheet.getCells();
        Row headerRow = cells.getRows().get(0);

        List<ObjectNode> jsonList = new ArrayList<>();
        for (int rowIndex = 1; rowIndex <= cells.getMaxDataRow(); rowIndex++) {
            Row dataRow = cells.getRows().get(rowIndex);
            if (dataRow == null) {
                continue;
            }
            ObjectNode jsonObject = new ObjectMapper().createObjectNode();
            for (int colIndex = 0; colIndex <= cells.getMaxDataColumn(); colIndex++) {
                String header = headerRow.get(colIndex).getStringValue();
                String value = dataRow.get(colIndex).getStringValue();
                jsonObject.put(header, value);
            }
            jsonList.add(jsonObject);
        }

        return jsonList.toString();
    }

    private String convertXmlToJson(File file) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        Object xmlObject = xmlMapper.readValue(file, Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(xmlObject);
        return ConversionUtil.replacePKeyWithClass(jsonData);
    }
    
    
}