package com.samir.uberweal.application.queries;

import com.samir.uberweal.core.domain.entities.Rider;

public record PreAuthorizeQuery(Rider rider, double amount) {
}
