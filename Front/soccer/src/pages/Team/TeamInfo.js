import React, { useEffect, useState } from "react";
import axios from "axios";
import Loading from "components/Loading";
import "./TeamInfo.css";
const TeamInfo = () => {
  const [datas, setDatas] = useState([{ name: "1234" }]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    axios.get("http://localhost:8080/api/v1/teams/ranks").then((response) => {
      setDatas(response.data.result);
      setLoading(false);
    });
  }, []);
  function goTeamDetail(id) {
    window.location.href = `/teamdetail/${id}/match`;
  }

  return (
    <div id="teamDiv">
      {loading ? (
        <Loading />
      ) : (
        <table className="table" id="teamTable">
          <thead id="teamTableHead">
            <tr id="teamTableHeadTr">
              <th className="rank" scope="col">
                순위
              </th>
              <th className="name" scope="col"></th>
              <th className="th" scope="col">
                경기
              </th>
              <th className="th" scope="col">
                승
              </th>
              <th className="th" scope="col">
                무
              </th>
              <th className="th" scope="col">
                패
              </th>
              <th className="th" scope="col">
                득점
              </th>
              <th className="th" scope="col">
                실점
              </th>
              <th className="th" scope="col">
                득실차
              </th>
              <th className="th" scope="col">
                승점
              </th>
            </tr>
          </thead>
          <tbody id="teamTableBody">
            {datas.map((data) => (
              <tr
                id="teamTableBodyTr"
                key={data.name}
                onClick={() => goTeamDetail(data.teamId)}
              >
                <td className="rank">{data.rank}</td>
                <td className="name" id="teamTableTeam">
                  <img id="teamLogo" src={data.image} alt="" />
                  <div id="teamName">{data.name}</div>
                </td>
                <td className="th">{data.win + data.lose + data.draw}</td>
                <td className="th">{data.win}</td>
                <td className="th">{data.draw}</td>
                <td className="th">{data.lose}</td>
                <td className="th">{data.goals}</td>
                <td className="th">{data.loseGoals}</td>
                <td className="th">{data.goals - data.loseGoals}</td>
                <td className="th">{data.pts}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default TeamInfo;
