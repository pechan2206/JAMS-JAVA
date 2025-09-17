package com.example.web.jams.dto;

import java.math.BigDecimal;

public interface RecaudoPorPlanDTO {

    Long getIdSuscripcion();
    String getDescripcion();
    BigDecimal getTotalRecaudo();
}
