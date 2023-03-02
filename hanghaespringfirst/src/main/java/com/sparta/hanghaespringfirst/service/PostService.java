package com.sparta.hanghaespringfirst.service;

import com.sparta.hanghaespringfirst.dto.PostRequestDto;
import com.sparta.hanghaespringfirst.entity.Post;
import com.sparta.hanghaespringfirst.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;    //MemoRepository에 연결이 되어서 사용이 가능하다

    @Transactional      //저장
    public Post createPost(PostRequestDto requestDto) {
        Post memo = new Post(requestDto);
        this.postRepository.save(memo);
        return memo;
    }

    @Transactional      //출력
    public List<Post> getPosts() {
        return this.postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional      //수정및 출력
    public long update(Long id, PostRequestDto requestDto) {
        Post post = checkPost(id);
        if(post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
        }
        else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        return post.getId();
    }

    @Transactional      //삭제
    public Long deleteMemo(Long id, PostRequestDto postRequestDto) {
        Post post = checkPost(id);
        if(post.getPassword().equals(postRequestDto.getPassword())){
            postRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        return id;
    }

    @Transactional      //지정 출력
    public Post choicePost(Long id) {
        Post post = checkPost(id);
        return post;
    }

    private Post checkPost(Long id){        //중복코드
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
    }
}
