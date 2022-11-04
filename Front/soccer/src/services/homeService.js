import {request} from './axios.js'

const MATCH_URL = 'api/v1/matches/round';
const PLAYER_URL = 'api/v1/teams/players/ranks';

export const getMatchInfo = async(round) => {
    try {
        const payload = await request.get(
            `${MATCH_URL}?round=${round}`
        );
        return payload;
    } catch (err) {
        return err;
    }
}

export const getPlayerInfo = async() => {
    try{
        const payload = await request.get(
            `${PLAYER_URL}`
        );
        return payload;
    } catch (err) {
        return err;
    }
}