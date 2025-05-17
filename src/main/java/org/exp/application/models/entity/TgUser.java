package org.exp.application.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.exp.application.models.basekeys.BaseDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bot_users")
public class TgUser extends BaseDate {
    @Id
    private Long id;

    private String fullname;
    private String username;

    private boolean _active;

    private String langCode;
}
