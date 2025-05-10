package org.exp.application.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.application.models.basekeys.BaseEntity;
import org.exp.application.models.extra.BotLanguage;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tg_users")
public class TgUser extends BaseEntity {

    private String fullname;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private BotLanguage language;
}
