import React, { useState } from 'react';
import { login } from './auth';

const LoginForm: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [message, setMessage] = useState<string>('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.name === 'username') {
            setUsername(e.target.value);
        } else if (e.target.name === 'password') {
            setPassword(e.target.value);
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await login(username, password);
            setMessage('Логин успешен!');
            setUsername('');
            setPassword('');
        } catch (error) {
            setMessage('Ошибка при логине: ' + (error as Error).message);
        }
    };

    return (
        <div>
            <h2>Вход</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Имя пользователя: </label>
                    <input
                        type="text"
                        name="username"
                        value={username}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Пароль: </label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Войти</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default LoginForm;