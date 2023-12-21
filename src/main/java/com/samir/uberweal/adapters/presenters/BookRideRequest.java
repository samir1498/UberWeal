package com.samir.uberweal.adapters.presenters;

public record BookRideRequest(Long riderId, String type, String startLocation, String endLocation) {
}
