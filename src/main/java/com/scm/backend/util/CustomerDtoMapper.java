package com.scm.backend.util;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.entity.Customer;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface CustomerDtoMapper {
    Customer toCustomer(CustomerDto customerDto);
    CustomerDto toCustomerDto(Customer customer);
}
