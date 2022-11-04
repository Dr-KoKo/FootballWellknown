import match from "modules/match.js";
import { request, axiosAuth } from "./axios.js";

const MATCH_URL = "api/v1/matches";

export const getTeamList = async () => {
  try {
    const payload = await request.get(`${MATCH_URL}/boards/teams`);
    return payload;
  } catch (err) {
    return err;
  }
}

export const getMatchList = async (round) => {
  try {
    const payload = await request.get(`${MATCH_URL}/boards/rounds/${round}`);
    return payload;
  } catch(err) {
    return err;
  }
}

export const getTeam = async (teamId) => {
  try {
    const payload = await request.get(`${MATCH_URL}/boards/teams/${teamId}`);
    return payload;
  } catch (err) {
    return err;
  }
};

export const getRound = async (matchId) => {
    try {
        const payload = await request.get(`${MATCH_URL}/boards/matches/${matchId}`)
        return payload;
    } catch (err) {
        return err;
      }
}

export const getMatch = async (matchId) => {
  try {
    const payload = await request.get(`${MATCH_URL}/match/${matchId}`);
    return payload;
  } catch (err) {
    return err;
  }
};
