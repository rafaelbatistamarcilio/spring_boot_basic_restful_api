package br.com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.app.entity.UserEntity;


/**
 * @author Rafael Marcilio <rafaelbatistamarcilio@gmail.com>
 * 
 * Repository layer
 * spring JpaRepository give some basic methods
 * custom methods cam be made like findByEmail method
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findOneByUsername(String username);
	
	/**
	 * custom metod expample
	 * @param user email
	 */
	@Query(value="Select u from UserEntity u where u.email=:pEmail")
	public UserEntity findByEmail(@Param("pEmail") String email);
}
