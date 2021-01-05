package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long>{
	
	CreditCard findByUser(Long user);
	

}
