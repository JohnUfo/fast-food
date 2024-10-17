package fast_food_website.entity;

import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsEntity {

    private String fileOriginalName; // pdp.jpg, inn.pdf

    private long size; //2048000

    private String contentType; //application/pdf, image/png

    //To save file into system
    private String name; //Papkani ichidan topish uchun unique name
}
