import React, { useState, useEffect } from 'react';
import { useSelector } from "react-redux";
import {
    IconButton,
  } from "@mui/material";
import { checkBoardLike, getHowLiked, postBoardLike } from 'services/boardServices';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

export const BOARD_LIKED_URL = "api/v1/boards/likes";

const LikeButton = (props) => {
    const [like, setLike] = useState(false);
    const [numsLike, setNumsLike] = useState(-1);
    const state = useSelector(state => state);

    const getIsLiked = async () => {
        
        if(state.user.isLongin){
            const result = await checkBoardLike(props.boardId);
            console.log(result);
            setLike(result.data.checkLiked);
        }
        
        const result = await getHowLiked(props.boardId);
        console.log(result);
        setNumsLike(result.data.count);
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
            {/* {(state.user.isLongin ?  */}
            <IconButton onClick={changeState}>
                {like ? (<FavoriteIcon  color="error" />) 
                :(<FavoriteBorderIcon sx={{color:"black"}}/>)}
            </IconButton>
            {/* : <Grid/>
            )} */}
            <p>{numsLike}</p>
        </div>
    );
};

export default LikeButton;