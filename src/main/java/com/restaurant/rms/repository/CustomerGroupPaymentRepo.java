package com.restaurant.rms.repository;

import com.restaurant.rms.models.CustomerGroupPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerGroupPaymentRepo extends JpaRepository<CustomerGroupPayment, Integer> {
}
