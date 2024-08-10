package ltd.matrixstudios.spaces.environments;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.files.WrappedFile;
import ltd.matrixstudios.spaces.environments.labels.Label;
import ltd.matrixstudios.spaces.environments.security.FileSecurityInformation;
import ltd.matrixstudios.spaces.util.EncryptionUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Class created on 5/24/2024
 *
 * @author Max C.
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
    private List<Label> labels = new ArrayList<>();
    // Look for a better identifier for this. Should be fine as of right now though.
    private Map<String, FileSecurityInformation> securedFiles = new HashMap<>();

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

        res.addAll(Arrays.asList(files));

        return res;
    }

    public void updateUsingJsonObject(JsonObject object) {
        this.pathToEnvironment = object.get("environment").getAsString();
        this.description = object.get("description").getAsString();

        save();
    }

    /**
     * Simple search system that uses different layers of depth
     * in order to search into folders and stuff and not just scrape
     * at the surface level.
     * </p>
     * Time complexity of this algorithm scales
     * horrifically so just be noted.
     *
     * @param term - Term that needs to be tested against
     * @param depth - How deep into folders should we search?
     */
    public List<File> search(String term, int depth) {
        List<File> currentFiles = listAllFiles();
        List<File> result = new ArrayList<>();

        if (term == null) return result;

        for (File file : currentFiles) {
            if (!file.isDirectory()) {
                if (file.getName().contains(term)) {
                    result.add(file);
                }
            } else if (file.isDirectory()) {
                if (depth != 0) {
                    int traversed = 0;
                    List<File> toIterate = new ArrayList<>(Arrays.stream(Objects.requireNonNull(file.listFiles())).toList());

                    while (traversed < depth) {
                        List<File> copy = toIterate;
                        toIterate = new ArrayList<>();

                        for (File subFile : copy) {
                            if (!subFile.isDirectory()) {
                                if (subFile.getName().contains(term)) {
                                    result.add(subFile);
                                }
                            } else {
                                toIterate.addAll(Arrays.stream(Objects.requireNonNull(subFile.listFiles())).toList());
                            }
                        }

                        traversed++;
                    }
                }
            }
        }

        return result;
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

        // Cache the stuff AFTER we ensure the disk write was successful
        SpacesApplication.instance.getEnvironmentManager().environmentMap.put(randomId, this);
    }

    public String getStringedIdentifier() {
        return randomId.toString();
    }

    public String createHref(WrappedFile file) {
        try {
            return "/environment/explore/" + getStringedIdentifier() + "?path=\"" + EncryptionUtil.encrypt(file.getFullPath()) + "\"";
        } catch (Exception e) {
            return "/environment/view/" + getStringedIdentifier();
        }
    }
}
