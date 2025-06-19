package com._5.content_manager.services.impl;

import com._5.content_manager.dtos.FakeCommentDTO;
import com._5.content_manager.dtos.FakePhotoDTO;
import com._5.content_manager.dtos.FakePostDTO;
import com._5.content_manager.dtos.FakeVideoDTO;
import com._5.content_manager.entities.Comment;
import com._5.content_manager.entities.Post;
import com._5.content_manager.entities.User;
import com._5.content_manager.models.comment.CommentStats;
import com._5.content_manager.models.post.AuthorInfo;
import com._5.content_manager.models.post.Media;
import com._5.content_manager.models.post.PostStats;
import com._5.content_manager.repositories.CommentRepository;
import com._5.content_manager.services.CommentService;
import com._5.content_manager.services.DataGeneratorService;
import com._5.content_manager.services.PostService;
import com._5.content_manager.services.UserService;
import com._5.content_manager.utils.FechaUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataGeneratorServiceImpl implements DataGeneratorService {
    private final List<String> redesSociales = Arrays.asList("Twitter", "Linkedin", "Facebook", "GitHub");
    private final List<String> intereses = Arrays.asList(
            "technology",
            "programming",
            "traveling",
            "photography",
            "reading",
            "movies",
            "music",
            "science",
            "art",
            "entrepreneurship",
            "video games",
            "graphic design",
            "artificial intelligence",
            "history",
            "astronomy",
            "writing",
            "fashion",
            "sports",
            "languages",
            "cooking"
    );
    private final List<String> categories = Arrays.asList(
            "Technology",
            "Programming",
            "Travel",
            "Photography",
            "Science",
            "Art",
            "Business",
            "Education",
            "Health",
            "Lifestyle",
            "Finance",
            "Design",
            "Gaming",
            "Entertainment",
            "History"
    );
    private final List<String> tags = Arrays.asList(
            "Java",
            "Spring Boot",
            "Web Development",
            "AI",
            "Machine Learning",
            "Cloud Computing",
            "UI/UX",
            "Microservices",
            "Databases",
            "MongoDB",
            "React",
            "Angular",
            "Docker",
            "Kubernetes",
            "Cybersecurity",
            "Startup",
            "Remote Work",
            "Travel Tips",
            "Photography Gear",
            "Financial Planning"
    );

    private final Random random = new Random();

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @Override
    public List<User> generateUsers(List<User> users) {
        for (User user : users){
            int randomInt = random.nextInt(0, 50000);
            user.getProfile().setAvatarUrl("https://i.pravatar.cc/"+randomInt);
            user.getProfile().setBirthDate(FechaUtils.generarFechaEntre(LocalDateTime.of(1970, 01, 01, 01, 01), LocalDateTime.of(2006, 01, 01, 01, 01)));
            List<String> redesAleatorias = getListaAleatoria(redesSociales);
            user.setInterests(getListaAleatoria(intereses));
            for (String redSocial : redesAleatorias){
                user.getSocialLinks().put(redSocial, user.getUsername());
            }
            user.setCreatedAt(FechaUtils.generarFechaEntre(LocalDateTime.of(2020, 01, 01, 01, 01), LocalDateTime.now()));
            user.setLastLogin(FechaUtils.generarFechaEntre(user.getCreatedAt(), LocalDateTime.now()));
        }
        return userService.createMany(users);
    }

    @Override
    public List<Post> generatePosts() {
        List<FakePostDTO> fakePostDTOS = this.getFakePosts();
        List<User> users = userService.getAllActiveUsers();
        List<Post> posts = new ArrayList<>();
        Collections.shuffle(fakePostDTOS);

        for(FakePostDTO fakePost : fakePostDTOS){
            int rdmIndex = random.nextInt(users.size() - 1);
            User usuarioRandom = users.get(rdmIndex);
            Post newPost = new Post();

            newPost.setTitle(fakePost.getTitle());
            newPost.setContent(fakePost.getBody());
            newPost.setCategory(categories.get(random.nextInt(categories.size() - 1)));
            newPost.setTags(this.getListaAleatoria(tags));

            newPost.setPublishedAt(FechaUtils.generarFechaEntre(usuarioRandom.getCreatedAt(), LocalDateTime.now()));

            PostStats postStats = new PostStats();
            postStats.setViewsCount(random.nextInt(10000));
            postStats.setLikesCount(random.nextInt(1000));
            newPost.setStats(postStats);

            userService.incrementPostCounting(usuarioRandom.getId());

            if(Math.random() < 0.5){
                Media media = new Media();
                if(Math.random() < 0.5){
                    List<FakePhotoDTO> fakePhotos = this.getFakePhotos();
                    FakePhotoDTO fakePhoto = fakePhotos.get(random.nextInt(fakePhotos.size() - 1));
                    media.setUrl(fakePhoto.getSrc().get("large"));
                    media.setType("img");
                    media.setAlt(fakePhoto.getAlt());
                    newPost.setMedia(media);
                }else{
                    List<FakeVideoDTO> fakeVideos = this.getFakeVideos();
                    FakeVideoDTO fakeVideo = fakeVideos.get(random.nextInt(fakeVideos.size() - 1));
                    media.setUrl(fakeVideo.getVideo_files().get(1).getLink());
                    media.setType(fakeVideo.getVideo_files().get(1).getFile_type());
                    media.setDuration(fakeVideo.getDuration());
                    media.setQuality(fakeVideo.getVideo_files().get(1).getQuality());
                }
                newPost.setMedia(media);
            }

            posts.add(postService.createPost(newPost, usuarioRandom));

        }

        return posts;
    }

    @Override
    public List<Comment> generateComments() {
        List<Post> posts = postService.allPosts();
        List<FakeCommentDTO> fakeComments = getFakeComments();
        List<Comment> comments = new ArrayList<>();
        List<User> users = userService.getAllActiveUsers();
        Collections.shuffle(fakeComments);

        for (Post post : posts){
            if(Math.random() < 0.7){
                FakeCommentDTO rdmFakeComment = fakeComments.get(random.nextInt(fakeComments.size() - 1));
                User author = users.get(random.nextInt(users.size() - 1));

                Comment newComment = new Comment();
                newComment.setPostId(post.getId());
                newComment.setContent(rdmFakeComment.getBody());
                newComment.setAuthor(AuthorInfo.builder()
                        .userId(author.getId())
                        .username(author.getUsername())
                        .avatarUrl(author.getProfile().getAvatarUrl())
                        .displayName(author.getProfile() != null ?
                                author.getProfile().getFirstName() + " " + author.getProfile().getLastName() :
                                author.getUsername())
                        .build());
                CommentStats commentStats = new CommentStats();
                if(Math.random() < 0.5){
                    commentStats.setLikesCount(random.nextInt(50));
                    commentStats.setRepliesCount(random.nextInt(50));
                }
                newComment.setStats(commentStats);
                newComment.setCreatedAt(FechaUtils.generarFechaEntre(post.getPublishedAt(), LocalDateTime.now()));
                newComment.setUpdatedAt(newComment.getCreatedAt());

                postService.updatePostCommentCount(post);
                comments.add(newComment);
            }
        }
        return commentRepository.saveAll(comments);
    }

    private List<String> getListaAleatoria(List<String> lista){
        List<String> copia = new ArrayList<>(lista);
        Collections.shuffle(copia);
        int cant = 0;
        if(copia.size() > 10){
            cant = random.nextInt(10);
        }else{
            cant = random.nextInt(copia.size());
        }
        return copia.subList(0, cant);
    }

    private List<FakeVideoDTO> getFakeVideos(){
        TypeReference<List<FakeVideoDTO>> typeReference = new TypeReference<List<FakeVideoDTO>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/videos.json");
        List<FakeVideoDTO> fakeVideos = new ArrayList<>();
        try {
            fakeVideos = objectMapper.readValue(inputStream, typeReference);
            return fakeVideos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<FakePhotoDTO> getFakePhotos(){
        TypeReference<List<FakePhotoDTO>> typeReference = new TypeReference<List<FakePhotoDTO>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/photos.json");
        List<FakePhotoDTO> fakePhotos = new ArrayList<>();
        try {
            fakePhotos = objectMapper.readValue(inputStream, typeReference);
            return fakePhotos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<FakePostDTO> getFakePosts(){
        TypeReference<List<FakePostDTO>> typeReference = new TypeReference<List<FakePostDTO>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/fake-posts.json");
        List<FakePostDTO> fakePosts = new ArrayList<>();
        try {
            fakePosts = objectMapper.readValue(inputStream, typeReference);
            return fakePosts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<FakeCommentDTO> getFakeComments(){
        TypeReference<List<FakeCommentDTO>> typeReference = new TypeReference<List<FakeCommentDTO>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/fake-comments.json");
        List<FakeCommentDTO> fakeComments = new ArrayList<>();
        try {
            fakeComments = objectMapper.readValue(inputStream, typeReference);
            return fakeComments;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
