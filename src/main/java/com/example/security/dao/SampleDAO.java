package com.example.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.security.model.Sample;
import java.util.List;

@Repository
public interface SampleDAO extends JpaRepository<Sample, Integer> {
    Sample findSampleById(Integer id);
    List<Sample> findByEmployeeNameContainingIgnoreCase(String name);
}