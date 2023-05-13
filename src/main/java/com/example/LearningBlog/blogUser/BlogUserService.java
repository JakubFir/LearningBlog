package com.example.LearningBlog.blogUser;

import com.example.LearningBlog.errorHandler.UsernameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class BlogUserService {
    private final BlogUserRepository blogUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void addBlogUser(BlogUser blogUser) {
        blogUserRepository.save(blogUser);
    }

    public BlogUser getBlogUser(Long id) {
        return blogUserRepository.findById(id).orElseThrow();
    }

    public List<BlogUser> getAllBlogUsers() {
        return blogUserRepository.findAll();
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

    public void updateUser(BlogUserDto blogUserDto, Long userId) {
       BlogUser userToUpdate = blogUserRepository.findById(userId).orElseThrow(() -> new UsernameTakenException("user not found"));
       userToUpdate.setRole(blogUserDto.getRole());
       blogUserRepository.save(userToUpdate);
    }
}
