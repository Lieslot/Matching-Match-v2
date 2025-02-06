package com.matchingMatch.notification.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.notification.domain.entity.FcmSubscription;

public interface FcmSubscriptionRepository extends JpaRepository<FcmSubscription, Long> {

	Optional<FcmSubscription> findByUserId(Long userId);

	List<FcmSubscription> findAllByUserId(Long userId);

	List<FcmSubscription> deleteAllByUserId(Long userId);
}
