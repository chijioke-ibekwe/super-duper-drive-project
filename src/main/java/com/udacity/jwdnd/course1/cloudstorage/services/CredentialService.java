package com.udacity.jwdnd.course1.cloudstorage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;
    private EncryptionService encryptionService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public List<CredentialForm> getAllCredentials(Authentication authentication){
        List<CredentialForm> credentialForms = new ArrayList<>();
        List<Credential> credentials = credentialMapper.getAllCredentials(userService.getUserId(authentication));
        credentials.forEach(credential -> {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            CredentialForm credentialForm = objectMapper.convertValue(credential, CredentialForm.class);
            credentialForm.setUnencryptedPassword(decryptedPassword);
            credentialForms.add(credentialForm);
        });
        return credentialForms;
    }

    public int addCredential(Credential credential, Authentication authentication){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUsername(),
                encodedKey, encryptedPassword, userService.getUserId(authentication)));
    }

    public int updateCredential(Credential credential, Authentication authentication){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setUserId(userService.getUserId(authentication));
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        return credentialMapper.editCredential(credential);
    }

    public int deleteCredential(Integer credentialId, Authentication authentication){
        return credentialMapper.deleteCredential(credentialId, userService.getUserId(authentication));
    }
}
