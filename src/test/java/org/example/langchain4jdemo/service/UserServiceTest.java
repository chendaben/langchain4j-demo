package org.example.langchain4jdemo.service;

import org.example.langchain4jdemo.entity.User;
import org.example.langchain4jdemo.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setName("Test User");
        testUser.setGender("Male");
        testUser.setIdCard("123456789012345678");
        testUser.setBirthday(new Date());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(userMapper.selectById(1)).thenReturn(testUser);

        // Act
        User result = userService.getUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test User", result.getName());
        verify(userMapper, times(1)).selectById(1);
    }

    @Test
    void getUserById_ShouldReturnNull_WhenUserDoesNotExist() {
        // Arrange
        when(userMapper.selectById(999)).thenReturn(null);

        // Act
        User result = userService.getUserById(999);

        // Assert
        assertNull(result);
        verify(userMapper, times(1)).selectById(999);
    }

    @Test
    void deleteUsersByIds_ShouldReturnDeletedCount() {
        // Arrange
        List<Integer> ids = Arrays.asList(1, 2, 3);
        when(userMapper.deleteBatchIds(ids)).thenReturn(3);

        // Act
        int result = userService.deleteUsersByIds(ids);

        // Assert
        assertEquals(3, result);
        verify(userMapper, times(1)).deleteBatchIds(ids);
    }

    @Test
    void createUser_ShouldReturnUserWithId() {
        // Arrange
        User newUser = new User();
        newUser.setName("New User");
        newUser.setGender("Female");
        newUser.setIdCard("987654321098765432");
        newUser.setBirthday(new Date());
        
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(10); // Simulate auto-generated ID
            return 1;
        }).when(userMapper).insert(newUser);

        // Act
        User result = userService.createUser(newUser);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals("New User", result.getName());
        verify(userMapper, times(1)).insert(newUser);
    }
}
