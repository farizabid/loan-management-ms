package loan.management.services;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import loan.management.constants.GeneralConstant;
import loan.management.dto.BaseResponse;
import loan.management.dto.UserDto;
import loan.management.models.User;
import loan.management.repositories.UserRepository;
import loan.management.utils.ObjectActiveAndCreatedDateUtil;
import loan.management.utils.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public BaseResponse<Object> signup(UserDto sr) throws Exception {
        try {
            User userExisting = userRepository.findByUsername(sr.username);

            if (userExisting != null) {
                return new BaseResponse<>(GeneralConstant.FAILED_CODE, GeneralConstant.USER_ALREADY_EXISTS, "");
            }

            User user = new User();
            user.username = sr.username;
            user.password = PasswordUtil.hash(sr.password);
            user.role = sr.role;
            user.email = sr.email;

            ObjectActiveAndCreatedDateUtil.registerObject(user);
            user.persist();

            return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, "");
        } catch (Exception ex){
            log.info("Error signUp : ", ex);
            throw new Exception(ex);
        }
    }

    @Transactional
    public BaseResponse<Object> login(UserDto lr) throws Exception {
        try {
            User user = userRepository.findByUsername(lr.username);

            if (user == null){
                return new BaseResponse<>(GeneralConstant.FAILED_CODE, GeneralConstant.USER_NOT_FOUND_MSG, "");
            }

            // validate passw if user exist
            boolean valid = PasswordUtil.verify(lr.password, user.password);

            if (!valid){
                return new BaseResponse<>(GeneralConstant.FAILED_CODE, "invalid password", "");
            }

            lr.role = user.role;
            lr.token = generateToken(user.username, user.role.name());

            return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, lr);
        } catch (Exception ex){
            log.info("Error in login : ", ex);
            throw new Exception(ex);
        }

    }

    public String generateToken(String username, String role) {
        return Jwt.issuer("loan-system")
                .subject(username)
                .groups(Set.of(role))
                .expiresIn(Duration.ofHours(1))
                .sign();
    }

    public BaseResponse<Object> getUser(UserDto lr) throws Exception {
        try {
            User user = userRepository.findByUsername(lr.username);

            if (user == null){
                return new BaseResponse<>(GeneralConstant.FAILED_CODE, GeneralConstant.USER_NOT_FOUND_MSG, "");
            }

            return new BaseResponse<>(GeneralConstant.SUCCESS_CODE, GeneralConstant.SUCCESS_MSG, user);
        } catch (Exception ex){
            log.info("Error in getUser : ", ex);
            throw new Exception(ex);
        }
    }

}
