package com.samir.uberweal.application.queries;

import com.samir.uberweal.domain.entities.Rider;

public record PreAuthorizeQuery(Rider rider, double amount) {
}
