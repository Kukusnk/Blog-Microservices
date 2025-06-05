import React, { useState } from 'react';

interface UserDTO {
    id: string;
    username: string;
    email: string;
}

const SearchForm: React.FC = () => {
    const [email, setEmail] = useState<string>('');
    const [user, setUser] = useState<UserDTO | null>(null);
    const [message, setMessage] = useState<string>('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8081/users/email/${email}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    // Добавляем токен, если он есть
                    Authorization: `Bearer ${localStorage.getItem('jwtToken') || ''}`,
                },
            });
            if (response.ok) {
                const data: UserDTO = await response.json();
                setUser(data);
                setMessage('');
            } else {
                setUser(null);
                setMessage('Пользователь не найден.');
            }
        } catch (error) {
            setUser(null);
            setMessage('Ошибка: Не удалось подключиться к серверу.');
        }
    };

    return (
        <div>
            <h2>Поиск пользователя по Email</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email: </label>
                    <input
                        type="email"
                        value={email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Поиск</button>
            </form>
            {message && <p>{message}</p>}
            {user && (
                <div>
                    <h3>Найденный пользователь:</h3>
                    <p>ID: {user.id}</p>
                    <p>Имя: {user.username}</p>
                    <p>Email: {user.email}</p>
                </div>
            )}
        </div>
    );
};

export default SearchForm;