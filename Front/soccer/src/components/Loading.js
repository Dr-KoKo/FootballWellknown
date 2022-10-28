import React from 'react';
import {Background, LoadingText} from './Styles';
import Spinner from './assets/Spin.gif';
const Loading = () => {
    return (
        <Background>
            <LoadingText>잠시만 기다려주세요</LoadingText>
            <img src={Spinner} alt='로딩중' width='5%'></img>
        </Background>
    );
};

export default Loading;