import React, { Fragment, useState } from "react";
import { useEffect } from "react";
import axios from "axios";

const MatchMain = () => {
  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth() + 1;
  const [year, setYear] = useState(currentYear);
  const [month, setMonth] = useState(currentMonth);
  const [matches, setMatches] = useState(
    [{matchVO:{
        matchId: 0,
        date: "",
        stadium: "",
        homeScore: "",
        awayScore: ""
    }}]
  );

  const handleChange = (y, m) => {
    setYear(y);
    setMonth(m);
  };

  const moveToDetail = (matchId) => {
    window.location.href = `/match/${matchId}`;
  }

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/matches/dates?year=${year}&month=${month}`)
    .then((res) => {
        setMatches(res.data.result);
    });
  },[month]);

  return (
    <Fragment>
        <span onClick={()=>handleChange(2022,8)}>2022년 8월 | </span>
        <span onClick={()=>handleChange(2022,9)}>9월 | </span>
        <span onClick={()=>handleChange(2022,10)}>10월 | </span>
        <span onClick={()=>handleChange(2022,11)}>11월 | </span>

        <table>
            <thead>
                <tr>
                    <th>날짜</th>
                    <th>시간</th>
                    <th>장소</th>
                    <th>경기</th>
                </tr>
            </thead>
            <tbody>
                {matches.map((data, index) => (
                    <tr key={index} onClick={() => moveToDetail(data.matchVO.matchId)}>
                        <td>{data.matchVO.date.split('T')[0]}</td>
                        <td>{data.matchVO.date.split('T')[1]}</td>
                        <td>{data.matchVO.stadium}</td>
                        <td>{data.matchVO.homeScore} vs {data.matchVO.awayScore}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    </Fragment>
  );
};

export default MatchMain;
