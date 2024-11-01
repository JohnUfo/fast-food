package fast_food_website.entity;

import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent extends AbsEntity {

    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    @OneToOne(mappedBy = "attachmentContent")
    private Attachment attachment;
}
