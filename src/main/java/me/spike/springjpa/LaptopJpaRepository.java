package me.spike.springjpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LaptopJpaRepository extends JpaRepository<Laptop, UUID> {}
