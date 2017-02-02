package br.com.app.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import br.com.app.entity.UserEntity;
import br.com.app.service.UserService;
import br.com.app.utils.BaseResource;
import br.com.app.utils.ServiceLayerException;

/**
 * Classe que recebe as chamadas de controle de users
 * 
 * @author Rafael Marcilio <https://github.com/rafaelbatistamarcilio>
 * 
 * Rest layer
 * 
 * Responsible for providing access to the application through HTTP requests to specific URLs, 
 * passing the request to the lower layer (Service) 
 * and responding to the HTTP request with the required data and a status indicating the result of the operation
 * 
 * OBS: By default the response object is in Json format in Spring Boot framework  
 *
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserResource extends BaseResource{
	@Autowired
	private  UserService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> findAll() {
		try {

			List<UserEntity> users = this.service.findAll();
			return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);

		} catch (ServiceLayerException e) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserEntity> findOne(@PathVariable Long id) {
		try {

			UserEntity user = this.service.findOne(id);

			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

		} catch (ServiceLayerException e) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable Long id) {
		try {

			this.service.remove(id);

			return new ResponseEntity<String>("SUCCESS_REST_LAYER_USER_DELETED", HttpStatus.OK);

		} catch (ServiceLayerException e) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserEntity> save(@RequestBody @Valid UserEntity novouser) {

		try {

			UserEntity user = this.service.save(novouser);

			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

		} catch (ServiceLayerException e) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
