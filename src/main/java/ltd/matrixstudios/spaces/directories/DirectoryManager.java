package ltd.matrixstudios.spaces.directories;

import lombok.Getter;

import java.io.File;

/**
 * Class created on 5/24/2024
 *
 * @author Max C.
 * @project spaces
 * @website https://solo.to/redis
 */
@Getter
public class DirectoryManager {
    private File dataPath;
    private File environmentPath;
    private File templatePath;

    public DirectoryManager() {
        setupAllDirectories();
    }

    public void setupAllDirectories() {
        File dataPath = new File(System.getProperty("user.home") + "\\spaces");

        if (!dataPath.exists()) {
            dataPath.mkdirs();
        }

        this.dataPath = dataPath;

        File environmentsSection = new File(dataPath.getPath() + "\\environments");

        if (!environmentsSection.exists()) {
            environmentsSection.mkdirs();
        }
        this.environmentPath = environmentsSection;

        File templatesSection = new File(dataPath.getPath() + "\\templates");

        if (!templatesSection.exists()) {
            templatesSection.mkdirs();
        }
        this.templatePath = templatesSection;
    }
}
