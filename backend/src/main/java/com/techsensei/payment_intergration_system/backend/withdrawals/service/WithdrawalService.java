package com.techsensei.payment_intergration_system.backend.withdrawals.service;

import java.util.List;

import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalRequest;
import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalResponse;

public interface WithdrawalService {

    WithdrawalResponse requestWithdrawal(String key,Long userId,WithdrawalRequest request);

    List<WithdrawalResponse> requestAllWithdrawals(Long userId);
}
