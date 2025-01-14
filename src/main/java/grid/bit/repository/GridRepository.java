package grid.bit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import grid.bit.model.Grid;

public interface GridRepository extends JpaRepository<Grid, Long> {
}