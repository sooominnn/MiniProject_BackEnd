package com.example.roomate.service;

import com.example.roomate.dto.request.PostRequestDto;
import com.example.roomate.dto.response.CommentResponseDto;
import com.example.roomate.dto.response.GetAllPostResponseDto;
import com.example.roomate.dto.response.PostResponseDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.entity.Comment;
import com.example.roomate.entity.Member;
import com.example.roomate.entity.Post;
import com.example.roomate.entity.PostHeart;
import com.example.roomate.jwt.TokenProvider;
import com.example.roomate.repository.CommentHeartRepository;
import com.example.roomate.repository.CommentRepository;
import com.example.roomate.repository.PostHeartRepository;
import com.example.roomate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final TokenProvider tokenProvider;

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

//        List<Comment> commentList = commentRepository.findAllByPostAndComment(post, null);
        List<Comment> commentList = post.getComments();
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
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .member(post.getMember().getNickname())
                        .heartNum(heartNum)
                        .comments(commentResponseDtoList)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost() {
        Member member = tokenProvider.getMemberFromAuthentication();
        List<Post> allPosts = postRepository.findAll();
        List<GetAllPostResponseDto> getAllPostResponseDtoList = new ArrayList<>();

        if ( null != member) {

            for (Post post : allPosts) {
                boolean check = false;
                PostHeart findPostHeart = postHeartRepository.findByPostIdAndMemberId(post.getId(), member.getId());
                if ( null != findPostHeart ) {
                    check = false;
                }
                else { check = true;}
                getAllPostResponseDtoList.add(
                        GetAllPostResponseDto.builder()
                                .id(post.getId())
                                .heartOn(check)
                                .heartNum(postHeartRepository.countAllByPostId(post.getId()))
//                                .title(post.getTitle())
//                                .imageUrl((post.getImageUrl()))
                                .build()
                );
            }

        }

        else {

            for (Post post : allPosts) {
                getAllPostResponseDtoList.add(
                        GetAllPostResponseDto.builder()
                                .id(post.getId())
//                                .title(post.getTitle())
//                                .imageUrl(post.getImageUrl())
                                .heartNum(postHeartRepository.countAllByPostId(post.getId()))
                                .build()
                );
            }
        }
        return ResponseDto.success(getAllPostResponseDtoList);
    }


    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }


    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto) {
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }

        Post post = Post.builder()
                .title(requestDto.getTitle())
//                .content(requestDto.getContent())
//                .imageUrl(requestDto.getImageUrl())
                .build();
        postRepository.save(post);

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .imageUrl(post.getImageUrl())
                        .heartNum(post.getHeartNum())
                        .build()
        );
    }

}