import {combineReducers} from 'redux';
import user from './user';
import match from './match';

const rootReducer = combineReducers({
    user,
    match,
})

export default rootReducer;