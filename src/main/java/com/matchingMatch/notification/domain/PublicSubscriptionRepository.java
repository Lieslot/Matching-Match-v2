package com.matchingMatch.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicSubscriptionRepository extends JpaRepository<PublicSubscription, Long> {

    Optional<PublicSubscription> findByUserId(Long teamId);

}
