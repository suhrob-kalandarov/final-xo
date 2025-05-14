package org.exp.application.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.application.models.basekeys.BaseDate;
import org.exp.application.models.entity.message.Language;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tg_users")
public class TgUser extends BaseDate {

    @Id
    private Long id;

    private String fullname;
    private String username;

    @Column(name = "message_id")
    private Integer messageId;

    private boolean _active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;
}
