package loan.management.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import loan.management.dto.BaseResponse;
import loan.management.dto.UserDto;
import loan.management.services.UserService;

@Path("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @POST
    @Path("/signup")
    public BaseResponse<Object> signup(
            UserDto userDto
    ) throws Exception {

        return userService.signup(userDto);
    }

    @POST
    @Path("/login")
    public BaseResponse<Object> login(
            UserDto userDto
    ) throws Exception {

        return userService.login(userDto);
    }
}