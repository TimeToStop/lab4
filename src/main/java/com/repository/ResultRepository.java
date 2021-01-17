package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.model.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> getAllByUserId(long id);
}
