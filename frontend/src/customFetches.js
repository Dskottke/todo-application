import axios from "axios";

const customToDoFetch = axios.create({
    baseURL: "/api/todos"
});

export default customToDoFetch

