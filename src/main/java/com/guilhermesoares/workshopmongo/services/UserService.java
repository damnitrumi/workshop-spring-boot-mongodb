package com.guilhermesoares.workshopmongo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermesoares.workshopmongo.domain.User;
import com.guilhermesoares.workshopmongo.dto.UserDTO;
import com.guilhermesoares.workshopmongo.repository.UserRepository;
import com.guilhermesoares.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	
	public List<User> findAll(){
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public User update(User obj) {
		try {
			Optional<User> newObj = repo.findById(obj.getId());
			User dtbObj = newObj.orElseThrow(NoSuchElementException::new);
			updateData(dtbObj, obj);
			return repo.save(dtbObj);
		}catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("Object not Found");
		}
	}
	
	private void updateData(User dtbObj, User routeObj) {
		dtbObj.setName(routeObj.getName());
		dtbObj.setEmail(routeObj.getEmail());
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
