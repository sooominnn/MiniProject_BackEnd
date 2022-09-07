package com.example.roomate.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.roomate.dto.response.*;
import com.example.roomate.entity.Comment;
import com.example.roomate.entity.Post;
import com.example.roomate.repository.CommentHeartRepository;
import com.example.roomate.repository.CommentRepository;
import com.example.roomate.repository.PostHeartRepository;
import com.example.roomate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostHeartRepository postHeartRepository;
    private final CommentHeartRepository commentHeartRepository;


    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByPostAndComment(post, null);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .member(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .heartNum(commentHeartRepository.countAllByCommentId(comment.getId()))
                            .build()
            );
        }

        Long heartNum = postHeartRepository.countAllByPostId(post.getId());


        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .member(post.getMember().getNickname())
                        .heartNum(heartNum)
                        .comments(commentResponseDtoList)
                        .build()
        );
    }
    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }


}
