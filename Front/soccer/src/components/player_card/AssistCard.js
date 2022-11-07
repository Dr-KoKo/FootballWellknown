import React from "react";
import Card from '@mui/material/Card';
import { CardContent, CardMedia } from "@mui/material";
import Typography from '@mui/material/Typography';
import './PlayerCard.css'

import Slider from "react-slick";
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import { pink } from "@mui/material/colors";


const AssistCard = (props) =>{

    // slider 세팅
    const settings = {
        dots: false,
        vertical: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        autoplay: true,
        autoplaySpeed: 2000,
      };
    
    const topPlayers = () => {
        const result = [];
        for(let i = 0; i < 3; i++){
            if(i === 0){
                result.push(
                    <div key={i} style={{display:'flex'}}>
                        <CardMedia
                            component="img"
                            image={props.players[i].image}
                            sx={{ width: 151, margin: "1rem", height: 151}}
                        >    
                        </CardMedia>
                        <CardContent>
                            <div style={{display:'flex'}}>
                                <Typography variant="h5" sx={{ width: '4rem', fontSize: '1.5rem', marginRight:'1.5rem'}} mb={0.5}>
                                    {`${i+1}위`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '12rem', fontSize: '1.5rem', fontWeight:'bold', marginRight:'2rem'}} mb={0.5}>
                                    {`${props.players[i].name}`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '5rem', fontSize: '1.5rem', fontWeight:'bold'}} mb={0.5}>
                                    {`${props.players[i].assists} 회`}
                                </Typography>
                            </div>
                            <Typography variant="body2" sx={{ width: '10rem',fontSize: '0.8rem', marginLeft: '5.5rem', color:'black'}}>{props.players[i].teamName}</Typography>
                        </CardContent>
                    </div>
                )
            }
        }
        return result;
    }

    const otherPlayers = () => {
        const result = [];
        for(let i = 1; i < 10; i++){
                result.push(
                    <div key={i} id="otherCard">
                        <CardMedia
                                component="img"
                                image={props.players[i].image}
                                sx={{ width: 151, margin: "1rem", height: 151 }}
                            >    
                        </CardMedia>
                        <CardContent>
                            <div style={{display:'flex'}}>
                                <Typography variant="h5" sx={{ width: '4rem', fontSize: '1.3rem', marginRight:'1.5rem'}} mb={0.5}>
                                    {`${i+1}위`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '12rem', fontSize: '1.3rem', fontWeight:'bold', marginRight:'2rem'}} mb={0.5}>
                                    {`${props.players[i].name}`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '5rem', fontSize: '1.3rem', fontWeight:'bold' }} mb={0.5}>
                                {`${props.players[i].assists} 회`}
                                </Typography>
                            </div>
                            <Typography variant="body2" sx={{ width: '10rem', fontSize:'0.7rem', marginLeft: '5.5rem', color:'black'}}>{props.players[i].teamName}</Typography>
                        </CardContent>
                        </div>
                        )
            }
            
 
        return result;
    }


    return (
        <div className="card-container">
            <Card sx={{ backgroundColor: 'transparent'}} raised={true}>
                <h2 style={{marginLeft: '1rem'}}>{props.category}</h2>
                <div style={{minHeight:'200px'}}>{topPlayers()}</div>
            </Card>
            <Slider {...settings}>
                {otherPlayers()}
            </Slider>
        </div>
    )
}

export default AssistCard;