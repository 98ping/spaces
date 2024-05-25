package ltd.matrixstudios.spaces.routes;

import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.EnvironmentManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class created on 5/23/2024
 *
 * @author Max C.
 * @project spaces
 * @website https://solo.to/redis
 */
@Controller
public class HomeRouteController {

    @RequestMapping(value = { "/home", "/" }, method = { RequestMethod.GET })
    public ModelAndView onHomeRequest() {
        ModelAndView view = new ModelAndView("home");
        view.addObject("environments", SpacesApplication.instance.getEnvironmentManager().getAllEnvironments());

        return view;
    }
    @RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
    public String onCreate(String environmentName) {
        EnvironmentManager.create(environmentName);

        return "redirect:/home"; 
    }

    @RequestMapping(value = { "/settings", "/options" }, method = { RequestMethod.GET })
    public ModelAndView onSettingsRequest() {
        return new ModelAndView("settings");
    }
}
