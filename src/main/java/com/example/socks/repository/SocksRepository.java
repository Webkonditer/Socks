package com.example.socks.repository;

import com.example.socks.model.Socks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocksRepository extends CrudRepository<Socks, Long> {

    Socks getSocksByColorAndCottonPart(String color, Integer cottonPart);

    @Query(value = "SELECT * FROM socks where color = ?1 AND cotton_part > ?2", nativeQuery = true)
    List<Socks> getSocksByColorAndMoreThenCottonPart(String color, Integer cottonPart);

    @Query(value = "SELECT * FROM socks where color = ?1 AND cotton_part < ?2", nativeQuery = true)
    List<Socks> getSocksByColorAndLessThenCottonPart(String color, Integer cottonPart);
}
