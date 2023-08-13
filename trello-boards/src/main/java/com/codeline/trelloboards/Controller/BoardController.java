package com.codeline.trelloboards.Controller;

import com.codeline.trelloboards.Models.Board;
import com.codeline.trelloboards.RequestObjects.BoardCreateRequest;
import com.codeline.trelloboards.RequestObjects.BoardUpdateRequest;
import com.codeline.trelloboards.ResponseObjects.BoardResponse;
import com.codeline.trelloboards.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin("*")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // Endpoint to create a new board
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardCreateRequest request) {
        try {
            Board newBoard = new Board();
            newBoard.setTitle(request.getTitle());
            newBoard = boardService.createBoard(newBoard);

            BoardResponse response = new BoardResponse();
            response.setBoardId(newBoard.getId());
            response.setName(newBoard.getTitle());
            response.setColumns(createDefaultColumns());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle any exceptions here and return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to retrieve all boards
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        try {
            List<BoardResponse> boardResponses = boardService.getAllBoards()
                    .stream()
                    .map(board -> {
                        BoardResponse response = new BoardResponse();
                        response.setBoardId(board.getId());
                        response.setName(board.getTitle());
                        response.setColumns(createDefaultColumns());
                        return response;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(boardResponses);
        } catch (Exception e) {
            // Handle any exceptions here and return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to update an existing board
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateRequest request) {
        try {
            Optional<Board> optionalBoard = Optional.ofNullable(boardService.getBoardById(boardId));
            if (!optionalBoard.isPresent()) {
                // Return a not found response if the board doesn't exist
                return ResponseEntity.notFound().build();
            }

            Board board = optionalBoard.get();
            board.setTitle(request.getTitle());

            boardService.updateBoard(board); // Assuming an updateBoard method

            BoardResponse response = new BoardResponse();
            response.setBoardId(board.getId());
            response.setName(board.getTitle());
            response.setColumns(createDefaultColumns());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle any exceptions here and return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to delete a board
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable Long boardId) {
        try {
            Optional<Board> optionalBoard = Optional.ofNullable(boardService.getBoardById(boardId));
            if (!optionalBoard.isPresent()) {
                // Return a not found response if the board doesn't exist
                return ResponseEntity.notFound().build();
            }

            boardService.deleteBoard(boardId);

            Map<String, Object> response = new HashMap<>();
            response.put("successful", true);
            response.put("message", "Board with ID " + boardId + " has been deleted successfully.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle any exceptions here and return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Helper method to create default columns for a board
    private Map<Integer, String> createDefaultColumns() {
        Map<Integer, String> columns = new HashMap<>();
        columns.put(1, "To Do");
        columns.put(2, "In Progress");
        columns.put(3, "Done");
        return columns;
    }
}


