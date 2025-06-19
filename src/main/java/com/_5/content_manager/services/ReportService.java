package com._5.content_manager.services;

import com._5.content_manager.dtos.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface ReportService {
    // Reporte 1: Cantidad de publicaciones por usuario
    List<UserPostCountDTO> getPostsByUserReport();
    // Reporte 2: Publicaciones con más comentarios
    List<TopCommentedPostDTO> getTopCommentedPostsReport();
    // Reporte 3: Promedio de publicaciones por mes
    AnnualContentReportDTO getMonthlyPostsAverageReport();
    // Reporte 4: Análisis de Interacción (Engagement) por Publicación.
    List<PostEngagementDTO> getPostEngagementData();

    // Reporte adicional: Dashboard general
    DashboardDTO getDashboardStats();


}
