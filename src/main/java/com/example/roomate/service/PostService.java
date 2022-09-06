package com.example.roomate.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import com.example.roomate.dto.response.CommentResponseDto;
import com.example.roomate.dto.response.PostResponseDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.entity.Comment;
import com.example.roomate.entity.Member;
import com.example.roomate.entity.Post;
import com.example.roomate.jwt.TokenProvider;
import com.example.roomate.repository.CommentHeartRepository;
import com.example.roomate.repository.CommentRepository;
import com.example.roomate.repository.PostHeartRepository;
import com.example.roomate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostHeartRepository postHeartRepository;
    private final CommentHeartRepository commentHeartRepository;

//    private final S3UploaderService s3UploaderService;
//    private final ImageRepository imageRepository;

    private final TokenProvider tokenProvider;

//    @Transactional
//    public ResponseDto<?> createPost(PostRequestDto requestDto, MultipartFile multipartFile, HttpServletRequest request) {
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        Member member = validateMember(request);
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        //AWS
//        String FileName = null;
//        if (multipartFile.isEmpty()) {
//            return ResponseDto.fail("INVALID_FILE", "파일이 유효하지 않습니다.");
//        }
//        ImageResponseDto imageResponseDto = null;
//        try {
//            FileName = s3UploaderService.uploadFile(multipartFile, "image");
//            imageResponseDto = new ImageResponseDto(FileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        assert imageResponseDto != null;
//        Post post = Post.builder()
//                .title(requestDto.getTitle())
//                .content(requestDto.getContent())
//                .comment_cnt(0)
//                .image(imageResponseDto.getImageUrl())
//                .member(member)
//                .build();
//        postRepository.save(post);
//
//        return ResponseDto.success(
//                PostResponseDto.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .member(post.getMember().getNickname())
//                        .imageUrl(post.getImage())
//                        .heartNum(post.getHeartNum())
//                        .comment_cnt(post.getComment_cnt())
//                        .build()
//        );
//    }

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
//    String imageUrl = post.getImage();

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImage())
                        .member(post.getMember().getNickname())
                        .heartNum(heartNum)
                        .comments(commentResponseDtoList)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost() {

        List<Post> allPosts = postRepository.findAllByOrderByModifiedAtDesc();
        List<GetAllPostResponseDto> getAllPostResponseDtoList = new ArrayList<>();

        for (Post post : allPosts) {
            getAllPostResponseDtoList.add(
                    GetAllPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .author(post.getMember().getNickname())
                            .heartNum(postHeartRepository.countAllByPostId(post.getId()))
                            .build()
            );
        }
        return ResponseDto.success(getAllPostResponseDtoList);
    }

//    @Transactional
//    public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto, MultipartFile multipartFile, HttpServletRequest request) {
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        Member member = validateMember(request);
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        Post post = isPresentPost(id);
//        if (null == post) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//        }
//
//        if (post.validateMember(member)) {
//            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
//        }
//
//        //AWS
//        String FileName = null;
//        if (multipartFile.isEmpty()) {
//            return ResponseDto.fail("INVALID_FILE", "파일이 유효하지 않습니다.");
//        }
//        ImageResponseDto imageResponseDto = null;
//        if (!multipartFile.isEmpty()) {
//            try {
//                FileName = s3UploaderService.uploadFile(multipartFile, "image");
//                imageResponseDto = new ImageResponseDto(FileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        assert imageResponseDto != null;
//        post.update(requestDto, imageResponseDto);
//
//        return ResponseDto.success(
//                PostResponseDto.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .imageUrl(post.getImage())
//                        .member(post.getMember().getNickname())
//                        .heartNum(postHeartRepository.countAllByPostId(post.getId()))
//                        .build()
//        );
//    }
//
//    @Transactional
//    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        Member member = validateMember(request);
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        Post post = isPresentPost(id);
//        if (null == post) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//        }
//
//        if (post.validateMember(member)) {
//            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
//        }
//
//        postRepository.delete(post);
//        return ResponseDto.success("delete success");
//    }
//
//    @Transactional(readOnly = true)
//    public Post isPresentPost(Long id) {
//        Optional<Post> optionalPost = postRepository.findById(id);
//        return optionalPost.orElse(null);
//    }
//

//    @Transactional
//    public Member validateMember(HttpServletRequest request) {
//        if (!tokenProvider.tokenValidation(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
//        return tokenProvider.getMemberFromAuthentication();
//    }

}
