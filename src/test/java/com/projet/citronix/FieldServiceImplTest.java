package com.projet.citronix;

import com.projet.citronix.dto.field.FieldRequestDTO;
import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.mapper.FieldMapper;
import com.projet.citronix.model.Farm;
import com.projet.citronix.model.Field;
import com.projet.citronix.model.Tree;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.service.impl.FieldServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldServiceImplTest {

    @InjectMocks
    private FieldServiceImpl fieldService;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FieldMapper fieldMapper;

    private Field field;
    private Farm farm;
    private Tree tree1;
    private Tree tree2;
    private FieldRequestDTO fieldRequestDTO;
    private FieldResponseDTO fieldResponseDTO;

    @BeforeEach
    void setUp() {
        tree1 = Tree.builder()
                .id(1L)
                .plantationDate(LocalDate.of(2020, 1, 1))
                .build();

        tree2 = Tree.builder()
                .id(2L)
                .plantationDate(LocalDate.of(2021, 1, 1))
                .build();

        farm = Farm.builder()
                .id(1L)
                .name("Test Farm")
                .size(2000.0)
                .fields(Collections.emptyList())
                .build();

        field = Field.builder()
                .id(1L)
                .name("Field 1")
                .area(1000.0)
                .farm(farm)
                .trees(Arrays.asList(tree1, tree2))
                .build();

        fieldRequestDTO = new FieldRequestDTO("Field 1", 1000.0, 1L);
        fieldResponseDTO = new FieldResponseDTO(1L, "Field 1", 1000.0, null);
    }

    @Nested
    @DisplayName("Create Field Tests")
    class CreateFieldTests {

        @Test
        @DisplayName("Should create field with trees successfully")
        void createField_WithTrees_Success() {
            when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
            when(fieldMapper.toEntity(fieldRequestDTO)).thenReturn(field);
            when(fieldRepository.save(field)).thenReturn(field);
            when(fieldMapper.toDTO(field)).thenReturn(fieldResponseDTO);

            FieldResponseDTO result = fieldService.createField(fieldRequestDTO);

            assertNotNull(result);
            assertEquals(fieldResponseDTO, result);
            verify(fieldRepository).save(field);
            assertEquals(2, field.getTrees().size());
        }

        @Test
        @DisplayName("Should throw exception if farm is not found")
        void createField_FarmNotFound() {
            when(farmRepository.findById(1L)).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> fieldService.createField(fieldRequestDTO));

            assertEquals("Farm not found with ID: 1", exception.getMessage());
            verify(fieldRepository, never()).save(any(Field.class));
        }
    }

    @Nested
    @DisplayName("Update Field Tests")
    class UpdateFieldTests {

        @Test
        @DisplayName("Should throw exception if field is not found")
        void updateField_FieldNotFound() {
            when(fieldRepository.findById(1L)).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> fieldService.updateField(1L, fieldRequestDTO));

            assertEquals("Field not found with ID: 1", exception.getMessage());
            verify(fieldRepository, never()).save(any(Field.class));
        }
    }

    @Nested
    @DisplayName("Delete Field Tests")
    class DeleteFieldTests {

        @Test
        @DisplayName("Should delete field successfully")
        void deleteField_Success() {
            when(fieldRepository.existsById(1L)).thenReturn(true);

            boolean result = fieldService.deleteField(1L);

            assertTrue(result);
            verify(fieldRepository).deleteById(1L);
        }

        @Test
        @DisplayName("Should return false if field does not exist")
        void deleteField_FieldNotFound() {
            when(fieldRepository.existsById(1L)).thenReturn(false);

            boolean result = fieldService.deleteField(1L);

            assertFalse(result);
            verify(fieldRepository, never()).deleteById(1L);
        }
    }

    @Nested
    @DisplayName("Get Field By ID Tests")
    class GetFieldByIdTests {

        @Test
        @DisplayName("Should return field by ID")
        void getFieldById_Success() {
            when(fieldRepository.findById(1L)).thenReturn(Optional.of(field));
            when(fieldMapper.toDTO(field)).thenReturn(fieldResponseDTO);

            FieldResponseDTO result = fieldService.getFieldById(1L);

            assertNotNull(result);
            assertEquals(fieldResponseDTO, result);
        }

        @Test
        @DisplayName("Should throw exception if field is not found")
        void getFieldById_NotFound() {
            when(fieldRepository.findById(1L)).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> fieldService.getFieldById(1L));

            assertEquals("Field not found with ID: 1", exception.getMessage());
        }
    }
}
