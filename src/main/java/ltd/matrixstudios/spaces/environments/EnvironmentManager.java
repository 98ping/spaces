package ltd.matrixstudios.spaces.environments;

import ltd.matrixstudios.spaces.SpacesApplication;
import org.springframework.security.core.parameters.P;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * Class created on 5/24/2024
 *
 * @author 98ping
 * @project spaces
 * @website https://solo.to/redis
 */
public class EnvironmentManager {
    public HashMap<UUID, Environment> environmentMap = new HashMap<>();

    public EnvironmentManager() {
        loadAllEnvironmentsIntoMap();
    }

    public void loadAllEnvironmentsIntoMap() {
        File environmentPath = SpacesApplication.instance.getDirectoryManager().getEnvironmentPath();

        if (environmentPath == null) {
            return;
        }

        File[] files = environmentPath.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                File[] elementContents = file.listFiles();

                if (elementContents == null) {
                    return;
                }

                File configurationFile = Arrays.stream(elementContents)
                        .filter(element -> element.getName().equals("configuration.json"))
                        .findFirst()
                        .orElse(null);

                if (configurationFile == null) {
                    return;
                }

                FileReader fileContents;

                try {
                    fileContents = new FileReader(configurationFile);
                } catch (FileNotFoundException e) {
                    fileContents = null;
                }

                if (fileContents == null) {
                    return;
                }

                Environment environment = SpacesApplication.GSON.fromJson(fileContents, Environment.class);

                if (environment != null) {
                    environmentMap.put(environment.getRandomId(), environment);
                }
            }
        }
    }

    // Static to ensure thymeleaf can see this
    public static void create(String name) {
        Environment toInsert = new Environment(name);

        SpacesApplication.instance.getEnvironmentManager().environmentMap.put(toInsert.getRandomId(), toInsert);
    }
}
