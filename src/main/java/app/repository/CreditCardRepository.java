package app.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long>{
	
	ArrayList<CreditCard> findByUser(Long user);
	

}
