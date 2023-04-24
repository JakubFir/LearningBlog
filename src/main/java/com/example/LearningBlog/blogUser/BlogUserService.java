package com.example.LearningBlog.blogUser;

import com.example.LearningBlog.errorHandler.UsernameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class BlogUserService {
    private final BlogUserRepository blogUserRepository;
    private final PasswordEncoder passwordEncoder;

    private final BlogUserDtoMapper blogUserDtoMapper;


    public void addBlogUser(BlogUser blogUser) {
        blogUserRepository.save(blogUser);
    }

    public BlogUser getBlogUser(Long id) {
        return blogUserRepository.findById(id).orElseThrow();
    }

    public List<BlogUserDto> getAllBlogUsers() {
        return blogUserRepository.findAll().stream().map(blogUserDtoMapper::mapBlogUserToBlogUserDto).collect(Collectors.toList());
    }

    public void registerUser(RegisterRequest request) {
        if (blogUserRepository.findBlogUsersByUsername(request.getUsername()).isPresent()) {
            throw new UsernameTakenException(request.getUsername() + " is taken");
        }
        BlogUser user = new BlogUser(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );
        addBlogUser(user);
    }

    public void registerAdmin(RegisterRequest request) {
        if (blogUserRepository.findBlogUsersByUsername(request.getUsername()).isPresent()) {
            throw new UsernameTakenException(request.getUsername() + " is taken");
        }
        BlogUser user = new BlogUser(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Role.ADMIN
        );
        addBlogUser(user);
    }

    public void deleteUser(Long userId) {
        blogUserRepository.deleteById(userId);
    }
}
