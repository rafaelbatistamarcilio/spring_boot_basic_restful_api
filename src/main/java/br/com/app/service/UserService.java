package br.com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.app.entity.UserEntity;
import br.com.app.repository.UserRepository;
import br.com.app.utils.ServiceLayerException;

/**
 * @author Rafael Marcilio <rafaelbatistamarcilio@gmail.com>
 * 
 * Service layer
 * business
 */
@Service
public class UserService {

	@Autowired
	private  UserRepository repository;
	
	public List<UserEntity> findAll() throws ServiceLayerException {

		try {

			return repository.findAll();

		} catch (Exception e) {
			throw new ServiceLayerException("ERRO_CAMADA_SERVICE_USUARIO_FIND_ALL");
		}
	}

	/**
	 * find a user by id 
	 * @param id  - user id
	 * 
	 * @return - UserEntity -> user instance
	 */
	public UserEntity findOne(Long id) throws ServiceLayerException {
		
		try {

			UserEntity user =  repository.findOne(id);
			
			if(user==null) throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"USER_NOT_FOUND");
			
			return user;
			
		}catch (ServiceLayerException slE) {			
			throw new ServiceLayerException(slE.getMessage());			
		}catch (Exception e) {
			throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"SEARCHING_FOR_USER");
		}
	}

	/**
	 * delete a user by id
	 * 
	 * @param id
	 * @return boolean - true if user was deleted
	 * 
	 */
	public boolean remove(Long id) throws ServiceLayerException {
		
		try {
			UserEntity user = repository.findOne(id);
			
			if (user == null) {
				throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"USER_NOT_FOUND");
			}			
		
			repository.delete(user);
			
			return true;

		}catch (ServiceLayerException slE) {			
			throw new ServiceLayerException(slE.getMessage());			
		} catch (Exception e) {
			throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"USER_NOT_DELETED");
		}

	}

	/**
	 * persiste ou atualiza um usuario
	 * 
	 * @param - novoUsuario objeto com os dados do usuario a ser persistido
	 * @return - 
	 */
	public UserEntity save(UserEntity newUser) throws ServiceLayerException {
		
		try {
			
			if (repository.findOneByUsername( newUser.getUsername() ) != null) {
				throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"USERNAME_ALREADY_EXISTS");
			}
			
			if (repository.findByEmail( newUser.getEmail()) != null) {
				throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"EMAIL_ALREADY_EXISTS");
			}
			
			//to return a UserEntity instance with id
			return repository.saveAndFlush(newUser);
			
		} catch (ServiceLayerException slE) {			
			
			throw new ServiceLayerException(slE.getMessage());		
			
		}catch (Exception e) {
			throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"USER_NOT_SAVED");
		}
	}

	public UserEntity findOneByUsername(String login) throws ServiceLayerException {

		try {
			UserEntity user =   this.repository.findOneByUsername(login);
			
			if(user==null) throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"USER_NOT_FOUND");
			
			return user;
			
		} catch (ServiceLayerException slE) {			
			
			throw new ServiceLayerException(slE.getMessage());		
			
		} catch (Exception e) {
			throw new ServiceLayerException(ServiceLayerException.ERROR_SERVICE_LAYER+"SEARCHING_FOR_USER");
		}
	}
}	
