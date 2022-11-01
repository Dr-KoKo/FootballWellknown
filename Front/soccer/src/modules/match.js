export const initState = {
  matchId: 0,
  date: "",
  home: "",
  homeImage: "",
  awayImage: "",
  homeScore: 0,
  away: "",
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
        homeImage: action.payload.homeImage,
        awayImage: action.payload.awayImage,
        homeScore: action.payload.homeScore,
        away: action.payload.away,
        awayScore: action.payload.awayScore,
        stadium: action.payload.stadium,
        matchStatus: action.matchStatus,
      }
    default:
      return state;
  }
}