package com._5.content_manager.services.impl;
import com._5.content_manager.dtos.*;
import com._5.content_manager.entities.Post;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import java.time.ZoneOffset;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com._5.content_manager.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UserPostCountDTO> getPostsByUserReport() {
        //1. Etapa de Agrupación
        GroupOperation groupOperation = Aggregation.group("author.userId")
                .first("author.displayName").as("displayName")
                .count().as("postCount");

        //2. Etapa de Ordenamiento
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "postCount");

        //3. Etapa de Proyección
        ProjectionOperation projectionOperation = Aggregation.project("postCount", "displayName")
                .and("_id").as("userId");

        LimitOperation limitOperation = Aggregation.limit(10);

        //4. Construir el pipeline
        Aggregation aggregation = Aggregation.newAggregation(groupOperation, limitOperation, sortOperation, projectionOperation);

        //5. Ejecutamos la agregación
        AggregationResults<UserPostCountDTO> results = mongoTemplate.aggregate(
                aggregation, "posts", UserPostCountDTO.class
        );

        return results.getMappedResults();
    }

    @Override
    public List<TopCommentedPostDTO> getTopCommentedPostsReport() {
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "stats.commentsCount");
        LimitOperation limitOperation = Aggregation.limit(10);
        ProjectionOperation projectionOperation = Aggregation.project("title")
                .and("_id").as("postId")
                .and("stats.commentsCount").as("commentsCount")
                .and("author.displayName").as("authorName");

        Aggregation aggregation = Aggregation.newAggregation(sortOperation, limitOperation, projectionOperation);

        AggregationResults<TopCommentedPostDTO> results = mongoTemplate.aggregate(
                aggregation, "posts", TopCommentedPostDTO.class
        );

        return results.getMappedResults();
    }

    @Override
    public AnnualContentReportDTO getMonthlyPostsAverageReport() {
        // Etapa 1: Filtrar publicaciones "published" en el último año
        MatchOperation match = Aggregation.match(
                Criteria.where("status").is("published")
                        .and("publishedAt").gte(LocalDateTime.now().minusYears(1))
        );

        // Etapa 2: Proyectar año y mes como campos nuevos
        ProjectionOperation projectDateParts = Aggregation.project("author.userId", "stats.viewsCount", "publishedAt")
                .and(DateOperators.Year.yearOf("publishedAt")).as("year")
                .and(DateOperators.Month.monthOf("publishedAt")).as("month");

        // Etapa 3: Agrupar por año y mes
        GroupOperation groupByMonth = Aggregation.group("year", "month")
                .count().as("postsCount")
                .addToSet("userId").as("authors")
                .sum("viewsCount").as("totalViews")
                .avg("viewsCount").as("avgViewsPerPost");

        // Etapa 4: Calcular cantidad de autores únicos
        AddFieldsOperation addUniqueAuthors = Aggregation.addFields()
                .addField("uniqueAuthorsCount")
                .withValue(ArrayOperators.Size.lengthOfArray("authors"))
                .build();

        // Etapa 5: Crear campo 'period' y proyectar datos para MonthlyStatDTO
        ProjectionOperation preparePeriod = Aggregation.project()
                .and(
                        ConditionalOperators.Cond
                                .when(Criteria.where("_id.month").lt(10))
                                .then(
                                        StringOperators.Concat.valueOf(
                                                        AggregationExpression.from(MongoExpression.create("{ $toString: \"$_id.year\" }"))
                                                ).concat("-0")
                                                .concatValueOf(
                                                        AggregationExpression.from(MongoExpression.create("{ $toString: \"$_id.month\" }"))
                                                )
                                )
                                .otherwise(
                                        StringOperators.Concat.valueOf(
                                                        AggregationExpression.from(MongoExpression.create("{ $toString: \"$_id.year\" }"))
                                                ).concat("-")
                                                .concatValueOf(
                                                        AggregationExpression.from(MongoExpression.create("{ $toString: \"$_id.month\" }"))
                                                )
                                )
                ).as("period")
                .and("postsCount").as("postsCount")
                .and("uniqueAuthorsCount").as("uniqueAuthors")
                .and("totalViews").as("totalViews");

        // Ordenar por periodo
        SortOperation sortByPeriod = Aggregation.sort(Sort.by("period").descending());

        // Etapa 6: Agrupar todo en el reporte anual
        GroupOperation finalGroup = Aggregation.group()
                .push("$$ROOT").as("monthlyStats")
                .avg("postsCount").as("avgPostsPerMonth")
                .avg("uniqueAuthors").as("avgAuthorsPerMonth");

        Aggregation aggregation = Aggregation.newAggregation(
                match,
                projectDateParts,
                groupByMonth,
                addUniqueAuthors,
                preparePeriod,
                sortByPeriod,
                finalGroup
        );

        AggregationResults<AnnualContentReportDTO> results = mongoTemplate.aggregate(
                aggregation,
                "posts",
                AnnualContentReportDTO.class
        );

        return results.getUniqueMappedResult();
    }

    @Override
    public List<PostEngagementDTO> getPostEngagementData() {
        MatchOperation matchStage = Aggregation.match(Criteria.where("status").is("published"));
        SortOperation sortStage = Aggregation.sort(Sort.Direction.DESC, "publishedAt");
        LimitOperation limitStage = Aggregation.limit(200);

        ProjectionOperation projectionStage = Aggregation.project("title", "slug")
                .and("stats.likesCount").as("likes")
                .and("stats.commentsCount").as("comments");

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                sortStage,
                limitStage,
                projectionStage
        );

        AggregationResults<PostEngagementDTO> results = mongoTemplate.aggregate(
                aggregation, "posts", PostEngagementDTO.class
        );

        return results.getMappedResults();
    }

    @Override
    public DashboardDTO getDashboardStats() {
        long totalUsers = mongoTemplate.count(new Query(), "users");
        long totalPosts = mongoTemplate.count(new Query(), "posts");
        long totalComments = mongoTemplate.count(new Query(), "comments");

        double avgCommentsPerPost = (totalPosts == 0) ? 0.0 : (double) totalComments / totalPosts;

        Query recentPostsQuery = new Query()
                .with(Sort.by(Sort.Direction.DESC, "publishedAt"))
                .limit(5);

        List<Post> recentPosts = mongoTemplate.find(recentPostsQuery, Post.class, "posts");

        return new DashboardDTO(
                totalUsers,
                totalPosts,
                totalComments,
                avgCommentsPerPost,
                recentPosts
        );
    }
}
