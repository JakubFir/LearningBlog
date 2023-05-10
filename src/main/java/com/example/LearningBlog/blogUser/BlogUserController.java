package com.example.LearningBlog.blogUser;


import com.example.LearningBlog.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/blog/users")
@AllArgsConstructor
@EnableAspectJAutoProxy
public class BlogUserController {
    private final BlogUserService blogUserService;
    private final BlogUserDtoMapper blogUserDtoMapper;
    private final JwtService jwtService;
    @GetMapping
    public List<BlogUserDto> getBlogUsers() {
        return blogUserService.getAllBlogUsers().stream().map(blogUserDtoMapper::mapBlogUserToBlogUserDto).collect(Collectors.toList());
    }
    @PutMapping(path = "{userId}")
    public void updateBlogUserRole(@RequestBody BlogUserDto blogUserDto, @PathVariable Long userId) {
        BlogUser blogUserToUpdate = blogUserService.getBlogUser(userId);
        blogUserToUpdate.setRole(blogUserDto.getRole());
        blogUserService.addBlogUser(blogUserToUpdate);
    }
    @PostMapping(path = "/admin")
    public ResponseEntity<?> addAdmin(@RequestBody RegisterRequest request) {
        blogUserService.registerAdmin(request);
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest request) {
        blogUserService.registerUser(request);
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }
@DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable Long userId){
        blogUserService.deleteUser(userId);
    }
}



