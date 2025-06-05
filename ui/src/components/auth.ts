import axios from 'axios';

// Функция для логина
export const login = async (username: string, password: string): Promise<string> => {
    try {
        const response = await axios.post('http://localhost:8081/auth/login', {
            email: username, // Убедимся, что email передаётся
            password,
        }, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        console.log('Ответ от сервера:', response.data); // Добавим лог для отладки
        const token = response.data.token;
        if (!token) {
            throw new Error('Токен не найден в ответе');
        }
        localStorage.setItem('jwtToken', token);
        console.log('Токен сохранён:', token);
        return token;
    } catch (error) {
        console.error('Ошибка при логине:', error);
        throw new Error('Не удалось войти');
    }
};

// Функция для получения текущего пользователя
export const getCurrentUser = async (): Promise<string> => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        throw new Error('Токен не найден, нужно войти');
    }
    try {
        const response = await axios.get('http://192.168.3.152:8081/users/me', {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        console.log('Данные пользователя:', response.data);
        return response.data; // Здесь можно вернуть данные пользователя
    } catch (error) {
        console.error('Ошибка при запросе пользователя:', error);
        throw new Error('Не удалось получить данные пользователя');
    }
};

// Функция для регистрации
export const register = async (username: string, email: string, password: string): Promise<void> => {
    try {
        const response = await axios.post('http://192.168.3.152:8081/auth/register', {
            username,
            email,
            password,
        });
        console.log('Регистрация успешна:', response.data);
    } catch (error) {
        console.error('Ошибка при регистрации:', error);
        throw new Error('Не удалось зарегистрироваться');
    }
};