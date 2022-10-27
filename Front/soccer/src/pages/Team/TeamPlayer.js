import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Loading from 'components/Loading';
import { useParams } from 'react-router';
const TeamPlayer = () => {
    const {id} = useParams();
    const [datas,setDatas] = useState([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        axios.get(`http://localhost:8080/api/v1/teams/${id}/players`).then((response) => {
          setDatas(response.data.result);
          setLoading(false);
        });
      }, []);
    return (
        <div>
          <div>감독</div>
           <div>{datas.coachName}</div>
           <img src={datas.coachImage} alt=""></img>
            <div>{datas.coachAge}</div>
            <div>{datas.country}</div>
            <div>골키퍼</div>
            <div></div>
        </div>

    );
};

export default TeamPlayer;