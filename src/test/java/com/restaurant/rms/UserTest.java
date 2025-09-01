package com.restaurant.rms;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.User;
import com.restaurant.rms.models.DTO.UserDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.UserRepo;
import com.restaurant.rms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private UserService userService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(); // assumes a no-args constructor exists (typical JPA)
        // (No need to set fields for these service-level tests)
    }

    @Test
    void createFromDto_success() {
        // Arrange
        UserDTO dto = new UserDTO();
        dto.setUsername("alice");
        dto.setPassword("p@ss");
        dto.setRole("ADMIN");
        dto.setEmployeeId(123);

        when(employeeRepo.findById(123)).thenReturn(Optional.of(employee));
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        User saved = userService.createFromDto(dto);

        // Assert
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(captor.capture());
        User toSave = captor.getValue();

        assertEquals("alice", toSave.getUsername());
        assertEquals("p@ss", toSave.getPassword());
        assertEquals("ADMIN", toSave.getRole());
        assertSame(employee, toSave.getEmployee());
        assertSame(toSave, saved);
    }

    @Test
    void createFromDto_employeeNotFound_throws() {
        // Arrange
        UserDTO dto = new UserDTO();
        dto.setUsername("bob");
        dto.setPassword("1234");
        dto.setRole("USER");
        dto.setEmployeeId(999);

        when(employeeRepo.findById(999)).thenReturn(Optional.empty());

        // Act + Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.createFromDto(dto));
        assertEquals("Employee not found", ex.getMessage());
        verify(userRepo, never()).save(any());
    }

    @Test
    void updateFromDto_success_sameUsername() {
        // Arrange
        Integer id = 1;
        User existing = new User("old", "oldpass", "USER", employee);
        existing.setUserId(id);

        UserDTO dto = new UserDTO();
        dto.setUsername("old"); // same username
        dto.setPassword("newpass");
        dto.setRole("ADMIN");
        dto.setEmployeeId(123);

        when(userRepo.findById(id)).thenReturn(Optional.of(existing));
        // findByUsername returns the same user -> allowed
        when(userRepo.findByUsername("old")).thenReturn(Optional.of(existing));
        when(employeeRepo.findById(123)).thenReturn(Optional.of(employee));
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        User updated = userService.updateFromDto(id, dto);

        // Assert
        assertEquals("old", updated.getUsername());
        assertEquals("newpass", updated.getPassword());
        assertEquals("ADMIN", updated.getRole());
        assertSame(employee, updated.getEmployee());
        verify(userRepo).save(existing);
    }

    @Test
    void updateFromDto_duplicateUsername_throws() {
        // Arrange
        Integer id = 1;
        User existing = new User("alice", "pass", "USER", employee);
        existing.setUserId(id);

        UserDTO dto = new UserDTO();
        dto.setUsername("taken");
        dto.setPassword("x");
        dto.setRole("USER");
        dto.setEmployeeId(1);

        User other = new User("taken", "y", "USER", employee);
        other.setUserId(2);

        when(userRepo.findById(id)).thenReturn(Optional.of(existing));
        when(userRepo.findByUsername("taken")).thenReturn(Optional.of(other));

        // Act + Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.updateFromDto(id, dto));
        assertEquals("Username already exists", ex.getMessage());
        verify(userRepo, never()).save(any());
    }

    @Test
    void delete_delegatesToRepo() {
        userService.delete(7);
        verify(userRepo).deleteById(7);
    }
}
