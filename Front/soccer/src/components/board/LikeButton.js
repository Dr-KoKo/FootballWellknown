import React, { useState, useEffect } from 'react';
import { useSelector } from "react-redux";
import {
    Grid,
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
        if(state.user.isLogin){
            
            const result = await checkBoardLike(props.boardId);
            setLike(result.data.checkLiked);
            console.log(result);
        }
        
        const result = await getHowLiked(props.boardId);
        console.log(result);
        setNumsLike(result.data.count);
    }

    const changeLike = async () => {
        const body = {
            "boardId": props.boardId,
            "checkLiked": !like
        }
        const result = await postBoardLike(body);
        // console.log(result);

        if(like)
            setNumsLike(numsLike-1);
        else 
           setNumsLike(numsLike+1);
        setLike(!like);
    }

    useEffect(() => {
        getIsLiked();
    }, [])

    return (
        <div>
            {(state.user.isLogin ? 
            <IconButton onClick={changeLike}>
                {like ? (<FavoriteIcon  color="error" />) 
                :(<FavoriteBorderIcon sx={{color:"black"}}/>)}
            </IconButton>
             : <Grid/>
            )}
            <p>{numsLike}</p>
        </div>
    );
};

export default LikeButton;