package pl.szyszlak.mamarek123.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szyszlak.mamarek123.persistence.entity.BlogPost;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, Long> {
}