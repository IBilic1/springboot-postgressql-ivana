package hr.algebra.ivanabilic.iisproject.repository;

import hr.algebra.ivanabilic.iisproject.entity.FileSkeleton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<FileSkeleton, String> {

}
