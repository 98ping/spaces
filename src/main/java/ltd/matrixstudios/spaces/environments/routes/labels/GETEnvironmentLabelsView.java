package ltd.matrixstudios.spaces.environments.routes.labels;

import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class GETEnvironmentLabelsView {

    @RequestMapping(value = {"/environment/labels/{id}"}, method = {RequestMethod.GET})
    public ModelAndView getLabelsBasedOnId(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("labels");
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);

        modelAndView.addObject("environment", environment);

        return modelAndView;
    }
}
