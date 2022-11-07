export const initState = {
  matchId: 0,
  date: "",
  home: "",
  homeId: 0,
  homeImage: "",
  awayImage: "",
  homeScore: 0,
  away: "",
  awayId: 0,
  awayScore: 0,
  stadium: "",
  matchStatus: "",
};

export default function (state = initState, action){
  switch(action.type){
    case "SET_MATCH":
      return {
        matchId: action.payload.matchId,
        date: action.payload.date,
        home: action.payload.home,
        homeId: 0,
        homeImage: action.payload.homeImage,
        awayImage: action.payload.awayImage,
        homeScore: action.payload.homeScore,
        away: action.payload.away,
        awayId: 0,
        awayScore: action.payload.awayScore,
        stadium: action.payload.stadium,
        matchStatus: action.matchStatus,
      }
    case "SET_HOME_ID":
      return {
        ...state,
        homeId: action.payload
      }
      case "SET_AWAY_ID":  
        return {
          ...state,
          awayId: action.payload
        }
    default:
      return state;
  }
}