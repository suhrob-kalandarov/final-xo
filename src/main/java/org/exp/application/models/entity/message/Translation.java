package org.exp.application.models.entity.message;

import jakarta.persistence.*;
import lombok.*;
import org.exp.application.models.basekeys.BaseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Translation extends BaseEntity {

    private String key;
    private String value;

    private String category;

    @ManyToOne
    private Language language;
}
