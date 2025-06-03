import React, { useState } from 'react';

interface UserCreateDTO {
    username: string;
    email: string;
    password: string;
}

const RegistrationForm: React.FC = () => {
    // State for form fields
    const [formData, setFormData] = useState<UserCreateDTO>({
        username: '',
        email: '',
        password: ''
    });
    const [message, setMessage] = useState<string>('');

    // Handle form input changes
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    // Handle form submission
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await fetch('http://192.168.3.152:8081/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                const data = await response.json();
                setMessage(`User ${data.username} registered successfully!`);
                setFormData({ username: '', email: '', password: '' }); // Clear form
            } else {
                setMessage('Registration failed. Please try again.');
            }
        } catch (error) {
            setMessage('Error: Could not connect to the server.');
        }
    };

    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Username: </label>
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
                    <label>Password: </label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Register</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default RegistrationForm;