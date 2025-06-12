package com._5.content_manager.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface ReportService {
    // Reporte 1: Cantidad de publicaciones por usuario
    List<Map> getPostsByUserReport();
    // Reporte 2: Publicaciones con más comentarios
    List<Map> getTopCommentedPostsReport();
    // Reporte 3: Publicaciones filtradas por tag y fecha
    List<Map> getPostsByTagAndDateReport(List<String> tags, LocalDateTime startDate, LocalDateTime endDate);
    // Reporte 4: Promedio de publicaciones por mes
    List<Map> getMonthlyPostsAverageReport();
    // Reporte 5: Análisis de comentarios por publicación
    List<Map> getCommentsAnalysisReport();
    // Reporte adicional: Dashboard general
    Map<String, Object> getDashboardStats();

}
