import React, { useState } from 'react';

interface UserDTO {
    id: string;
    username: string;
    email: string;
}

const SearchForm: React.FC = () => {
    // State for search input and result
    const [email, setEmail] = useState<string>('');
    const [user, setUser] = useState<UserDTO | null>(null);
    const [message, setMessage] = useState<string>('');

    // Handle input change
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    };

    // Handle form submission
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://192.168.3.152:8081/users/email/${email}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                const data: UserDTO = await response.json();
                setUser(data);
                setMessage('');
            } else {
                setUser(null);
                setMessage('User not found.');
            }
        } catch (error) {
            setUser(null);
            setMessage('Error: Could not connect to the server.');
        }
    };

    return (
        <div>
            <h2>Search User by Email</h2>
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
                <button type="submit">Search</button>
            </form>
            {message && <p>{message}</p>}
            {user && (
                <div>
                    <h3>User Found:</h3>
                    <p>ID: {user.id}</p>
                    <p>Username: {user.username}</p>
                    <p>Email: {user.email}</p>
                </div>
            )}
        </div>
    );
};

export default SearchForm;