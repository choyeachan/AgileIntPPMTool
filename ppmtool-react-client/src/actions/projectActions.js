import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async dispacth => {
    try {
        const res = await axios.post(
            "/api/project", project
        );
        history.push("/dashboard");
        dispacth({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {
        dispacth({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const getProjects = () => async dispatch => {
    const res = await axios.get("/api/project/all");
    dispatch({
        type: GET_PROJECTS,
        payload: res.data
    });
}

export const getProject = (id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/project/${id}`);
        dispatch({
            type: GET_PROJECT,
            payload: res.data
        });
    } catch (error) {
        history.push("/dashboard");
    }
}

export const deleteProject = id => async dispatch => {
    if (window.confirm("프로젝트를 삭제하시겠습까?")) {
        await axios.delete(`/api/project/${id}`)
        dispatch({
            type: DELETE_PROJECT,
            payload: id
        });
    }

}