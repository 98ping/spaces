package ltd.matrixstudios.spaces.environments;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ltd.matrixstudios.spaces.SpacesApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public List<File> listAllFiles() {
        List<File> res = new ArrayList<>();

        if (pathToEnvironment == null) {
            return res;
        }

        File environmentPath = new File(pathToEnvironment);
        File[] files = environmentPath.listFiles();

        if (files == null) {
            return res;
        }

        Arrays.stream(files).sorted(((o1, o2) -> Math.toIntExact(o2.lastModified() - o1.lastModified()))).forEach(res::add);

        return res;
    }

    public void adaptJsonObject(JsonObject object) {
        this.pathToEnvironment = object.get("environment").getAsString();
        this.description = object.get("description").getAsString();
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

    public String getStringedIdentifier() {
        return randomId.toString();
    }
}
