package fast_food_website.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentContent extends AbsEntity {


    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    @OneToOne(mappedBy = "attachmentContent")
    private Attachment attachment;
}
