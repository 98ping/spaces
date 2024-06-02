package ltd.matrixstudios.spaces.environments.routes.labels;

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

/**
 * Class created on 5/31/2024
 *
 * @author Max C.
 * @project spaces
 * @website https://solo.to/redis
 */
@Controller
public class POSTEnvironmentLabelDelete {

    @RequestMapping(value = {"/environment/{id}/labels/remove/{labelId}"}, method = {RequestMethod.POST})
    public ModelAndView addEnvironmentLabels(@PathVariable("id") String id, @PathVariable("labelId") String labelId) {
        ModelAndView modelAndView = new ModelAndView("editor");
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);

        Label environemntLabel = environment.getLabels()
                .stream()
                .filter(label -> label.getId().equalsIgnoreCase(labelId))
                .findFirst().orElse(null);

        if (environemntLabel != null) {
            System.out.println("Received an update request to delete a label from the environment " + environemntLabel.getId());

            environment.getLabels().remove(environemntLabel);
            environment.save();
        }

        modelAndView.addObject("environment", environment);
        modelAndView.addObject("files", environment.listAllFiles().stream().map(WrappedFile::new).collect(Collectors.toList()));

        return modelAndView;
    }
}
