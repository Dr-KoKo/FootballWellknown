import React, { useState, useEffect } from 'react';
import {
    IconButton,
  } from "@mui/material";
import { checkBoardLike, postBoardLike } from 'services/boardServices';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

export const BOARD_LIKED_URL = "api/v1/boards/likes";

const LikeButton = (props) => {
    const [state, setState] = useState(false);
    const [numsLike, setNumsLike] = useState(-1);

    const getIsLiked = async () => {
        const result = await checkBoardLike(props.boardId);
        setState(result.data.checkLiked.liked);
        setNumsLike(result.data.checkLiked.numLiked);
    }

    const changeState = async () => {
        const body = {
            "boardId": props.boardId,
            "checkLiked": !state
        }
        const result = await postBoardLike(body);
        console.log(result);
        if(state)
            setNumsLike(numsLike-1);
        else 
           setNumsLike(numsLike+1);
        
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
            <p>{numsLike}</p>
        </div>
    );
};

export default LikeButton;