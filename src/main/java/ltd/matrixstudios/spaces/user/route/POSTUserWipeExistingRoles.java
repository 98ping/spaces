package ltd.matrixstudios.spaces.user.route;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.user.UserRepository;
import ltd.matrixstudios.spaces.user.model.SpaceUser;
import ltd.matrixstudios.spaces.user.model.SpaceUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.UUID;

@Controller
public class POSTUserWipeExistingRoles {

    @Autowired
    public UserRepository repository;

    @RequestMapping(value = {"/user/{id}/roles/wipe"}, method = {RequestMethod.POST})
    public String delegateRole(HttpServletRequest request, @PathVariable String id) {
        // Make sure requester has the permissions they need
        SpaceUser requester = (SpaceUser) request.getSession().getAttribute("user");

        if (requester == null || !requester.has(SpaceUserRole.ADMIN)) {
            throw new RuntimeException("You do not have permission to request this endpoint!");
        }

        SpaceUser user = repository.findById(UUID.fromString(id)).orElse(null);

        if (user == null) {
            throw new RuntimeException("Unable to find a space user with this identifier!");
        }

        user.getRoles().clear();
        repository.save(user);

        System.out.println("Removed all roles from the user " + id);

        return "redirect:/home";
    }
}