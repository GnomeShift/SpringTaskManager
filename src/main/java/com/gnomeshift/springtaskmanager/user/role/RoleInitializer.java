package com.gnomeshift.springtaskmanager.user.role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {
    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
        return args -> {
            if(roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName(Roles.ROLE_ADMIN);
                roleRepository.save(adminRole);

                Role userRole = new Role();
                userRole.setName(Roles.ROLE_USER);
                roleRepository.save(userRole);
            }
        };
    }
}
