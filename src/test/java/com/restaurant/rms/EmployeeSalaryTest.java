package com.restaurant.rms;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.EmployeeSalary;
import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.EmployeeSalaryRepo;
import com.restaurant.rms.service.EmployeeSalaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeSalaryTest {

    @Mock
    private EmployeeSalaryRepo salaryRepo;

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeSalaryService salaryService;

    @Test
    void getAll_callsRepo() {
        salaryService.getAll();
        verify(salaryRepo).findAll();
    }

    @Test
    void getByIdOrThrow_found_returnsEntity() {
        EmployeeSalary s = new EmployeeSalary();
        when(salaryRepo.findById(5)).thenReturn(Optional.of(s));
        EmployeeSalary out = salaryService.getByIdOrThrow(5);
        assertSame(s, out);
    }

    @Test
    void getByIdOrThrow_notFound_throws() {
        when(salaryRepo.findById(404)).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> salaryService.getByIdOrThrow(404));
        assertEquals("Salary not found", ex.getMessage());
    }

    @Test
    void createFromDto_success() {
        // Arrange
        Employee emp = new Employee();
        when(employeeRepo.findById(10)).thenReturn(Optional.of(emp));
        when(salaryRepo.save(any(EmployeeSalary.class))).thenAnswer(inv -> inv.getArgument(0));

        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
        dto.setEmployeeId(10);
        dto.setAmount(new BigDecimal("1234.56"));
        dto.setMethod("Bank");
        dto.setLastPayment(LocalDate.of(2025, 8, 1));

        // Act
        EmployeeSalary saved = salaryService.createFromDto(dto);

        // Assert
        ArgumentCaptor<EmployeeSalary> captor = ArgumentCaptor.forClass(EmployeeSalary.class);
        verify(salaryRepo).save(captor.capture());
        EmployeeSalary toSave = captor.getValue();

        assertSame(emp, toSave.getEmployee());
        assertEquals(new BigDecimal("1234.56"), toSave.getEmployeePaymentAmount());
        assertEquals("Bank", toSave.getEmployeePaymentMethod());
        assertEquals(LocalDate.of(2025, 8, 1), toSave.getLastPayment());
        assertSame(toSave, saved);
    }

    @Test
    void createFromDto_employeeNotFound_throws() {
        when(employeeRepo.findById(77)).thenReturn(Optional.empty());

        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
        dto.setEmployeeId(77);
        dto.setAmount(new BigDecimal("1.00"));
        dto.setMethod("Cash");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> salaryService.createFromDto(dto));
        assertEquals("Employee not found", ex.getMessage());
        verify(salaryRepo, never()).save(any());
    }

    @Test
    void updateFromDto_success() {
        // Arrange
        EmployeeSalary existing = new EmployeeSalary();
        existing.setEmployeePaymentId(9);

        Employee emp = new Employee();

        when(salaryRepo.findById(9)).thenReturn(Optional.of(existing));
        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));
        when(salaryRepo.save(any(EmployeeSalary.class))).thenAnswer(inv -> inv.getArgument(0));

        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
        dto.setEmployeePaymentId(9);
        dto.setEmployeeId(1);
        dto.setAmount(new BigDecimal("222.22"));
        dto.setMethod("EFT");
        dto.setLastPayment(LocalDate.of(2025, 9, 1));

        // Act
        EmployeeSalary updated = salaryService.updateFromDto(dto);

        // Assert
        assertSame(emp, updated.getEmployee());
        assertEquals(new BigDecimal("222.22"), updated.getEmployeePaymentAmount());
        assertEquals("EFT", updated.getEmployeePaymentMethod());
        assertEquals(LocalDate.of(2025, 9, 1), updated.getLastPayment());
        verify(salaryRepo).save(existing);
    }

    @Test
    void updateFromDto_missingId_throws() {
        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
        dto.setEmployeeId(1);
        dto.setAmount(new BigDecimal("10.00"));
        dto.setMethod("Cash");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> salaryService.updateFromDto(dto));
        assertEquals("Salary ID is required for update", ex.getMessage());
        verify(salaryRepo, never()).save(any());
    }

    @Test
    void delete_delegatesToRepo() {
        salaryService.delete(12);
        verify(salaryRepo).deleteById(12);
    }
}
