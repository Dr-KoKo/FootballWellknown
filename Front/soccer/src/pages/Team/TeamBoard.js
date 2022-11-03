import React from 'react';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router';
const TeamBoard = () => {
    const {id} = useParams();
    const [datas, setDatas] = useState(null);
    useEffect(() => {
        axios
          .get(`http://localhost:8080/api/v1/teams/players/${id}`)
          .then((response) => {
            setDatas(response.data.result);
            console.log(response.data.result);
            
          });
      }, []);
    return (
        <div>
            보드입니다.
        </div>
    );
};

export default TeamBoard;