package org.exp.application.models.entity.message;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.exp.application.models.basekeys.BaseEntity;
import org.exp.application.models.entity.session.SessionMenu;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "translations")
public class Translation extends BaseEntity {

    private String key;

    @Column(columnDefinition = "TEXT")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu")
    private SessionMenu menu;

    @ManyToOne
    private Language language;
}
