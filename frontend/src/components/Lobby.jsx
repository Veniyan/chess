import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';

const Lobby = () => {
    const [games, setGames] = useState([]);
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        fetchGames();
    }, []);

    const fetchGames = async () => {
        try {
            const response = await api.get('/games');
            setGames(response.data);
        } catch (error) {
            console.error('Error fetching games:', error);
        }
    };

    const createGame = async () => {
        try {
            console.log('Creating game...');
            const response = await api.post('/games/create');
            console.log('Game created:', response.data);
            navigate(`/game/${response.data.id}`);
        } catch (error) {
            console.error('Error creating game:', error);
            alert(`Failed to create game: ${error.response?.data?.error || error.message}`);
        }
    };

    const resumeGame = (gameId) => {
        navigate(`/game/${gameId}`);
    };

    return (
        <div className="lobby-container">
            <h2>Welcome {user?.username}</h2>
            <button onClick={createGame}>Start New Local Game</button>
            <h3>Your Recent Games:</h3>
            {games.length === 0 ? <p>No games found.</p> : (
                <ul className="game-list">
                    {games.map(game => (
                        <li key={game.id}>
                            <span>Game #{game.id} - {new Date(game.startTime).toLocaleString()}</span>
                            <button onClick={() => resumeGame(game.id)}>Resume</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default Lobby;
