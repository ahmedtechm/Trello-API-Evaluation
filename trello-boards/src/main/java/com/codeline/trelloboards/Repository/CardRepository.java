package com.codeline.trelloboards.Repository;

import com.codeline.trelloboards.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    // This method retrieves a list of cards associated with a specific board ID.
    List<Card> findByBoardId(Long boardId);

}
