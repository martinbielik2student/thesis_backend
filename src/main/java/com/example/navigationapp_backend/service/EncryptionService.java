package com.example.navigationapp_backend.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class EncryptionService {

    public Map<String,String> encryptPassword(String password){
        ByteSource byteSourceSalt= getSalt();
        String hashedPassword=hashAndSaltPassword(password,byteSourceSalt);
        Map<String,String> map = new HashMap<>();
        map.put("hashedPassword",hashedPassword);
        map.put("salt",byteSourceSalt.toHex());
        return map;
    }

    private ByteSource getSalt(){
        return new SecureRandomNumberGenerator().nextBytes();
    }

    private String hashAndSaltPassword(String plainPassword,ByteSource salt){
        return new Sha512Hash(plainPassword,salt,2000000).toHex();
    }

    public boolean checkPasswordMatch(String plainPassword,String hashedPassword,String stringSalt){
        ByteSource salt = ByteSource.Util.bytes(Hex.decode(stringSalt));
        String hashedLoginPassword = hashAndSaltPassword(plainPassword,salt);
        return hashedLoginPassword.equals(hashedPassword);
    }

    public String generateToken(Long aisId, UriInfo uriInfo) {
        Key key = generateKey(aisId.toString());
        String token = Jwts.builder().setSubject(aisId.toString()).setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date()).setExpiration(convertToDateViaSqlTimestamp(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512,key).setAudience(uriInfo.getBaseUri().toString()).compact();
        return token;
    }

    public Key generateKey(String unifier){
        return new SecretKeySpec(unifier.getBytes(),0,unifier.getBytes().length,"DES");
    }

    private Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
