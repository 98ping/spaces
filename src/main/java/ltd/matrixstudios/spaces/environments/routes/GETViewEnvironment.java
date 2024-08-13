package ltd.matrixstudios.spaces.environments.routes;

import jakarta.servlet.http.HttpServletRequest;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.environments.files.WrappedFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GETViewEnvironment {
    @RequestMapping(value = {"/environment/view/{id}"}, method = {RequestMethod.GET})
    public ModelAndView openEnvironmentEditor(@PathVariable String id) {
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);
        ModelAndView modelAndView = new ModelAndView("editor");

        modelAndView.addObject("environment", environment);
        modelAndView.addObject("files", environment.listAllFiles().stream().map(WrappedFile::new).collect(Collectors.toList()));

        return modelAndView;
    }
}
