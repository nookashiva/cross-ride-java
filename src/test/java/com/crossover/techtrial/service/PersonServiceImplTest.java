package com.crossover.techtrial.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class PersonServiceImplTest {
	
	  @Mock
	  PersonRepository personRepository;
	  @InjectMocks
	  PersonServiceImpl classToTest;
	  
	  @Test
	  public void shouldGetAllPersonList(){
		 List<Person> personList =  new ArrayList<Person>();
		 personList.add(new Person());
		when(personRepository.findAll()).thenReturn(personList);
		List<Person> result = classToTest.getAll();
		Assert.assertEquals(1, result.size());
	  }

	  @Test
	  public void shouldSavePerson(){
		  Person person = new Person();
		  when(personRepository.save(person)).thenReturn(person);
		  Person p = classToTest.save(person);
		  Assert.assertNotNull(p);
	  }
	  
	  @Test
	  public void shouldReturnPersonIdIdFound(){
		  Optional<Person> person = Optional.of(new Person());
		  when(personRepository.findById(1l)).thenReturn(person);
		  Person p = classToTest.findById(1l);
		  Assert.assertNotNull(p);
	  }
	  
}
