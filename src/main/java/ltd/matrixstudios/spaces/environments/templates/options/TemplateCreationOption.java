package ltd.matrixstudios.spaces.environments.templates.options;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TemplateCreationOption {
    public String question;
    public boolean freeResponse;
    public List<String> responses = new ArrayList<>();
}
