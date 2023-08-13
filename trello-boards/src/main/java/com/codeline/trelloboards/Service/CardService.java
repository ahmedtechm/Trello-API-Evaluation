package com.codeline.trelloboards.Service;

import com.codeline.trelloboards.Models.Card;
import com.codeline.trelloboards.Repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    // Create a new card in the repository.
    public Card createCard(Card card) {
        try {
            return cardRepository.save(card);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating card: " + e.getMessage(), e);
        }
    }

    // Retrieve all cards associated with a specific board.
    public List<Card> getAllCardsFromBoard(Long boardId) {
        try {
            return cardRepository.findByBoardId(boardId);
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving cards from board: " + e.getMessage(), e);
        }
    }

    // Get a specific card by its ID and the ID of the associated board.
    public Card getCardByIdAndBoardId(Long cardId, Long boardId) {
        try {
            Optional<Card> optionalCard = cardRepository.findById(cardId);
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                // Check if the card belongs to the specified board.
                if (card.getBoard() != null && card.getBoard().getId().equals(boardId)) {
                    return card;
                }
            }
            return null; // Card not found or doesn't belong to the specified board.
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving card by ID and board ID: " + e.getMessage(), e);
        }
    }

    // Update an existing card's information.
    public void updateCard(Card card) {
        try {
            cardRepository.save(card);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating card: " + e.getMessage(), e);
        }
    }

    // Delete a card by its ID.
    public boolean deleteCard(Long cardId) {
        try {
            Optional<Card> optionalCard = cardRepository.findById(cardId);
            if (optionalCard.isPresent()) {
                cardRepository.delete(optionalCard.get());
                return true; // Card successfully deleted.
            }
            return false; // Card not found.
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting card: " + e.getMessage(), e);
        }
    }
}
