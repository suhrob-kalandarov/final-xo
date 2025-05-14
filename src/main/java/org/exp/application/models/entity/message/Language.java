package org.exp.application.models.entity.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.exp.application.models.basekeys.BaseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bot_languages")
public class Language extends BaseEntity {
    private String flag;
    private String country;
    private String language;

    @Column(name = "code")
    private String code;
}
