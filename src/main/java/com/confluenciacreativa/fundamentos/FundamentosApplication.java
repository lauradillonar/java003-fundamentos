package com.confluenciacreativa.fundamentos;
import com.confluenciacreativa.fundamentos.bean.MyBean;
import com.confluenciacreativa.fundamentos.bean.MyBeanWithDependency;
import com.confluenciacreativa.fundamentos.bean.MyBeanWithProperties;
import com.confluenciacreativa.fundamentos.component.ComponentDependency;
import com.confluenciacreativa.fundamentos.entity.User;
import com.confluenciacreativa.fundamentos.pojo.UserPojo;
import com.confluenciacreativa.fundamentos.repository.UserRepository;
import com.confluenciacreativa.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
								  MyBean myBean,
								  MyBeanWithDependency myBeanWithDependency,
								  MyBeanWithProperties myBeanWithProperties,
								  UserPojo userPojo,
								  UserRepository userRepository,
								  UserService userService){

		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "TestTransactional3@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1,test2,test3,test4);

		userService.saveTransactional(users);

		userService.getAllUsers()
				.stream()
				.forEach(user -> LOGGER.info("Este es el usuario dentro del método transaccional "+user));
	}

	private void getInformationJpqlFromUser(){
		LOGGER.info("Usuario con el método findByUserEmail: "+
				userRepository.findByUserEmail("julie@domain.com")
						.orElseThrow(() -> new RuntimeException(("No se encuentró el usuario")))
		);

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con método sort " + user));

		userRepository.findByName("John")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con query methods " + user));

		LOGGER.info("Usuario con query method findByEmailAndName"+userRepository.findByEmailAndName("daniela@domain.com", "Daniela")
				.orElseThrow(()-> new RuntimeException("Usuario no encontrado")));

		userRepository.findByNameLike("%u%")
				.stream()
				.forEach(user->LOGGER.info("Usuario findByNameLike " + user));

		userRepository.findByNameOrEmail(null, "user10@domain.com")
				.stream()
				.forEach(user->LOGGER.info("Usuario findByNameOrEmail " + user));

		userRepository.findByBirthDateBetween(LocalDate.of(2021,3,1),
												LocalDate.of(2021,5,1))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas: "+user));

		// userRepository.findByNameLikeOrderByIdDesc("%user%")
		//		.stream()
		//		.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado " + user));

		userRepository.findByNameContainingOrderByIdDesc("user")
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado " + user));

		LOGGER.info("El usuario a partir del named parameter es: "+ userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021,03,20),
				"daniela@domain.com")
				.orElseThrow(()->new RuntimeException("No se encontró el usuario a partir del named parameter")));
	}

	private void saveUsersInDataBase(){
		User user1 = new User("John", "john@domain.com", LocalDate.of(2021,03,20));
		User user2 = new User("Julie", "julie@domain.com", LocalDate.of(2021,05,20));
		User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2021,03,20));
		User user4 = new User("user4", "user4@domain.com", LocalDate.of(2021,07,20));
		User user5 = new User("user5", "user5@domain.com", LocalDate.of(2021,11,20));
		User user6 = new User("user6", "user6@domain.com", LocalDate.of(2021,2,20));
		User user7 = new User("user7", "user7@domain.com", LocalDate.of(2021,3,20));
		User user8 = new User("user8", "user8@domain.com", LocalDate.of(2021,4,20));
		User user9 = new User("user9", "user9@domain.com", LocalDate.of(2021,5,20));
		User user10 = new User("user10", "user10@domain.com", LocalDate.of(2021,8,20));
		User user11 = new User("user11", "user11@domain.com", LocalDate.of(2021,1,20));
		User user12 = new User("user12", "user 12@domain.com", LocalDate.of(2021,2,20));

		List<User> list= Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10,user11,user12);
		list.stream().forEach(userRepository::save);
	}

	public void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
		try{
			//error
			int value = 10/0;
			LOGGER.debug("Mi valor: "+value);
		}catch (Exception e) {
			LOGGER.error("Esto es un error al dividir por cero "+e.getMessage());
		}
	}
}
