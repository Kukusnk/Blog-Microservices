import React, { useState } from 'react';
import { register } from './auth';

interface UserCreateDTO {
    username: string;
    email: string;
    password: string;
}

const RegistrationForm: React.FC = () => {
    const [formData, setFormData] = useState<UserCreateDTO>({
        username: '',
        email: '',
        password: '',
    });
    const [message, setMessage] = useState<string>('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await register(formData.username, formData.email, formData.password);
            setMessage('Регистрация успешна!');
            setFormData({ username: '', email: '', password: '' }); // Очищаем форму
        } catch (error) {
            setMessage('Ошибка при регистрации: ' + (error as Error).message);
        }
    };

    return (
        <div>
            <h2>Регистрация</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Имя пользователя: </label>
                    <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Email: </label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Пароль: </label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Зарегистрироваться</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default RegistrationForm;