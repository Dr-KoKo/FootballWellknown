import React from "react";
import { useState } from "react";
import axios from "axios";

function TeamMain(){
    const [datas, setDatas] = useState(null);
    try{
        const response = await axios.get("http://localhost:8080/teams/ranks");
        setDatas(response.result);
        console.log(datas);
    } catch(e){
        console.log(e);
    }
    return(
        <div>{datas}</div>
    ); 

}