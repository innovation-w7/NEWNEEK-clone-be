package com.innovation.newneekclone.repository;

import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Optional<Like> findByNewsAndUser(News news, User user);
  List<Like> findAllByUser(User user);
}

