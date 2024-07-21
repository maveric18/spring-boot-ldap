package com.learn.ldapauth.controller;

import com.learn.ldapauth.model.LdapUser;
import com.learn.ldapauth.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LdapAuthenticationController {

    @Autowired
    private LdapService ldapService;

    @GetMapping("/")
    public String index() {
        return "Welcome to LDAP Auth Controller";
    }

    @GetMapping("/getLoggedInUser")
    public String getLoggedInUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //access user details
        String username = userDetails.getUsername();
        boolean accountNonExpired = userDetails.isAccountNonExpired();
        return "User Details: " + username + ", Account Non Expired: " + accountNonExpired;
    }

    @GetMapping("/getAllUsers")
    public List<LdapUser> getAllUsers() {
        return ldapService.getAllUsers();
    }

    @GetMapping("/getUserById/{uid}")
    public LdapUser getUserById(@PathVariable String uid) {
        return ldapService.getUserById(uid);
    }

    @GetMapping("/deleteUserById/{uid}")
    public String deleteUserById(@PathVariable String uid) {
        ldapService.deleteUserById(uid);
        return "User delete success";
    }
}
