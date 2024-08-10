package ltd.matrixstudios.spaces.environments.routes.security;

import com.google.gson.JsonObject;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.environments.files.WrappedFile;
import ltd.matrixstudios.spaces.environments.security.FileSecurityInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class POSTEnvironmentSecureFile {

    @RequestMapping(value = {"/environment/files/{id}/secure"}, method = {RequestMethod.POST})
    public ModelAndView updateEnvironment(@PathVariable String id, @RequestBody String payload) {
        JsonObject jsonObject = SpacesApplication.GSON.fromJson(payload, JsonObject.class);
        ModelAndView modelAndView = new ModelAndView("editor");
        UUID formattedId = UUID.fromString(id);

        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(formattedId);
        String fileName = jsonObject.get("fileName").getAsString();

        // Sending all of this over a single web request MIGHT be an issue, but it is an issue for later me
        environment.getSecuredFiles().put(
                fileName, new FileSecurityInformation(
                        jsonObject.get("password").getAsString(),
                        jsonObject.get("passphrase").getAsString(),
                        jsonObject.get("userId").getAsString()
                )
        );
        environment.save();

        modelAndView.addObject("environment", environment);
        modelAndView.addObject("files", environment.listAllFiles().stream().map(WrappedFile::new).collect(Collectors.toList()));

        return modelAndView;
    }
}
