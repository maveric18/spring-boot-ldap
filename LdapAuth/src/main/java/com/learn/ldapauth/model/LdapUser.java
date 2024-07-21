package com.learn.ldapauth.model;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

public class LdapUser {
    private String cn;
    private String sn;
    private String username;    //userId
    private String password;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Attributes toAttributes() {
        Attributes attributes = new BasicAttributes();
        attributes.put("objectClass", "inetOrgPerson");
        attributes.put("cn", cn);
        attributes.put("sn", sn);
        attributes.put("uid", username);
        attributes.put("userPassword", password);
        return attributes;
    }
}
