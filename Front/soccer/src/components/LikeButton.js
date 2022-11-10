import React, { useState, useEffect } from 'react';
import {
    IconButton,
  } from "@mui/material";
import { axiosAuth } from 'services/axios';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

export const BOARD_LIKED_URL = "api/v1/boards/isLiked";

const LikeButton = (props) => {
    const [state, setState] = useState(false);

    const getIsLiked = () => {
        const result = axiosAuth.post(`${BOARD_LIKED_URL}/${props.boardId}`);
        console.log(result);
    }

    const changeState = () => {
        alert(props.boardId);
        setState(!state);
    }

    useEffect(() => {
        getIsLiked();
    }, [])

    return (
        <div>
            <IconButton onClick={changeState}>
            {state ? (
                <FavoriteIcon  color="error" />
            ) 
            :(
                <FavoriteBorderIcon sx={{color:"black"}}/>
            )}
            </IconButton>
        </div>
    );
};

export default LikeButton;