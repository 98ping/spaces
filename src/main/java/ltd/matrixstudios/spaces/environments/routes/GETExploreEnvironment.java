package ltd.matrixstudios.spaces.environments.routes;

import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.environments.files.WrappedFile;
import ltd.matrixstudios.spaces.util.EncryptionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GETExploreEnvironment {
    @RequestMapping(value = {"/environment/explore/{id}"}, method = {RequestMethod.GET})
    public ModelAndView exploreBasedOnId(@PathVariable String id, @RequestParam String path) {
        ModelAndView modelAndView = new ModelAndView("editor");
        UUID formattedId = UUID.fromString(id);
        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);

        List<File> whatToExplore = new ArrayList<>();

        try {
            File whereToExplore = new File(EncryptionUtil.decrypt(path
                    .replace(" ", "+")
                    .replace("\"", "")));

            if (whereToExplore.isDirectory()) {
                File[] files = whereToExplore.listFiles();

                if (files != null) {
                    whatToExplore.addAll(Arrays.stream(files).toList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // can be ignored because we just default them to nothing if it can't be explored
        }

        modelAndView.addObject("environment", environment);
        modelAndView.addObject("path", path);
        modelAndView.addObject("files", whatToExplore.stream().map(WrappedFile::new).collect(Collectors.toList()));

        return modelAndView;
    }
}
