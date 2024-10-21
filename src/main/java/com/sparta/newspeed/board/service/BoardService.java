package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.CreateBoardRequestDto;
import com.sparta.newspeed.board.dto.CreateBoardResponseDto;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
//    private final UserRepository userRepository;


    @Transactional
    public CreateBoardResponseDto createBoard(CreateBoardRequestDto reqDto) {
        // 임시 가짜 유저 엔티티 생성
        User user = new User();
        user.setId(1L);
        user.setUsername("하하하");
        user.setPassword("1234");
        user.setEmail("asdf@gmail.com");
        // 임시

        Board board = new Board(
                user,
                reqDto.getTitle(),
                reqDto.getContent()
        );
        boardRepository.save(board);

        return new CreateBoardResponseDto("201", "게시물 생성 완료", user.getId());
    }
}
