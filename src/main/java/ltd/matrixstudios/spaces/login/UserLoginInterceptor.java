package ltd.matrixstudios.spaces.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltd.matrixstudios.spaces.user.model.SpaceUser;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SpaceUser storedUser = (SpaceUser) request.getSession().getAttribute("user");

        if (storedUser != null) {
            response.sendRedirect("/panel");
        }

        return storedUser == null;
    }
}
