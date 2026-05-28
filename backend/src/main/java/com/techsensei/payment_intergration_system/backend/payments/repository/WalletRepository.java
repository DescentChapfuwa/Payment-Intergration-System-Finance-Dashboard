package com.techsensei.payment_intergration_system.backend.payments.repository;


import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(""" 
           SELECT w 
           FROM Wallet w 
           WHERE w.user.id = :userId
          """)
    Optional<Wallet> findByUserIdForUpdate(@Param("userId") Long userId);

}
