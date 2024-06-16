package org.theoliverlear.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Embeddable
public class SafePassword {
    @Column(name = "password")
    private String encodedPassword;
    @Transient
    @JsonIgnore
    private BCryptPasswordEncoder passwordEncoder;
    public SafePassword() {
        this.encodedPassword = "";
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public SafePassword(String unencodedPassword) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.encodedPassword = this.passwordEncoder.encode(unencodedPassword);
    }
    public String encodePassword(String unencodedPassword) {
        return this.passwordEncoder.encode(unencodedPassword);
    }
    public boolean compareUnencodedPassword(String unencodedPassword) {
        return this.passwordEncoder.matches(unencodedPassword, this.encodedPassword);
    }
}
