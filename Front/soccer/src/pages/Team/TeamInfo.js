import React, { useEffect, useState } from "react";
import axios from "axios";
const TeamInfo = () => {
  const [datas, setDatas] = useState([{ name: "1234" }]);
  useEffect(() => {
    axios.get("http://localhost:8080/api/v1/teams/ranks").then((response) => {
      setDatas(response.data.result);
    });
  }, []);

  return (
    <div>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">순위</th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col">경기</th>
            <th scope="col">승</th>
            <th scope="col">무</th>
            <th scope="col">패</th>
            <th scope="col">득점</th>
            <th scope="col">실점</th>
            <th scope="col">득실차</th>
            <th scope="col">승점</th>
          </tr>
        </thead>
        <tbody>
          {datas.map((data) => (
            <tr key={data.name}>
              <td>{data.rank}</td>
              <td>{data.name}</td>
              <td>
                <img src={data.image} alt="" />
              </td>
              <td>{data.win + data.lose + data.draw}</td>
              <td>{data.win}</td>
              <td>{data.draw}</td>
              <td>{data.lose}</td>
              <td>{data.goals}</td>
              <td>{data.loseGoals}</td>
              <td>{data.goals - data.loseGoals}</td>
              <td>{data.pts}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TeamInfo;
