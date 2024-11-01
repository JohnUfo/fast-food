package fast_food_website.repository;

import fast_food_website.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Long> {

}
