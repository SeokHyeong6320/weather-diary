package zerobase.weather.repositpry;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.weather.domain.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    List<Diary> findAllByDate(LocalDate date);
    Optional<Diary> findFirstByDate(LocalDate date);
    void deleteAllByDate(LocalDate date);
}
