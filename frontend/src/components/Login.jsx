import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../api/axiosConfig';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            // Encode credentials for Basic Auth
            const authdata = window.btoa(username + ':' + password);
            console.log('Logging in with username:', username);
            console.log('Encoded authdata:', authdata);

            const user = { username, authdata };

            // Verify credentials
            const response = await api.get('/auth/me', {
                headers: { Authorization: `Basic ${authdata}` }
            });

            console.log('Login verification successful:', response.data);
            console.log('Storing user in localStorage:', user);

            localStorage.setItem('user', JSON.stringify(user));

            // Verify it was stored
            const storedUser = localStorage.getItem('user');
            console.log('Verified stored user:', storedUser);

            navigate('/lobby');
        } catch (error) {
            console.error('Login error:', error);
            alert('Invalid credentials');
        }
    };

    return (
        <div className="auth-container">
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">Login</button>
            </form>
            <p>Don't have an account? <Link to="/register">Register</Link></p>
        </div>
    );
};

export default Login;
