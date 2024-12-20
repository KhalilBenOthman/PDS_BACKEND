package com.spsrh.userService.service.impl;

import com.spsrh.userService.dto.*;
import com.spsrh.userService.dto.ManagerDTO;
import com.spsrh.userService.exception.InvalidAssignationException;
import com.spsrh.userService.exception.ManagerNotFoundException;
import com.spsrh.userService.exception.UserNotFoundException;
import com.spsrh.userService.feign.AuthServiceFeignClient;
import com.spsrh.userService.model.*;
import com.spsrh.userService.repository.ManagerRepository;
import com.spsrh.userService.repository.UserRepository;
import com.spsrh.userService.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
	private final ManagerRepository managerRepository;
	private final AuthServiceFeignClient authSeriveFeignClient;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository,
    					ManagerRepository managerRepository, 
    					ModelMapper modelMapper,
    					AuthServiceFeignClient authSeriveFeignClient) {
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
        this.authSeriveFeignClient = authSeriveFeignClient;
    }

	/*
	 * @Override public User createUser(UserDTO userDTO) { User user = null;
	 * 
	 * if (userDTO instanceof ManagerDTO) { // Handle manager creation ManagerDTO
	 * managerDTO = (ManagerDTO) userDTO;
	 * 
	 * // Check if manager already exists Optional<User> existingManager =
	 * Optional.of(userRepository.findByUsername(managerDTO.getUsername())); if
	 * (existingManager.isPresent()) { throw new
	 * InvalidAssignationException("Manager with username '" +
	 * managerDTO.getUsername() + "' already exists."); }
	 * 
	 * // Create and set up manager user = new Manager();
	 * user.setUsername(userDTO.getUsername()); user.setEmail(userDTO.getEmail());
	 * String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
	 * user.setPassword(hashedPassword); user.setRole(RoleEnum.MANAGER);
	 * 
	 * // Get team members (employees) List<Employee> teamMembers =
	 * userRepository.findAllByUsernameIn(managerDTO.getTeamMemberUsernames())
	 * .stream() .map(u -> (Employee) u) .collect(Collectors.toList());
	 * 
	 * ((Manager) user).setTeam(teamMembers); for (Employee employee : teamMembers)
	 * { employee.setManager((Manager) user); } } else if (userDTO instanceof
	 * EmployeeDTO) { // Handle employee creation EmployeeDTO employeeDTO =
	 * (EmployeeDTO) userDTO;
	 * 
	 * // Check if employee already exists Optional<User> existingEmployee =
	 * Optional.of(userRepository.findByUsername(employeeDTO.getUsername())); if
	 * (existingEmployee.isPresent()) { throw new
	 * InvalidAssignationException("Employee with username '" +
	 * employeeDTO.getUsername() + "' already exists."); }
	 * 
	 * // Create and set up employee user = new Employee();
	 * user.setUsername(userDTO.getUsername()); user.setEmail(userDTO.getEmail());
	 * String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
	 * user.setPassword(hashedPassword); user.setRole(RoleEnum.EMPLOYEE);
	 * 
	 * ((Employee) user).setHireDate(employeeDTO.getHireDate()); Manager manager =
	 * (Manager) userRepository.findByUsername(employeeDTO.getManagerUsername()); if
	 * (manager == null) { throw new
	 * ManagerNotFoundException(employeeDTO.getManagerUsername()); } ((Employee)
	 * user).setManager(manager);
	 * 
	 * manager.getTeam().add((Employee) user); } else { // Handle general User
	 * creation (if needed) user = new User();
	 * user.setUsername(userDTO.getUsername()); user.setEmail(userDTO.getEmail());
	 * String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
	 * user.setPassword(hashedPassword); user.setRole(RoleEnum.ADMIN); }
	 * 
	 * return userRepository.save(user); }
	 */
    private void setUserProperties(User user, UserDTO userDTO) {
        // Set core user properties (username, email, password, role)
        user.setUsername(userDTO.getUsername());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(hashedPassword);
        
        // Set role
        if (userDTO instanceof ManagerDTO) {
            user.setRole(RoleEnum.MANAGER);
        } else if (userDTO instanceof EmployeeDTO) {
            user.setRole(RoleEnum.EMPLOYEE);
        } else {
            user.setRole(RoleEnum.ADMIN); // Default role for general users (e.g., admins)
        }

        // Set additional common attributes (address, city, createdAt, etc.)
        user.setAdresse(userDTO.getAdresse());
        user.setCity(userDTO.getCity());
        user.setCodePostal(userDTO.getCodePostal());
        user.setPays(userDTO.getPays());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setIsActive(userDTO.getIsActive());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
    }

    @Override 
    public User createUser(UserDTO userDTO) {
        User user = null;

        if (userDTO instanceof ManagerDTO) {
            // Handle manager creation
            ManagerDTO managerDTO = (ManagerDTO) userDTO;

            // Check if manager already exists
            Optional<User> existingManager = Optional.ofNullable((userRepository.findByUsername(managerDTO.getUsername())));
            if (existingManager.isPresent()) {
                throw new InvalidAssignationException("Manager with username '" + managerDTO.getUsername() + "' already exists.");
            }

            // Create and set up manager
            user = new Manager();

            // Set user properties and attributes
            setUserProperties(user, managerDTO);

            ((Manager) user).setHireDate(managerDTO.getHireDate());
            ((Manager) user).setJobTitle(managerDTO.getJobTitle());
            ((Manager) user).setDepartment(Department.valueOf(managerDTO.getDepartment())); // Assuming `Department` is an enum
            ((Manager) user).setSkills(managerDTO.getSkills());
            ((Manager) user).setSalary(managerDTO.getSalary());
            ((Manager) user).setAvailabilityStatus(managerDTO.getAvailabilityStatus());
            ((Manager) user).setLastPerformanceReviewDate(managerDTO.getLastPerformanceReviewDate());
            
            
            // Get team members (employees)
            List<Employee> teamMembers = userRepository.findAllByUsernameIn(managerDTO.getTeamMemberUsernames())
                    .stream()
                    .map(u -> (Employee) u)
                    .collect(Collectors.toList());

            ((Manager) user).setTeam(teamMembers);
            for (Employee employee : teamMembers) {
                employee.setManager((Manager) user);
            }
        } else if (userDTO instanceof EmployeeDTO) {
            // Handle employee creation
            EmployeeDTO employeeDTO = (EmployeeDTO) userDTO;

            // Check if employee already exists
            Optional<User> existingEmployee = Optional.ofNullable(userRepository.findByUsername(employeeDTO.getUsername()));
            if (existingEmployee.isPresent()) {
                throw new InvalidAssignationException("Employee with username '" + employeeDTO.getUsername() + "' already exists.");
            }

            // Create and set up employee
            user = new Employee();

            // Set user properties and attributes
            setUserProperties(user, employeeDTO);

            // Set specific employee attributes
            ((Employee) user).setHireDate(employeeDTO.getHireDate());
            ((Employee) user).setJobTitle(employeeDTO.getJobTitle());
            ((Employee) user).setDepartment(Department.valueOf(employeeDTO.getDepartment())); // Assuming `Department` is an enum
            ((Employee) user).setSkills(employeeDTO.getSkills());
            ((Employee) user).setSalary(employeeDTO.getSalary());
            ((Employee) user).setAvailabilityStatus(employeeDTO.getAvailabilityStatus());
            ((Employee) user).setLastPerformanceReviewDate(employeeDTO.getLastPerformanceReviewDate());

            Manager manager = (Manager) userRepository.findByUsername(employeeDTO.getManagerUsername());
            if (manager == null) {
                throw new ManagerNotFoundException(employeeDTO.getManagerUsername());
            }
            ((Employee) user).setManager(manager);

            manager.getTeam().add((Employee) user);
        } else {
            // Handle general User creation (if needed, such as ADMIN)
            user = new User();

            // Set user properties and attributes
            setUserProperties(user, userDTO);
        }

        return userRepository.save(user);
    }

    
    
    
    
    
    
    
    
    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
    	return userRepository.findAll();
    }
   

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + username);
        }
        userRepository.delete(user);
    }


    @Override
    public boolean validateCredentials(UserCredentials credentials) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(credentials.getUsername()));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Use the password encoder to match the raw password with the hashed one in the database
            return passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        }
        return false;
    }
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
	@Override
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	
	public String getUserFromToken(String token) {
		String response = authSeriveFeignClient.getUserFromToken(token);
		return response;
	}
	@Override
    public User getUserBytoken(String token) {
        User user = userRepository.findByUsername(getUserFromToken(token));
        if (user == null) {
            throw new UserNotFoundException("User not found: ");
        }
        return user;
    }

	@Override
	public Map<String, Object> getUserStatistics() {
	    List<User> allUsers = getAllUsers(); // Assuming this fetches all users.

	    long totalUsers = allUsers.size();
	    long activeUsers = allUsers.stream().filter(User::getIsActive).count();
	    
	    LocalDate today = LocalDate.now();
	    long usersCreatedToday = allUsers.stream()
	        .filter(user -> user.getCreatedAt().toLocalDate().isEqual(today))
	        .count();

	    long usersCreatedThisMonth = allUsers.stream()
	        .filter(user -> user.getCreatedAt().toLocalDate().getMonth() == today.getMonth() &&
	                        user.getCreatedAt().toLocalDate().getYear() == today.getYear())
	        .count();

	    long admins = allUsers.stream().filter(user -> user.getRole() == RoleEnum.ADMIN).count();
	    long employees = allUsers.stream().filter(user -> user.getRole() == RoleEnum.EMPLOYEE).count();
	    long managers = allUsers.stream().filter(user -> user.getRole() == RoleEnum.MANAGER).count();

	    Map<String, Object> stats = new HashMap<>();
	    stats.put("totalUsers", totalUsers);
	    stats.put("activeUsers", activeUsers);
	    stats.put("usersCreatedToday", usersCreatedToday);
	    stats.put("usersCreatedThisMonth", usersCreatedThisMonth);
	    stats.put("admins", admins);
	    stats.put("employees", employees);
	    stats.put("managers", managers);

	    return stats;
	}	
}
