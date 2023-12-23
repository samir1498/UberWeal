package com.samir.uberweal.application.query.queries;

import com.samir.uberweal.domain.entities.Rider;

public record PreAuthorizeQuery(Rider rider, double amount) {
}
