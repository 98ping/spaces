package ltd.matrixstudios.spaces.user.route;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GETUserPage {

    @Autowired public UserService userService;

    @RequestMapping(value = {"/users"}, method = {RequestMethod.GET})
    public ModelAndView getUserPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "page", defaultValue = "1") int page) throws IOException {
        // Make sure requester has the permissions they need
        SpaceUser requester = (SpaceUser) request.getSession().getAttribute("user");

        if (requester == null || !requester.has(SpaceUserRole.ADMIN)) {
            response.sendError(404, "You do not have permission to view this page!");
        }

        ModelAndView modelAndView = new ModelAndView("users");

        List<SpaceUser> users = userService.getOrMapUsers().values().stream().toList();
        List<SpaceUser> result = new ArrayList<>();
        double maxPages = Math.ceil(users.size() / 10.0);

        if (maxPages >= page) {
            int end = page * 10;
            for (int i = end - 10; i < end; i++) {
                if (i < users.size()) {
                    result.add(users.get(i));
                }
            }
        }

        modelAndView.addObject("users", result);
        modelAndView.addObject("page", page);
        modelAndView.addObject("maxPages", Math.round(maxPages));

        return modelAndView;
    }
}
