import React, { useState, useEffect } from 'react';
import { getCurrentUser } from './auth';

const GetMe: React.FC = () => {
    const [user, setUser] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const userData = await getCurrentUser();
                setUser(userData);
                setLoading(false);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'Произошла ошибка');
                setLoading(false);
            }
        };

        fetchUser();
    }, []);

    if (loading) {
        return <div>Загрузка...</div>;
    }

    if (error) {
        return <div className="text-red-500">{error}</div>;
    }

    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold">Текущий пользователь</h1>
            <p>{user}</p>
        </div>
    );
};

export default GetMe;