package com.demo.springpetclinic.services.springdatajpa;

import com.demo.springpetclinic.model.Owner;
import com.demo.springpetclinic.repositories.OwnerRepository;
import com.demo.springpetclinic.repositories.PetRepository;
import com.demo.springpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSpringDataJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSpringDataJpaService ownerSpringDataJpaService;

    Owner owner;

    private final Long ID = 1L;
    private final String LAST_NAME = "Smith";

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        // Given
        when(ownerRepository.findByLastName(anyString())).thenReturn(owner);

        // When
        Owner smith = ownerSpringDataJpaService.findByLastName(LAST_NAME);

        // Then
        assertEquals(ID, smith.getId());
        verify(ownerRepository, times(1)).findByLastName(LAST_NAME);
    }

    @Test
    void findById() {
        // Given
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(owner));

        // When
        Owner toReturn = ownerSpringDataJpaService.findById(ID);

        // Then
        assertEquals(LAST_NAME, toReturn.getLastName());
        verify(ownerRepository, times(1)).findById(ID);
    }

    @Test
    void findAll() {
        // Given
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(owner);
        when(ownerRepository.findAll()).thenReturn(ownerSet);

        // When
        Set<Owner> toReturn = ownerSpringDataJpaService.findAll();

        // Then
        assertEquals(1, toReturn.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void save() {
        // Given
        when(ownerRepository.save(any(Owner.class))).thenReturn((owner));

        // When
        Owner toReturn = ownerSpringDataJpaService.save(owner);

        // Then
        assertNotNull(toReturn);
        assertEquals(ID, toReturn.getId());
        assertEquals(LAST_NAME, toReturn.getLastName());
    }

    @Test
    void delete() {
        // When
        ownerSpringDataJpaService.delete(owner);

        // Then
        verify(ownerRepository, times(1)).delete(any(Owner.class));
    }

    @Test
    void deleteById() {
        // When
        ownerSpringDataJpaService.deleteById(ID);

        // Then
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }
}