package org.exp.application.models.entity.extra;

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
public class BotLanguage extends BaseEntity {
    private String country;
    private String flag;
    private String name;

    @Column(name = "lang_code")
    private String langCode;
}
