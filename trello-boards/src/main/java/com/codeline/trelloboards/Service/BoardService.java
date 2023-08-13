package com.codeline.trelloboards.Service;

import com.codeline.trelloboards.Models.Board;
import com.codeline.trelloboards.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // Create a new board and save it in the database
    public Board createBoard(Board board) {
        try {
            return boardRepository.save(board);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create board.", e);
        }
    }

    // Retrieve a list of all boards from the database
    public List<Board> getAllBoards() {
        try {
            return boardRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve boards.", e);
        }
    }

    // Retrieve a board by its unique ID from the database
    public Board getBoardById(Long id) {
        try {
            Optional<Board> optionalBoard = boardRepository.findById(id);
            return optionalBoard.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve board by ID.", e);
        }
    }

    // Update an existing board's information in the database
    public void updateBoard(Board board) {
        try {
            boardRepository.save(board);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update board.", e);
        }
    }

    // Delete a board by its ID from the database
    public boolean deleteBoard(Long id) {
        try {
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                boardRepository.delete(optionalBoard.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete board.", e);
        }
    }
}



