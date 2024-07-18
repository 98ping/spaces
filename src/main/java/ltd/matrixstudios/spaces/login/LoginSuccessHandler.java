package ltd.matrixstudios.spaces.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltd.matrixstudios.spaces.user.UserRepository;
import ltd.matrixstudios.spaces.user.UserService;
import ltd.matrixstudios.spaces.user.model.SpaceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    public UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);

        SpaceUser userFromAuthentication = userRepository.findByUsername(authentication.getName());

        if (userFromAuthentication == null) {
            throw new RuntimeException("Unable to find a space user with this name!");
        }

        request.getSession().setAttribute("user", userFromAuthentication);

        String redirect = "/panel";

        if (request.getAttribute("redirect") != null) {
            redirect = (String) request.getAttribute("redirect");
        }

        System.out.println(userFromAuthentication.getDisplayName() + " has been successfully logged in!");
        response.sendRedirect(redirect);
    }
}
