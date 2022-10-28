import { axiosAuth } from './axios';




export const USER_URL = "/api/v1/users";

export const getUserInfo = async () => {
    try{
        const payload = await axiosAuth.get(`${USER_URL}`)
        return payload
    } catch(err){
        return err;
    }
};