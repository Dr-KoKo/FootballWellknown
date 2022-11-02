import React from "react";
import { Paper, Button } from '@mui/material'
import './Item.css'
import { teamNameShort } from "const/teamNameShort";

const day = ['(일)','(월)','(화)','(수)','(목)','(금)','(토)']

const Item = (props) =>{
    console.log(props.item[0]);

    return (
        <div className="item-container">
            
            {props.item[0].map((data, i) => 
            <div className="item-paper" key={i}>
                <div className="item-body">
                    <div className="item-team">
                        <img src={data.matchVO.homeImage} alt="home" width={'50%'}/>
                        <p style={{fontWeight:"bold"}}>{teamNameShort[data.matchVO.home]}</p>
                    </div>
                    {data.matchStatus === 'READY' ? 
                    <div className="item-text">
                    {/*날짜 */}
                    <p style={{fontWeight:"bold", margin: "0"}}>
                        {data.matchVO.date.split('T')[0].split('-')[1]}.
                        {data.matchVO.date.split('T')[0].split('-')[2]}
                        {day[new Date(data.matchVO.date).getDay()]}
                    </p>
                    <p style={{fontWeight:"bold", marginTop: "0.2rem", marginBottom: "0.2rem"}}>vs</p>
                    {/*시간 */}
                    <p style={{fontWeight:"bold", margin: "0"}}>{data.matchVO.date.split('T')[1]}</p>
                    
                    </div>
                    :
                    <div className="item-text">
                    {/*날짜 */}
                    <p style={{fontWeight:"bold", margin: "0"}}>
                        {data.matchVO.date.split('T')[0].split('-')[1]}.
                        {data.matchVO.date.split('T')[0].split('-')[2]}
                        {day[new Date(data.matchVO.date).getDay()]}
                    </p>
                    <p style={{fontWeight:"bold", marginTop: "0.2rem", marginBottom: "0.2rem"}}>{`${data.matchVO.homeScore} : ${data.matchVO.awayScore}`}</p>
                    {/*시간 */}
                    <p style={{fontWeight:"bold", margin: "0"}}>종료</p>
                    
                    </div>
                    }
                    
                    

                    <div className="item-team">
                        <img src={data.matchVO.awayImage} alt="away" width={'50%'}/>
                        <p style={{fontWeight:"bold"}}>{teamNameShort[data.matchVO.away]}</p>
                    </div>
                </div>
                
            </div>
            )}
        </div>
    )
}
export default Item;