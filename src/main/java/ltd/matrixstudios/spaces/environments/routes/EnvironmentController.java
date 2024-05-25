package ltd.matrixstudios.spaces.environments.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class created on 5/24/2024
 *
 * @author 98ping
 * @project spaces
 * @website https://solo.to/redis
 */
@Controller
public class EnvironmentController {

    @RequestMapping(value = { "/environment/view/{id}" }, method = { RequestMethod.GET })
    public ModelAndView openEnvironmentEditor(@PathVariable String id) {
        return new ModelAndView("editor");
    }
}
