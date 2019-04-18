import { combineReducers } from 'redux';
import dataTypeReducer from './dataTypeReducer';
import dataFileReducer from './dataFileReducer';
import dataSubTypeReducer from './dataSubTypeReducer';
import schemaVersionReducer from './schemaVersionReducer';

const rootReducer = combineReducers({
    dataTypeReducer,
    dataFileReducer,
    dataSubTypeReducer,
    schemaVersionReducer
})

export default rootReducer