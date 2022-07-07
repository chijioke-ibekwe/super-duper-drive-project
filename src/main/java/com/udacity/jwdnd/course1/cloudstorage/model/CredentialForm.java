package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private String unencryptedPassword;
    private Integer userId;

    public CredentialForm() {
    }

    public CredentialForm(Integer credentialId, String url, String username, String key, String password,
                          String unencryptedPassword, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.unencryptedPassword = unencryptedPassword;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnencryptedPassword() {
        return unencryptedPassword;
    }

    public void setUnencryptedPassword(String unencryptedPassword) {
        this.unencryptedPassword = unencryptedPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
