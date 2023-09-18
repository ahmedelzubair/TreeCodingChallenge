package sa.com.tree.account.statment.treecodingchallenge.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sa.com.tree.account.statment.treecodingchallenge.entity.User;
import sa.com.tree.account.statment.treecodingchallenge.repository.UserRepository;

import static sa.com.tree.account.statment.treecodingchallenge.dto.Role.ADMIN;
import static sa.com.tree.account.statment.treecodingchallenge.dto.Role.USER;

@Configuration
public class InitialDataConfig {


    @Bean
    public CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            var admin = User.builder()
                    .username("admin")
                    .password("admin")
                    .role(ADMIN)
                    .build();
            repository.save(admin);
            var user = User.builder()
                    .username("user")
                    .password("user")
                    .role(USER)
                    .build();
            repository.save(user);
        };
    }


}
