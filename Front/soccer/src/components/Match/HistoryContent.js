import { Box, Typography } from '@mui/material';
import React from 'react';
import goal from '../assets/ball.png';
import yellow from '../assets/yellow-card.png';
import red from '../assets/red.png';
import outArrow from '../assets/upward-arrow.png';
import inArrow from '../assets/downward-arrow.png';

const HistoryContent = (props) => {
  const history = props.history;
  const content = history.history;
  const flag = props.opposite;
  if(content === "SUB"){
    if(history.teamType === "HOME"){
      return (
        <Box>
          <Typography
            variant="h6" 
            component="span"
            textAlign={flag ? 'right' : 'left'}
            color='GrayText'
            m={1}
          >
            {history.mainName}
          </Typography>
          <img src={outArrow} alt="out" />
          <br/>
          <Typography 
            variant="h6" 
            component="span"
            textAlign={flag ? 'right' : 'left'}
            m={1}
          >
            {history.subName}</Typography>
          <img src={inArrow} alt="in" />
        </Box>
      )
    }
    else{
      return (
        <Box>
          <img src={outArrow} alt="out" />
          <Typography
            variant="h6" 
            component="span"
            textAlign={flag ? 'right' : 'left'}
            color='GrayText'
            m={1}
          >
            {history.mainName}
          </Typography>
          <br/>
          <img src={inArrow} alt="in" />
          <Typography 
            variant="h6" 
            component="span"
            textAlign={flag ? 'right' : 'left'}
            m={1}
          >
            {history.subName}</Typography>
        </Box>

      )
    }
  }
  else if(content === "GOAL") return (
    <Box>
      <Typography
        variant="h6" 
        component="span"
        textAlign={flag ? 'right' : 'left'}
        fontWeight={'bold'}
      >
        {history.mainName} GOAL!!
      </Typography>
      <img src={goal} alt="goal" />
      {history.subName.length > 0 && 
      <Typography variant='subtitle2'>{history.subName} 도움</Typography>}
    </Box>
  )
  else if(content === "YELLOW") return (
    <Box>
      <Typography
        variant="h6" 
        component="span"
        textAlign={flag ? 'right' : 'left'}
      >
        {history.mainName}
      </Typography>
      <img src={yellow} alt="yellow-card" />
    </Box>
  )
  else if(content === "RED") return (
    <Box>
      <Typography
        variant="h6" 
        component="span"
        textAlign={flag ? 'right' : 'left'}
      >
        {history.mainName}
      </Typography>
      <img src={red} alt="red-card" />
    </Box>
  )

};

export default HistoryContent;