package ltd.matrixstudios.spaces.environments.routes;

import com.google.gson.JsonObject;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * Class created on 5/24/2024
 *
 * @author Max C.
 * @project spaces
 * @website https://solo.to/redis
 */
@Controller
public class EnvironmentController {

    @RequestMapping(value = { "/environment/view/{id}" }, method = { RequestMethod.GET })
    public ModelAndView openEnvironmentEditor(@PathVariable String id) {
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);
        ModelAndView modelAndView = new ModelAndView("editor");

        modelAndView.addObject("environment", environment);

        return modelAndView;
    }

    @RequestMapping(value = { "/environment/update/{id}" }, method = { RequestMethod.POST })
    public ModelAndView updateEnvironment(@PathVariable String id, @RequestBody String payload) {
        JsonObject jsonObject = SpacesApplication.GSON.fromJson(payload, JsonObject.class);
        ModelAndView modelAndView = new ModelAndView("editor");
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);

        System.out.println("Received an update request with the json object body " + jsonObject.toString());

        environment.updateUsingJsonObject(jsonObject);

        modelAndView.addObject("environment", environment);

        return modelAndView;
    }

}
