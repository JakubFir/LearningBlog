# Learning Blog
Learning blog is an invidual project of an online blog that I used to
learn and consolidate knowledge from a course.

#### Admin view:
* https://i.imgur.com/VkOdNaQ.png
* https://i.imgur.com/gZnoIhu.png
### User view:
* https://i.imgur.com/QIWru7a.png
#### Not looged in:
* https://i.imgur.com/KHgghwb.png
## Pages:
* Contact page : https://i.imgur.com/wwqezUm.png
* About me : https://i.imgur.com/AymcuUY.png (in develop)

### Requirements

* JVM
* Docker
* PostgreSQL

### Launching the Project
To launch the project, follow these steps:

Create two databases:
* blogdatabase
* blogdatabasetests

Then:
* `mvn clean install`
* Navigate to the `\LearningBlog` directory.
* Run the following command to start Docker containers:

* `docker-compose up`

### Mappings:

### Add Anonymous Comment to Post
* Description: Adds an anonymous comment to a specific post.
* Request Body: AnonymousCommentDto (data transfer object containing comment details)
* Method: POST
* Path: `/api/v1/comments/anonymous/{postId}`
 
### Add Post
* Description: Creates a new blog post for a specific blog user.
* Request Body: PostDto (data transfer object containing post details)
* Method: POST
* Path: `/api/v1/blog/posts/{blogUserId}`

### Add Admin
* Description: Adds a new admin user to the blog system.
* Request Body: RegisterRequest (data transfer object containing admin user details)
* Method: POST
* Path: `/api/v1/blog/users/admin`

### Poll Anonymous Comments from Topic
* Description: Retrieves anonymous comments from a Kafka topic.
* Method: GET
* Path: `/api/v1/messages/comments`

### Get All Posts
* Description: Retrieves all blog posts.
* Method: GET
* Path: `/api/v1/blog/posts`

### Login
* Description: Authenticates a user and generates an access token.
* Request Body: AuthenticationRequest (data transfer object containing login credentials)
* Method: POST
* Path: `/api/v1/blog/login`

### Get Post Comments
* Description: Retrieves comments for a specific post.
* Method: GET
* Path: `/api/v1/comments/{postId}`

### Add Comment to Post
* Description: Adds a comment to a specific post by a user. 
* Request Body: CommentDto (data transfer object containing comment details)
* Method: POST
* Path: `/api/v1/comments/{postId}/{userId}`

### Translator
* Description: Performs translation for a specific ID.
* Method: POST
* Path: `/api/v1/translator/{id}`

### Add User
* Description: Adds a new user to the blog system. 
* Request Body: RegisterRequest (data transfer object containing user details)
* Method: POST
* Path: `/api/v1/blog/users`

### Get Translated Posts
* Description: Retrieves translated posts.
* Method: GET
* Path: `/api/v1/translator`

### Send Mail
* Description: Sends an email using the provided mail details.
* Request Body: Mail (data transfer object containing mail details)
* Method: POST
* Path: `/api/v1/blog/contact`

### Poll Logs from Topic
* Description: Retrieves logs from a Kafka topic.
* Method: GET
* Path: `/api/v1/messages/logs`

### Get Comments
* Description: Retrieves all comments.
* Method: GET
* Path: `/api/v1/comments`

### Find All Comments to Approve
* Description: Retrieves all anonymous comments to approve.
* Method: GET
* Path: `/api/v1/comments/anonymous`

### Update Post
* Description: Updates a specific post with new details.
* Request Body: PostDto (data transfer object containing updated post details)
* Method: PUT
* Path: `/api/v1/blog/posts/{postId}`

### Get Blog Users
* Description: Retrieves all blog users.
* Method: GET
* Path: `/api/v1/blog/users`

### Delete Post
* Description: Deletes a specific post.
* Method: DELETE
* Path: `/api/v1/blog/posts/{postId}`

### Delete User
* Description: Deletes a specific user.
* Method: DELETE
* Path: `/api/v1/blog/users/{userId}`

### Send Anonymous Comment to Kafka
* Request Body: AnonymousCommentDto (data transfer object containing the anonymous comment)
* Method: POST
* Path: `/api/v1/messages/comments/{id}`

### Update Blog User Role
* Description: Updates the role of a specific blog user.
* Request Body: BlogUserDto (data transfer object containing updated user details)
* Method: PUT
* Path:`/api/v1/blog/users/{userId}`

### Get Post
* Description: Retrieves a specific post.
* Method: GET
* Path: `/api/v1/blog/posts/{postId}`

### Send Logs to Kafka
* Description: Sends logs to a Kafka topic.
* Method: POST
* Path: `/api/v1/messages/logs`
