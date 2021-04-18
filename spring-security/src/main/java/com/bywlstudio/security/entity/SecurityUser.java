package com.bywlstudio.security.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: zl
 * @Date: Create in 2021/4/9 16:15
 * @Description:
 */
@Data
@Slf4j
public class SecurityUser implements UserDetails {

    @NotNull
    private transient User user;

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getEnable() != 0;
    }

    private List<String> permissionValueList;

    public SecurityUser() {}

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        permissionValueList.forEach(permissionValue->{
            if(!StringUtils.isEmpty(permissionValue)) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority((permissionValue));
                authorities.add(authority);
            }
        });
        return authorities;
    }


}
