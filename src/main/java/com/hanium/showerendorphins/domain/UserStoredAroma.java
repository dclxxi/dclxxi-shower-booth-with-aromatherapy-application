package com.hanium.showerendorphins.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "user_stored_aroma")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class UserStoredAroma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aroma_id")
    private Aroma aroma;

    @Builder
    public UserStoredAroma(Integer id, User user, Aroma aroma) {
        this.id = id;
        this.user = user;
        this.aroma = aroma;
    }

    public UserStoredAroma() {

    }
}
