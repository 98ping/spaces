package ltd.matrixstudios.spaces.environments.routes.user;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltd.matrixstudios.spaces.SpacesApplication;
import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.user.UserRepository;
import ltd.matrixstudios.spaces.user.model.SpaceUser;
import ltd.matrixstudios.spaces.user.model.SpaceUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class POSTEnvironmentDelegateUserPermission {

    @RequestMapping(value = {"/environment/{id}/roles/delegate"}, method = {RequestMethod.POST})
    public String delegateRoleToEnvironment(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody String payload) throws IOException {
        JsonObject jsonObject = SpacesApplication.GSON.fromJson(payload, JsonObject.class);

        // Make sure requester has the permissions they need
        SpaceUser requester = (SpaceUser) request.getAttribute("user");

        if (requester == null || !requester.has(SpaceUserRole.ADMIN)) {
            response.sendError(404, "You do not have permission to view this page!");
        }

        Environment environment = SpacesApplication.instance.getEnvironmentManager().getEnvironmentById(UUID.fromString(id));

        // A bit better safety for enums. Messy but it works
        SpaceUserRole role = Arrays.stream(SpaceUserRole.values())
                .filter(value -> value.name().equals(jsonObject.get("role").getAsString()))
                .findFirst().orElse(null);

        if (role == null) {
            throw new RuntimeException("Unable top find a role by this name!");
        }

        UUID userId = UUID.fromString(jsonObject.get("userId").getAsString());
        List<SpaceUserRole> userRoles = environment.getUserRoles().getOrDefault(userId, new ArrayList<>());
        userRoles.add(role);

        environment.getUserRoles().put(userId, userRoles);
        environment.save();

        return "redirect:/home";
    }
}