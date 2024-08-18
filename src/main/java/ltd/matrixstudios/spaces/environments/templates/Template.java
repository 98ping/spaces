package ltd.matrixstudios.spaces.environments.templates;

import lombok.Getter;
import lombok.Setter;
import ltd.matrixstudios.spaces.environments.templates.options.TemplateCreationOption;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Template {
    public String id;
    public String description;
    public List<String> identifierList = new ArrayList<>();
    public List<File> templateFiles = new ArrayList<>();
    public List<TemplateCreationOption> creationOptions = new ArrayList<>();

    public Template(File directory) {
        this.id = directory.getName();
    }
}
