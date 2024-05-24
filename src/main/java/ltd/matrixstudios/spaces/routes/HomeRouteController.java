package ltd.matrixstudios.spaces.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class created on 5/23/2024
 *
 * @author 98ping
 * @project spaces
 * @website https://solo.to/redis
 */
@Controller
public class HomeRouteController {

    @RequestMapping(value = { "/home", "/" }, method = { RequestMethod.GET })
    public ModelAndView onHomeRequest() {
        return new ModelAndView("home");
    }

    @RequestMapping(value = { "/settings", "/options" }, method = { RequestMethod.GET })
    public ModelAndView onSettingsRequest() {
        return new ModelAndView("settings");
    }
}
