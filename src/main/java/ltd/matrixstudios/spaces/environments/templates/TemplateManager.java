package ltd.matrixstudios.spaces.environments.templates;

import ltd.matrixstudios.spaces.SpacesApplication;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TemplateManager {
    public Map<String, Template> templateMap = new HashMap<>();

    public TemplateManager() { loadTemplates(); }

    public void loadTemplates() {
        File templatePath = SpacesApplication.instance.getDirectoryManager().getTemplatePath();

        if (templatePath == null) {
            return;
        }

        File[] files = templatePath.listFiles();

        if (files == null) return;

        for (File template : files) {
            if (template.isDirectory()) {
                templateMap.put(template.getName(), new Template(template));
            }
        }
    }
}
