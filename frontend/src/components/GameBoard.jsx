import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Chess } from 'chess.js';
import { Chessboard } from 'react-chessboard';
import api from '../api/axiosConfig';

const GameBoard = () => {
    const { id } = useParams();
    const [game, setGame] = useState(new Chess());
    const [fen, setFen] = useState(game.fen());
    const [status, setStatus] = useState('');

    // In local hotseat mode, we don't care about "user's color" since both play on same screen
    // so checks for orientation or turn vs user are removed.

    useEffect(() => {
        const fetchGame = async () => {
            try {
                const response = await api.get(`/games/${id}`);
                const serverGame = response.data;
                const newGame = new Chess();
                // If remote fen exists and is valid, load it
                if (serverGame.fen) {
                    newGame.load(serverGame.fen);
                }
                setGame(newGame);
                setFen(newGame.fen());
                setStatus(serverGame.status);
            } catch (error) {
                console.error("Error loading game", error);
            }
        };

        fetchGame();
        // Removed polling used intervals
    }, [id]);

    const onDrop = (sourceSquare, targetSquare) => {
        const move = {
            from: sourceSquare,
            to: targetSquare,
            promotion: 'q',
        };

        try {
            const tempGame = new Chess(game.fen());
            const result = tempGame.move(move);

            if (!result) return false; // Illegal move

            // Update local state immediately for responsiveness
            setGame(tempGame);
            setFen(tempGame.fen());

            // Sync with server for validation/persistence
            api.post(`/games/${id}/move`, { fen: tempGame.fen() })
                .catch(err => {
                    console.error("Move failed on server", err);
                    // Could revert here if needed
                });

            return true;
        } catch (e) {
            return false;
        }
    };

    return (
        <div className="game-container">
            <h2>Game #{id} (Local Hotseat)</h2>
            <p>Status: {status}</p>
            <p>Turn: {game.turn() === 'w' ? "White" : "Black"}</p>
            <div className="board-wrapper">
                <Chessboard position={fen} onPieceDrop={onDrop} />
            </div>
        </div>
    );
};

export default GameBoard;
