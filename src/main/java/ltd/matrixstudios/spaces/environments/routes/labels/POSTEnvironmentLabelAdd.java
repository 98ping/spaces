package ltd.matrixstudios.spaces.environments.routes.labels;

import com.google.gson.JsonObject;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.environments.files.WrappedFile;
import ltd.matrixstudios.spaces.environments.labels.Label;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class POSTEnvironmentLabelAdd {

    @RequestMapping(value = {"/environment/{id}/labels/add"}, method = {RequestMethod.POST})
    public ModelAndView addEnvironmentLabels(@PathVariable String id, @RequestBody String payload) {
        Label label = SpacesApplication.GSON.fromJson(payload, Label.class);

        ModelAndView modelAndView = new ModelAndView("editor");
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);

        System.out.println("Received an update request to add a label to the environment " + label.getId());

        environment.getLabels().add(label);
        environment.save();

        modelAndView.addObject("environment", environment);
        modelAndView.addObject("files", environment.listAllFiles().stream().map(WrappedFile::new).collect(Collectors.toList()));

        return modelAndView;
    }
}
