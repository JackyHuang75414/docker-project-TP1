package fr.takima.training.simpleapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.takima.training.simpleapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}