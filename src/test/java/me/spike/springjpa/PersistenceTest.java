package me.spike.springjpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PersistenceTest {

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    private LaptopJpaRepository laptopJpaRepository;

    @Test
    void shouldDeleteLaptopWhenEmployeeNoLongerNeedsOne() {
        final UUID employeeId = UUID.randomUUID();
        final UUID laptopId = UUID.randomUUID();
        final Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName("Emp");
        final Laptop laptop = new Laptop();
        laptop.setId(laptopId);
        laptop.setModel("Apple");
        employee.setLaptop(laptop);
        laptop.setEmployee(employee);

        employeeJpaRepository.save(employee);

        final Optional<Employee> maybeEmployee = employeeJpaRepository.findById(employeeId);
        assertTrue(maybeEmployee.isPresent());
        final Employee fetchedEmployee = maybeEmployee.get();
        assertEquals("Emp", fetchedEmployee.getName());
        assertNotNull(fetchedEmployee.getLaptop());
        assertEquals("Apple", fetchedEmployee.getLaptop().getModel());
        assertEquals(laptopId, fetchedEmployee.getLaptop().getId());

        final Optional<Laptop> maybeLaptop = laptopJpaRepository.findById(laptopId);
        assertTrue(maybeLaptop.isPresent());

        //Unlink laptop
        fetchedEmployee.getLaptop().setEmployee(null);
        fetchedEmployee.setLaptop(null);

        employeeJpaRepository.save(fetchedEmployee);

        assertTrue(laptopJpaRepository.findById(laptopId).isEmpty());
    }
}
