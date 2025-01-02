package com.vou.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vou.backend.user.model.Gift;

import java.util.Optional;

public interface GiftLinkRepository extends JpaRepository<Gift, Long> {
    Optional<Gift> findByToken(String token);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM Gift g WHERE g.senderId = :userId AND g.type = :platform")
    boolean existsByUserIdAndPlatform(Long userId, String platform);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM Gift g WHERE g.token = :token")
	boolean existsByToken(String token);
}
