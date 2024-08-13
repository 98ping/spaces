package ltd.matrixstudios.spaces.user.route;

import jakarta.servlet.http.HttpServletRequest;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.environments.files.WrappedFile;
import ltd.matrixstudios.spaces.user.UserService;
import ltd.matrixstudios.spaces.user.model.SpaceUser;
import ltd.matrixstudios.spaces.user.model.SpaceUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GETUserPage {

    @Autowired public UserService userService;

    @RequestMapping(value = {"/users"}, method = {RequestMethod.GET})
    public ModelAndView getUserPage(HttpServletRequest request) {
        // Make sure requester has the permissions they need
        SpaceUser requester = (SpaceUser) request.getSession().getAttribute("user");

        if (requester == null || !requester.has(SpaceUserRole.ADMIN)) {
            throw new RuntimeException("You do not have permission to request this endpoint!");
        }

        ModelAndView modelAndView = new ModelAndView("users");

        modelAndView.addObject("users", userService.getOrMapUsers().values());

        return modelAndView;
    }
}
