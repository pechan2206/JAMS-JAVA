package com.example.web.jams.repositorios;

import com.example.web.jams.modelos.SuscripcionModelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.web.jams.dto.*;

@Repository
public interface SuscripcionRepositorio extends JpaRepository<SuscripcionModelo, Long> {

    @Query(value = """
            SELECT
                COUNT(su.fk_id_usuario) AS totalUsuarios
            FROM suscripcion s
            LEFT JOIN suscripcion_usuario su ON su.fk_id_suscripcion = s.id_suscripcion
            GROUP BY s.id_suscripcion, s.descripcion
            """, nativeQuery = true)
    List<UsuariosPorPlanDTO> contarUsuariosPorPlan();

    @Query(value = """
            SELECT
                s.id_suscripcion AS idSuscripcion,
                s.descripcion AS descripcion,
                SUM(s.precio) AS totalRecaudo
            FROM suscripcion s
            LEFT JOIN suscripcion_usuario su ON su.fk_id_suscripcion = s.id_suscripcion
            GROUP BY s.id_suscripcion, s.descripcion
            """, nativeQuery = true)
    List<RecaudoPorPlanDTO> recaudoPorPlan();

    @Query("SELECT s.descripcion FROM SuscripcionModelo s")
    List<String> findAllDescripciones();

}
