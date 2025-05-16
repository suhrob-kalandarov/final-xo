package org.exp.application.models.entity.session;

import jakarta.persistence.*;
import lombok.*;
import org.exp.application.models.basekeys.BaseDate;
import org.exp.application.models.entity.TgUser;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_sessions")
public class UserSession extends BaseDate {
    @Id
    @Column(unique = true)
    private Long userId;

   /* @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private TgUser botUser;
*/
    @Column(name = "message_id")
    private Integer messageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_page")
    private SessionPage currentPage;

    @Column(name = "last_callback_data")
    private String lastCallbackData;

    private boolean _active;
}
