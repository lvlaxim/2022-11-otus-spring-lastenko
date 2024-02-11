package ru.lastenko.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("A user named '%s' is missing", username)));
        return getUserDetailsOf(appUser);
    }

    private UserDetails getUserDetailsOf(AppUser appUser) {
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(getRolesOf(appUser))
                .build();
    }

    private String[] getRolesOf(AppUser appUser) {
        return appUser.getRoles().stream()
                .map(AppRole::getRoleName)
                .toArray(String[]::new);
    }
}
