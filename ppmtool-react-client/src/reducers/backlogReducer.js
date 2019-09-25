import {
    GET_BACKLOG,
    GET_PROJECT_TASK,
    DELETE_PROEJCT_TASK
} from "../actions/types"
import { stat } from "fs"

const initialState = {
    project_tasks: [],
    project_task: {}
}

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_BACKLOG:
            return {
                ...state,
                project_tasks: action.paylaod
            }
        case GET_PROJECT_TASK:
            return {
                ...state,
                project_task: action.paylaod
            }
        case DELETE_PROEJCT_TASK:
            return {
                ...state
                //TO_DO
            }
        default:
            return state;
    }
}