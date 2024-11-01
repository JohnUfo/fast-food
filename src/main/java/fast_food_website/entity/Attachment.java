package fast_food_website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsEntity {

    @Column(nullable = false)
    private String fileOriginalName;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private AttachmentContent attachmentContent;

}
