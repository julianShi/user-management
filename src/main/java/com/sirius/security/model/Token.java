package com.sirius.security.model;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class Token {
    private String name;
    private OffsetDateTime tokenExpirationDate;
}
