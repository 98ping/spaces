package ltd.matrixstudios.spaces.environments.routes;

import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.util.ZipUtility;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.UUID;

@Controller
public class GETZipEnvironmentContents {

    /**
     * This function is a blatant attempt at covering my tracks when it comes to
     * backend error handling lol
     * <p>
     * Don't worry though, JavaScript will handle it!
     */
    @RequestMapping(value = {"/environment/zip/{id}"}, method = {RequestMethod.GET})
    public ResponseEntity<Resource> zipEnvironment(@PathVariable String id) {
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);

        // Basic handling of the environment path stuff
        if (environment == null) {
            return ResponseEntity.status(404).body(null);
        }

        if (environment.getPathToEnvironment() == null) {
            return ResponseEntity.status(404).body(null);
        }

        File environmentDestination = new File(environment.getPathToEnvironment());
        File[] files = environmentDestination.listFiles();

        if (files == null) {
            return ResponseEntity.status(404).body(null);
        }

        // Initiate the directories for both the zip action and the file that the zip
        // will go to
        String zipDir = SpacesApplication.instance.getDirectoryManager().getEnvironmentPath().getPath()
                + "\\" + environment.getStringedIdentifier() + "\\zip";
        String outputPath = zipDir + "\\" + environment.getStringedIdentifier() + ".zip";

        String[] filesToNames = Arrays.stream(files).map(File::getPath).toArray(String[]::new);
        File outputPathFile = new File(outputPath);
        File outputZipDir = new File(zipDir);

        if (!outputZipDir.exists()) {
            outputZipDir.mkdirs();
        }

        if (!outputPathFile.exists()) {
            try {
                outputPathFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(404).body(null);
            }
        }

        // Zip it!
        try {
            ZipUtility.zip(filesToNames, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }


        // Send the input stream back to the webpage for download
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(outputPathFile));

            return ResponseEntity.ok()
                    .contentLength(outputPathFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (FileNotFoundException e) {
            System.out.println("final path didnt exist");
            return ResponseEntity.status(404).body(null);
        }
    }
}
