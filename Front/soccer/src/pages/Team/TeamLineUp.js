import axios from "axios";
import React, { useState, useEffect } from "react";
import { useParams } from "react-router";

const TeamLineUp = () => {
    const SERVER_URL = process.env.REACT_APP_SERVER_URL; 
    const {id} = useParams();
    const [matches ,setMatches] = useState(null);
    useEffect(() => {
        axios.get(`${SERVER_URL}/api/v1/teams/${id}/details`).then((response) => {
            console.log(response.data.result);
          setMatches(response.data.result);
        });
      }, []);

      return(<div>TeamLineUp</div>);
}