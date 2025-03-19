// API utilities
const API = {
    // Base URL for API calls
    baseUrl: '',
    
    // Initialize API configuration
    init: async () => {
        try {
            const response = await fetch('/api/config');
            const config = await response.json();
            API.baseUrl = config.apiBaseUrl || '';
            console.log('API initialized with baseUrl:', API.baseUrl);
        } catch (error) {
            console.error('Failed to initialize API configuration:', error);
            // Fallback to relative URLs if config fetch fails
            API.baseUrl = '';
        }
    },
    
    // Authentication
    login: async (username, password) => {
        try {
            const response = await fetch(`${API.baseUrl}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });
            return await response.json();
        } catch (error) {
            console.error('Login error:', error);
            throw error;
        }
    },
    
    register: async (userData) => {
        try {
            const response = await fetch(`${API.baseUrl}/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });
            return await response.json();
        } catch (error) {
            console.error('Registration error:', error);
            throw error;
        }
    },
    
    // Pets
    getPets: async (page = 1, size = 10) => {
        try {
            const response = await fetch(`${API.baseUrl}/pets?page=${page}&size=${size}`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching pets:', error);
            throw error;
        }
    },
    
    getPetById: async (id) => {
        try {
            const response = await fetch(`${API.baseUrl}/pets/${id}`);
            return await response.json();
        } catch (error) {
            console.error(`Error fetching pet ${id}:`, error);
            throw error;
        }
    },
    
    createPet: async (petData) => {
        try {
            const response = await fetch(`${API.baseUrl}/pets`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(petData)
            });
            return await response.json();
        } catch (error) {
            console.error('Error creating pet:', error);
            throw error;
        }
    },
    
    searchPets: async (keyword, page = 1, size = 10) => {
        try {
            const response = await fetch(`${API.baseUrl}/pets/search?keyword=${encodeURIComponent(keyword)}&page=${page}&size=${size}`);
            return await response.json();
        } catch (error) {
            console.error('Error searching pets:', error);
            throw error;
        }
    },
    
    filterPets: async (filters, page = 1, size = 10) => {
        try {
            let url = `${API.baseUrl}/pets/filter?page=${page}&size=${size}`;
            if (filters.type) url += `&type=${encodeURIComponent(filters.type)}`;
            if (filters.status) url += `&status=${encodeURIComponent(filters.status)}`;
            if (filters.startTime) url += `&startTime=${encodeURIComponent(filters.startTime)}`;
            if (filters.endTime) url += `&endTime=${encodeURIComponent(filters.endTime)}`;
            
            const response = await fetch(url);
            return await response.json();
        } catch (error) {
            console.error('Error filtering pets:', error);
            throw error;
        }
    },
    
    markPetAsFound: async (id) => {
        try {
            const response = await fetch(`${API.baseUrl}/pets/${id}/found`, {
                method: 'PUT'
            });
            return await response.json();
        } catch (error) {
            console.error(`Error marking pet ${id} as found:`, error);
            throw error;
        }
    },
    
    // Comments
    getCommentsByPetId: async (petId, page = 1, size = 10) => {
        try {
            const response = await fetch(`${API.baseUrl}/comments/pet/${petId}?page=${page}&size=${size}`);
            return await response.json();
        } catch (error) {
            console.error(`Error fetching comments for pet ${petId}:`, error);
            throw error;
        }
    },
    
    createComment: async (commentData) => {
        try {
            const response = await fetch(`${API.baseUrl}/comments`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(commentData)
            });
            return await response.json();
        } catch (error) {
            console.error('Error creating comment:', error);
            throw error;
        }
    },
    
    uploadAttachments: async (commentId, files) => {
        try {
            const formData = new FormData();
            for (let i = 0; i < files.length; i++) {
                formData.append('files', files[i]);
            }
            
            const response = await fetch(`${API.baseUrl}/comments/${commentId}/attachments`, {
                method: 'POST',
                body: formData
            });
            return await response.json();
        } catch (error) {
            console.error(`Error uploading attachments for comment ${commentId}:`, error);
            throw error;
        }
    },
    
    markCommentAsUseful: async (id) => {
        try {
            const response = await fetch(`${API.baseUrl}/comments/${id}/useful`, {
                method: 'PUT'
            });
            return await response.json();
        } catch (error) {
            console.error(`Error marking comment ${id} as useful:`, error);
            throw error;
        }
    },
    
    // Notifications
    getUserNotifications: async (userId, page = 1, size = 10) => {
        try {
            const response = await fetch(`${API.baseUrl}/notifications/user/${userId}?page=${page}&size=${size}`);
            return await response.json();
        } catch (error) {
            console.error(`Error fetching notifications for user ${userId}:`, error);
            throw error;
        }
    },
    
    getUnreadNotificationCount: async (userId) => {
        try {
            const response = await fetch(`${API.baseUrl}/notifications/user/${userId}/unread/count`);
            return await response.json();
        } catch (error) {
            console.error(`Error fetching unread notification count for user ${userId}:`, error);
            throw error;
        }
    },
    
    markNotificationAsRead: async (id) => {
        try {
            const response = await fetch(`${API.baseUrl}/notifications/${id}/read`, {
                method: 'PUT'
            });
            return await response.json();
        } catch (error) {
            console.error(`Error marking notification ${id} as read:`, error);
            throw error;
        }
    },
    
    markAllNotificationsAsRead: async (userId) => {
        try {
            const response = await fetch(`${API.baseUrl}/notifications/user/${userId}/read/all`, {
                method: 'PUT'
            });
            return await response.json();
        } catch (error) {
            console.error(`Error marking all notifications as read for user ${userId}:`, error);
            throw error;
        }
    }
};

// Utility functions
const Utils = {
    formatDate: (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleString();
    },
    
    showMessage: (message, isError = false) => {
        const messageElement = document.getElementById('message');
        if (messageElement) {
            messageElement.textContent = message;
            messageElement.className = isError ? 'error' : 'success';
            messageElement.style.display = 'block';
            
            setTimeout(() => {
                messageElement.style.display = 'none';
            }, 5000);
        }
    },
    
    getCurrentUser: () => {
        const userJson = localStorage.getItem('currentUser');
        return userJson ? JSON.parse(userJson) : null;
    },
    
    setCurrentUser: (user) => {
        localStorage.setItem('currentUser', JSON.stringify(user));
    },
    
    clearCurrentUser: () => {
        localStorage.removeItem('currentUser');
    },
    
    isLoggedIn: () => {
        return !!Utils.getCurrentUser();
    },
    
    redirectIfNotLoggedIn: () => {
        if (!Utils.isLoggedIn()) {
            window.location.href = 'login.html';
        }
    },
    
    redirectIfLoggedIn: () => {
        if (Utils.isLoggedIn()) {
            window.location.href = 'index.html';
        }
    }
};
