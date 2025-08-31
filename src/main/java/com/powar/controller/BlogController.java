package com.powar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin(origins = "*")
public class BlogController {
    
    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
    
    /**
     * Get all blog posts
     */
    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getAllPosts(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "20") int limit,
                                                          @RequestParam(required = false) String search) {
        try {
            logger.info("Fetching blog posts - page: {}, limit: {}, search: {}", page, limit, search);
            
            // Mock data for now
            List<Map<String, Object>> posts = new ArrayList<>();
            Map<String, Object> post = new HashMap<>();
            post.put("id", 1);
            post.put("user_id", 1);
            post.put("title", "Sample Blog Post");
            post.put("content", "This is a sample blog post content.");
            post.put("image_url", null);
            post.put("tags", "sample,blog");
            post.put("status", "PUBLISHED");
            post.put("view_count", 0);
            post.put("like_count", 0);
            post.put("comment_count", 0);
            post.put("created_at", "2024-01-01T00:00:00Z");
            post.put("updated_at", "2024-01-01T00:00:00Z");
            post.put("email_id", "user@example.com");
            post.put("first_name", "John");
            post.put("last_name", "Doe");
            post.put("author_profile_url", null);
            posts.add(post);
            
            Map<String, Object> pagination = new HashMap<>();
            pagination.put("page", page);
            pagination.put("limit", limit);
            pagination.put("total", 1);
            pagination.put("pages", 1);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Blog posts fetched successfully");
            response.put("data", posts);
            response.put("pagination", pagination);
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched {} blog posts", posts.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching blog posts", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch blog posts: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Get blog post by ID
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable Long id) {
        try {
            logger.info("Fetching blog post with ID: {}", id);
            
            // Mock data for now
            Map<String, Object> post = new HashMap<>();
            post.put("id", id);
            post.put("user_id", 1);
            post.put("title", "Sample Blog Post");
            post.put("content", "This is a sample blog post content.");
            post.put("image_url", null);
            post.put("tags", "sample,blog");
            post.put("status", "PUBLISHED");
            post.put("view_count", 0);
            post.put("like_count", 0);
            post.put("comment_count", 0);
            post.put("created_at", "2024-01-01T00:00:00Z");
            post.put("updated_at", "2024-01-01T00:00:00Z");
            post.put("email_id", "user@example.com");
            post.put("first_name", "John");
            post.put("last_name", "Doe");
            post.put("author_profile_url", null);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Blog post fetched successfully");
            response.put("data", post);
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched blog post with ID: {}", id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching blog post with ID: {}", id, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch blog post: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Get posts by user ID
     */
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<Map<String, Object>> getPostsByUser(@PathVariable Long userId,
                                                            @RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "20") int limit) {
        try {
            logger.info("Fetching blog posts for user ID: {} - page: {}, limit: {}", userId, page, limit);
            
            // Mock data for now
            List<Map<String, Object>> posts = new ArrayList<>();
            Map<String, Object> post = new HashMap<>();
            post.put("id", 1);
            post.put("user_id", userId);
            post.put("title", "Sample Blog Post");
            post.put("content", "This is a sample blog post content.");
            post.put("image_url", null);
            post.put("tags", "sample,blog");
            post.put("status", "PUBLISHED");
            post.put("view_count", 0);
            post.put("like_count", 0);
            post.put("comment_count", 0);
            post.put("created_at", "2024-01-01T00:00:00Z");
            post.put("updated_at", "2024-01-01T00:00:00Z");
            post.put("email_id", "user@example.com");
            post.put("first_name", "John");
            post.put("last_name", "Doe");
            post.put("author_profile_url", null);
            posts.add(post);
            
            Map<String, Object> pagination = new HashMap<>();
            pagination.put("page", page);
            pagination.put("limit", limit);
            pagination.put("total", 1);
            pagination.put("pages", 1);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Blog posts fetched successfully");
            response.put("data", posts);
            response.put("pagination", pagination);
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched {} blog posts for user {}", posts.size(), userId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching blog posts for user ID: {}", userId, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch blog posts: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Create new blog post
     */
    @PostMapping("/posts")
    public ResponseEntity<Map<String, Object>> createPost(@RequestHeader("Authorization") String authHeader,
                                                        @RequestBody Map<String, Object> postData) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Creating new blog post");
            
            // Mock response for now
            Map<String, Object> createdPost = new HashMap<>(postData);
            createdPost.put("id", 1);
            createdPost.put("user_id", 1);
            createdPost.put("status", "PUBLISHED");
            createdPost.put("view_count", 0);
            createdPost.put("like_count", 0);
            createdPost.put("comment_count", 0);
            createdPost.put("created_at", "2024-01-01T00:00:00Z");
            createdPost.put("updated_at", "2024-01-01T00:00:00Z");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Blog post created successfully");
            response.put("data", createdPost);
            response.put("source", "java-backend");
            
            logger.info("Blog post created successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error creating blog post", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to create blog post: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Update blog post
     */
    @PutMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id,
                                                        @RequestHeader("Authorization") String authHeader,
                                                        @RequestBody Map<String, Object> postData) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Updating blog post with ID: {}", id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Blog post updated successfully");
            response.put("source", "java-backend");
            
            logger.info("Blog post updated successfully with ID: {}", id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error updating blog post with ID: {}", id, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to update blog post: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Delete blog post
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long id,
                                                       @RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Deleting blog post with ID: {}", id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Blog post deleted successfully");
            response.put("source", "java-backend");
            
            logger.info("Blog post deleted successfully with ID: {}", id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error deleting blog post with ID: {}", id, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to delete blog post: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Search blog posts
     */
    @GetMapping("/posts/search")
    public ResponseEntity<Map<String, Object>> searchPosts(@RequestParam String q,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "20") int limit) {
        try {
            logger.info("Searching blog posts with query: '{}' - page: {}, limit: {}", q, page, limit);
            
            // Mock data for now
            List<Map<String, Object>> posts = new ArrayList<>();
            Map<String, Object> post = new HashMap<>();
            post.put("id", 1);
            post.put("user_id", 1);
            post.put("title", "Sample Blog Post");
            post.put("content", "This is a sample blog post content.");
            post.put("image_url", null);
            post.put("tags", "sample,blog");
            post.put("status", "PUBLISHED");
            post.put("view_count", 0);
            post.put("like_count", 0);
            post.put("comment_count", 0);
            post.put("created_at", "2024-01-01T00:00:00Z");
            post.put("updated_at", "2024-01-01T00:00:00Z");
            post.put("email_id", "user@example.com");
            post.put("first_name", "John");
            post.put("last_name", "Doe");
            post.put("author_profile_url", null);
            posts.add(post);
            
            Map<String, Object> pagination = new HashMap<>();
            pagination.put("page", page);
            pagination.put("limit", limit);
            pagination.put("total", 1);
            pagination.put("pages", 1);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Blog posts search completed");
            response.put("data", posts);
            response.put("searchQuery", q);
            response.put("pagination", pagination);
            response.put("source", "java-backend");
            
            logger.info("Successfully searched blog posts with query: '{}'", q);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error searching blog posts with query: '{}'", q, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to search blog posts: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
