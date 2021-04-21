package com.old.school.selfdestructingmessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.old.school.selfdestructingmessage.user.UserEntity;
import com.old.school.selfdestructingmessage.user.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
 
    private boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
   
        // Create users
        createUserIfNotFound("admin");
        alreadySetup = true;
    }
 
    private final UserEntity createUserIfNotFound(final String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            user = new UserEntity(username, "$2y$10$m.mhdWyYEVi/7QvVB50iTe0AhSDfZV2i/RB0LsKYFiXJajpYnIfgy");
            user = userRepository.save(user);
        }
        return user;
    }
}
