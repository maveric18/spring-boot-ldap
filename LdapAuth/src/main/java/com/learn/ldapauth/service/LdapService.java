package com.learn.ldapauth.service;

import com.learn.ldapauth.model.LdapUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@Service
public class LdapService {

    private static final String BASE_DN = "ou=users,ou=system";

    @Autowired
    private LdapTemplate ldapTemplate;

    public void addUser(LdapUser ldapUser) {
        ldapTemplate.bind("uid=" + ldapUser.getUsername() + "," + BASE_DN, null, ldapUser.toAttributes());
    }

    public List<LdapUser> getAllUsers() {
        return ldapTemplate.search(BASE_DN, "(objectclass=inetOrgPerson)",
                (AttributesMapper<LdapUser>) attributes -> {
                    LdapUser ldapuser = new LdapUser();
                    ldapuser.setCn(attributes.get("cn").get().toString());
                    ldapuser.setSn(attributes.get("sn").get().toString());
                    ldapuser.setUsername(attributes.get("uid").get().toString());
                    ldapuser.setPassword(attributes.get("userPassword").get().toString());
                    return ldapuser;
                });
    }

    public LdapUser getUserById(String uid) {
        List<LdapUser> userList = ldapTemplate.search(BASE_DN, "(uid="+uid+")",
                (AttributesMapper<LdapUser>) attributes -> {
                    LdapUser ldapuser = new LdapUser();
                    ldapuser.setCn(attributes.get("cn").get().toString());
                    ldapuser.setSn(attributes.get("sn").get().toString());
                    ldapuser.setUsername(attributes.get("uid").get().toString());
                    ldapuser.setPassword(attributes.get("userPassword").get().toString());
                    return ldapuser;
                });
        if (!userList.isEmpty()) {
            return userList.get(0);
        }else {
            return null;
        }
    }

    public void deleteUserById(String uid) {
        Name userDn = LdapNameBuilder.newInstance(BASE_DN).add("uid="+uid).build();
        ldapTemplate.unbind(userDn);
    }
}
