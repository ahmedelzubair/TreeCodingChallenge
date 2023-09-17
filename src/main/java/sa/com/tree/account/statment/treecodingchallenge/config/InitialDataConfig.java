package sa.com.tree.account.statment.treecodingchallenge.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sa.com.tree.account.statment.treecodingchallenge.dto.RegistrationDTO;
import sa.com.tree.account.statment.treecodingchallenge.service.UserService;

import static sa.com.tree.account.statment.treecodingchallenge.dto.Role.ADMIN;
import static sa.com.tree.account.statment.treecodingchallenge.dto.Role.USER;

@Configuration
public class InitialDataConfig {


    @Bean
    public CommandLineRunner commandLineRunner(UserService service) {
        return args -> {
            var admin = RegistrationDTO.builder()
                    .username("admin")
                    .password("admin")
                    .role(ADMIN)
                    .build();
            service.addNewUser(admin);
            var user = RegistrationDTO.builder()
                    .username("user")
                    .password("user")
                    .role(USER)
                    .build();
            service.addNewUser(user);
        };
    }


}
