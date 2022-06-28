package com.devsuperior.bds01.services;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import com.devsuperior.bds01.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAllPaged(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize() , Sort.by(Sort.Direction.ASC, "name"));
        Page<Employee> page = employeeRepository.findAll(pageRequest);
        return page.map(x-> new EmployeeDTO(x));

    }

    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Department department = departmentRepository.getOne(employeeDTO.getDepartmentId());
        Employee entity = new Employee();
        entity.setName(employeeDTO.getName());
        entity.setEmail(employeeDTO.getEmail());
        entity.setDepartment(department);

        entity = employeeRepository.save(entity);

        return new EmployeeDTO(entity);
    }
}
