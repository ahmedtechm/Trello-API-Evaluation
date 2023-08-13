package com.codeline.trelloboards.Controller;

import com.codeline.trelloboards.Models.Board;
import com.codeline.trelloboards.Models.Card;
import com.codeline.trelloboards.RequestObjects.CardCreateRequest;
import com.codeline.trelloboards.RequestObjects.CardUpdateRequest;
import com.codeline.trelloboards.ResponseObjects.CardResponse;
import com.codeline.trelloboards.Service.BoardService;
import com.codeline.trelloboards.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards/{boardId}/cards")
@CrossOrigin("*")
public class CardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CardService cardService;

    // Endpoint to create a new card
    @PostMapping
    public ResponseEntity<CardResponse> createCard(
            @PathVariable Long boardId,
            @RequestBody CardCreateRequest request) {
        try {
            Board board = boardService.getBoardById(boardId);
            if (board == null) {
                return ResponseEntity.notFound().build();
            }

            Card newCard = new Card();
            newCard.setTitle(request.getTitle());
            newCard.setDescription(request.getDescription());
            newCard.setSection(request.getSection());
            newCard.setBoard(board);

            newCard = cardService.createCard(newCard);

            CardResponse response = new CardResponse();
            response.setCard_id(newCard.getId());
            response.setTitle(newCard.getTitle());
            response.setDescription(newCard.getDescription());
            response.setSection(newCard.getSection());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to get all cards from a specific board
    @GetMapping
    public ResponseEntity<List<CardResponse>> getAllCards(@PathVariable Long boardId) {
        try {
            List<Card> cards = cardService.getAllCardsFromBoard(boardId);
            List<CardResponse> cardResponses = cards.stream()
                    .map(card -> {
                        CardResponse response = new CardResponse();
                        response.setCard_id(card.getId());
                        response.setTitle(card.getTitle());
                        response.setDescription(card.getDescription());
                        response.setSection(card.getSection());
                        return response;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(cardResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to get details of a specific card
    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> getCard(
            @PathVariable Long boardId,
            @PathVariable Long cardId) {
        try {
            Card card = cardService.getCardByIdAndBoardId(cardId, boardId);
            if (card == null) {
                return ResponseEntity.notFound().build();
            }

            CardResponse response = new CardResponse();
            response.setCard_id(card.getId());
            response.setTitle(card.getTitle());
            response.setDescription(card.getDescription());
            response.setSection(card.getSection());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to update the details of a specific card
    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponse> updateCard(
            @PathVariable Long boardId,
            @PathVariable Long cardId,
            @RequestBody CardUpdateRequest request) {
        try {
            Card card = cardService.getCardByIdAndBoardId(cardId, boardId);
            if (card == null) {
                return ResponseEntity.notFound().build();
            }

            card.setTitle(request.getTitle());
            card.setDescription(request.getDescription());
            card.setSection(request.getSection());

            cardService.updateCard(card);

            CardResponse response = new CardResponse();
            response.setCard_id(card.getId());
            response.setTitle(card.getTitle());
            response.setDescription(card.getDescription());
            response.setSection(card.getSection());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to delete a specific card
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(
            @PathVariable Long boardId,
            @PathVariable Long cardId) {
        try {
            if (cardService.deleteCard(cardId)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
