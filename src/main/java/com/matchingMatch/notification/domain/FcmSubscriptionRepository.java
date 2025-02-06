package com.matchingMatch.notification.domain;

import com.matchingMatch.notification.domain.entity.FcmSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmSubscriptionRepository extends JpaRepository<FcmSubscription, Long> {

    Optional<FcmSubscription> findByUserId(Long userId);
    List<FcmSubscription> findAllByUserId(Long userId);
    List<FcmSubscription> deleteAllByUserId(Long userId);
}
