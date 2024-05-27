package ltd.matrixstudios.spaces.environments.routes;

import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.environments.files.WrappedFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GETSearchEnvironment {

    @RequestMapping(value = {"/environment/search/{id}"}, method = {RequestMethod.GET})
    public ModelAndView searchBasedOnId(@PathVariable String id, @RequestParam String keyword, @RequestParam(defaultValue = "0") int depth) {
        ModelAndView modelAndView = new ModelAndView("editor");
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);
        // Why in the world do we have to do this
        List<File> searchResults = environment.search(keyword.replace("\"", ""), depth);

        modelAndView.addObject("environment", environment);
        modelAndView.addObject("files", searchResults.stream().map(WrappedFile::new).collect(Collectors.toList()));

        return modelAndView;
    }
}
