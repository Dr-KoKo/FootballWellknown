import React from "react";
import Card from '@mui/material/Card';
import { CardContent, CardMedia } from "@mui/material";
import Typography from '@mui/material/Typography';
import './PlayerCard.css'


const AssistCard = (props) =>{
    
    const top3Players = () => {
        const result = [];
        for(let i = 0; i < 3; i++){
            if(i == 0){
                result.push(
                    <div style={{display:'flex'}}>
                        <CardMedia
                            component="img"
                            image={props.players[i].image}
                            sx={{ width: 151, margin: "1rem" }}
                        >    
                        </CardMedia>
                        <CardContent key={i}>
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
            }else{
                result.push(
                    <CardContent key={i}>
                            <div style={{display:'flex'}}>
                                <Typography variant="h5" sx={{ width: '6rem', fontSize: '1.3rem', marginRight:'1.5rem'}} mb={0.5}>
                                    {`${i+1}위`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '16rem', fontSize: '1.3rem', fontWeight:'bold', marginRight:'2rem'}} mb={0.5}>
                                    {`${props.players[i].name}`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '4rem', fontSize: '1.3rem', fontWeight:'bold' }} mb={0.5}>
                                {`${props.players[i].assists} 회`}
                                </Typography>
                            </div>
                            <Typography variant="body2" sx={{ width: '12rem', fontSize:'0.7rem', marginLeft: '7.5rem', color:'black'}}>{props.players[i].teamName}</Typography>
                        </CardContent>)
            }
            
        }
        return result;
    }

    const otherPlayers = () => {
        const result = [];
        for(let i = 3; i < 10; i++){
                result.push(
                    <CardContent key={i}>
                            <div style={{display:'flex'}}>
                                <Typography variant="h5" sx={{ width: '6rem', fontSize: '1.3rem', marginRight:'1.5rem'}} mb={0.5}>
                                    {`${i+1}위`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '16rem', fontSize: '1.3rem', fontWeight:'bold', marginRight:'2rem'}} mb={0.5}>
                                    {`${props.players[i].name}`}
                                </Typography>
                                <Typography variant="h5" sx={{ width: '4rem', fontSize: '1.3rem', fontWeight:'bold' }} mb={0.5}>
                                {`${props.players[i].assists} 회`}
                                </Typography>
                            </div>
                            <Typography variant="body2" sx={{ width: '12rem', fontSize:'0.7rem', marginLeft: '7.5rem', color:'black'}}>{props.players[i].teamName}</Typography>
                        </CardContent>)
            }
            
 
        return result;
    }


    return (
        <div className="card-container">
            <Card sx={{ backgroundColor: 'transparent'}}>
                <h2 style={{marginLeft: '1rem'}}>{props.category}</h2>
                <div style={{minHeight:'400px'}}>{top3Players()}</div>
            </Card>
            <div>{otherPlayers()}</div>
        </div>
    )
}

export default AssistCard;