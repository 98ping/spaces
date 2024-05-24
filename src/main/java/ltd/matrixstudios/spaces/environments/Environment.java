package ltd.matrixstudios.spaces.environments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ltd.matrixstudios.spaces.SpacesApplication;

import java.io.*;
import java.util.UUID;

/**
 * Class created on 5/24/2024
 *
 * @author 98ping
 * @project spaces
 * @website https://solo.to/redis
 */
@Getter
@Setter
public class Environment {
    private UUID randomId;
    private String name;
    private String description;
    private String pathToEnvironment;

    public Environment(String name) {
        this.randomId = UUID.randomUUID();
        this.name = name;
        this.description = "None Yet!";
        this.pathToEnvironment = null;

        save();
    }

    public void save() {
        File path = new File(SpacesApplication.instance.getDirectoryManager().getEnvironmentPath().getPath() + "\\" + randomId.toString());

        if (!path.exists()) {
            path.mkdirs();
        }

        File config = new File(path.getPath() + "\\configuration.json");

        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(config));
            output.write(SpacesApplication.GSON.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
