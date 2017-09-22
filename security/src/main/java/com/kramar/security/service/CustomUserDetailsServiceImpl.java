package com.kramar.security.service;

import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.CustomGrantedAuthorities;
import com.kramar.data.dto.CustomUserDetails;
import com.kramar.data.test.repository.UserRepository;
import com.kramar.data.type.UserRole;
import com.kramar.data.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDbo user = userRepository.getUserByEmail(email);
        UserDetails userDetails = transform(user);
        return userDetails;
    }

    //TODO: Transforms for Users

    public CustomUserDetails transform(UserDbo user) {
        if (user == null) return null;

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(user.getId());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        userDetails.setUserStatus(user.getStatus());
        userDetails.setAuthorities(transformAuthorities(user));

        return userDetails;
    }

    public CustomUserDetails transform(LinkedHashMap<String, String> map) {
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(UUID.fromString(map.get("userId")));
        userDetails.setEmail(map.get("email"));
        userDetails.setUserStatus(UserStatus.valueOf(map.get("userStatus")));
        return userDetails;
    }

    private List<CustomGrantedAuthorities> transformAuthorities(UserDbo user) {
        if (user == null) return Collections.EMPTY_LIST;
//        Map<UserRole, UUID> userRoles = user.getUserRoles();
        List<UserRole> userRoles = user.getUserRoles();
        if (userRoles.isEmpty()) return Collections.EMPTY_LIST;

//        boolean isSystemAdmin = userRoles.keySet().stream()
//                .anyMatch(userRole -> userRole.equals(UserRole.SUPER_ADMIN));

//        CustomGrantedAuthorities authorities;
//        if (isSystemAdmin) {
//            authorities = new SatGrantedAuthorities(UserRole.SYSTEM_ADMIN, TenantContext.getCurrentTenant());
//        } else {
//            log.info("Tenant Id = {}", TenantContext.getCurrentTenant());
//            Optional<SatGrantedAuthorities> authoritiesOp = userRoles.entrySet().stream().filter(e -> e.getValue().equals(TenantContext.getCurrentTenant()))
//                    .map(e -> new SatGrantedAuthorities(e.getKey(), e.getValue()))
//                    .findFirst();
//            if (authoritiesOp.isPresent()) authorities = authoritiesOp.get();
//            else return Collections.EMPTY_LIST;
//        }

        List<CustomGrantedAuthorities> collect = userRoles.stream().map(CustomGrantedAuthorities::new).collect(Collectors.toList());

        return collect;
    }
}
