import { combineReducers } from 'redux';
import dataTypeReducer from './dataTypeReducer';
import dataFileReducer from './dataFileReducer';
import dataSubTypeReducer from './dataSubTypeReducer';
import schemaVersionReducer from './schemaVersionReducer';
import releaseVersionReducer from './releaseVersionReducer';

const rootReducer = combineReducers({
    dataTypeReducer,
    dataFileReducer,
    dataSubTypeReducer,
    schemaVersionReducer,
    releaseVersionReducer
})

export default rootReducer